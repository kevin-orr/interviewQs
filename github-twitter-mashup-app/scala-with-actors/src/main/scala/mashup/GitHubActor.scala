package mashup

import java.util.concurrent.CountDownLatch

import akka.actor.{Actor, ActorRef, Props}
import akka.http.javadsl.model.StatusCodes
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.util.ByteString
import mashup.AppImplicitsAndConfig.AppConfig
import mashup.Protocol._

import scala.util.{Failure, Success}

class GitHubActor(driver: ActorRef, maxCount: Int = 10, doneSignal: CountDownLatch, config: AppConfig)
    extends Actor
      with akka.actor.ActorLogging {

  import AppImplicitsAndConfig._
  import GitHubActor._

  val projectName = appConfig.getOrElse("github.project", "Reactive")

  override def receive: Receive = {

    case SearchGitHubFor(projectName) =>
              log.info(s"Searching GitHub for projects with tag $projectName")

              Http().singleRequest(HttpRequest(uri = s"https://api.github.com/search/repositories?q=${projectName}&sort=stars&order=desc")) onComplete {

                      // ok, got a good response with some data
                      case resp @ Success(HttpResponse(StatusCodes.OK, headers, entity, _)) =>

                        entity.dataBytes.runFold(ByteString(""))(_ ++ _).foreach { body =>

                          val gitHubProjectsInfo: List[GitHubProject] = GitHubProjectInfo(body.utf8String).take(maxCount)

                          if (gitHubProjectsInfo.isEmpty) {
                            log.info(s"Failed to find any ${projectName} projects.")
                            driver ! NoGitHubProjectsFound

                          } else {
                            log.info(s"Successfully got list of projects from GitHub. Will now search twitter for tweets about these GitHub prjects:$gitHubProjectsInfo")
                            gitHubProjectsInfo.foreach(searchTwitter)
                          }

                        }
                        resp.value.discardEntityBytes()

                      // oh ho, we've not got a good response
                      case resp @ Success(HttpResponse(code, _, _, _)) =>

                        resp.value.discardEntityBytes()
                        log.info("Got bad responsefrom github, sending driver BadGitHubResponse command")
                        // report error to driver
                        driver ! BadGitHubResponse(code)

                      // WTF!
                      case Failure(_) => log.error("Failed to get response from github, sending driver UnknownGitHubFailure command"); driver ! UnknownGitHubFailure;
                  }

    case Quit => log.info("GitHub actor sent a kill signal - shutting actor down...")
                 driver ! ActorShuttingDown("GitHubActor")
                 context.stop(this.self)

    case _ => log.info("GitHub sent back UNKNOWN error so letting Driver Actor know...")
              driver ! UnknownGitHubFailure
  }

  // really used for side effect of creating an actor and then sending it a message
  def searchTwitter(gitHubProjectInfo: GitHubProject) : Unit = {
    // we're only gonna pull back 10, not all, tweets but could be configurable
    log.info(s"Creating new Twitter actor to search for ${gitHubProjectInfo.name}")
    context.actorOf(TwitterActor.props(gitHubProjectInfo.name)(maxCount)(driver)(doneSignal)) ! TwitterSearch(gitHubProjectInfo)

  }
}

object GitHubActor {

  import AppImplicitsAndConfig._

  def props(driver: ActorRef)(count: Int)(doneSignal: CountDownLatch)(implicit appConfig: AppConfig): Props = Props(new GitHubActor(driver, count, doneSignal, appConfig))

  // pull out the name+description for the top reactive projects
  def GitHubProjectInfo(jsonString: String) : List[GitHubProject] = {
    val map: Map[Any, Any] = JsonUtil.fromJson[Map[Any, Any]](jsonString)
    val items: List[Map[String, Any]] = map("items").asInstanceOf[List[Map[String, Any]]]
    val names: List[String] = items.map(_ ("name")).asInstanceOf[List[String]]
    val description: List[String] = items.map(_ ("description")).asInstanceOf[List[String]]

    names.zip(description).map(tuple => GitHubProject(tuple._1, tuple._2))
  }
}