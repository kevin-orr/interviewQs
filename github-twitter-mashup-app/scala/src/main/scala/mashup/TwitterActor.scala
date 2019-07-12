package mashup

import java.util.concurrent.CountDownLatch

import akka.actor.{Actor, ActorRef, Props}
import com.danielasfregola.twitter4s.TwitterRestClient
import com.danielasfregola.twitter4s.entities._
import com.danielasfregola.twitter4s.entities.enums.ResultType
import mashup.AppImplicitsAndConfig.AppConfig
import mashup.Protocol._

import scala.concurrent.Future
import scala.util.{Failure, Success}

class TwitterActor(projectName: String, tweetCount: Int = 10, driver: ActorRef, doneSignal: CountDownLatch, appConfig: AppConfig)
  extends Actor
    with akka.actor.ActorLogging {

  log.info(s"Beginning search of twitter for the $tweetCount most recent tweets about project $projectName")

  import AppImplicitsAndConfig._
  // setup the tokens for Dan's twitter4s module
  val consumerToken = appConfig("consumer").asInstanceOf[ConsumerToken]
  val accessToken = appConfig("access").asInstanceOf[AccessToken]
  // and the client that's gonna do the heavy lifting - we don't need to do the raw api calls - coolio
  val client = new TwitterRestClient(consumerToken, accessToken)

  // enter the dragon, I mean, loop...
  override def receive: Receive = {

    // ok, so we've been given signal to search twitter
    case TwitterSearch(gitHubProjectToSearch) =>

            val future: Future[RatedData[StatusSearch]] = client.searchTweet(query = gitHubProjectToSearch.name, count = tweetCount, result_type = ResultType.Recent)

            future.onComplete {

              case Success(RatedData(rate_limit, StatusSearch(listTweets, _))) => sendMsg(ProjectTweetsInfo(gitHubProjectToSearch, listTweets.map(_.text)))

              case Failure(_) => sendMsg(BadTwitterResponse(s"Failed to get response from twitter for $projectName"))

            }

    // ok, so we've been told we can quit now
    case Quit => log.info(s"Twitter actor for project ${projectName} sent signal to shutdown...");
                 driver ! ActorShuttingDown(projectName)
                 context.stop(this.self)

    // ah FFS, something unknown, log it so!
    case _ => log.info("Recieved unknown error from twitter"); sendMsg(BadTwitterResponse(s"Something when wrong when searching twitter for $projectName"))
  }

  // inform the mother ship something has gone wrong as well as dounting down the latch
  def sendMsg(command: Any) : Unit = {
    driver ! command
    doneSignal.countDown()
  }
}

object TwitterActor {

  def props(projectName: String)(tweetCount: Int = 10)(driver: ActorRef)(doneSignal: CountDownLatch)(implicit appConfig: AppConfig): Props
        = Props(new TwitterActor(projectName, tweetCount, driver, doneSignal, appConfig))

}