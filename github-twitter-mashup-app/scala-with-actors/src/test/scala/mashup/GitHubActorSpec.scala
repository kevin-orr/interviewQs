package mashup

import java.util.UUID
import java.util.concurrent.CountDownLatch

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.testkit.{EventFilter, TestKit, TestProbe}
import com.typesafe.config.ConfigFactory
import mashup.AppImplicitsAndConfig.{AppConfig, system}
import mashup.Protocol.{ActorShuttingDown, NoGitHubProjectsFound, Quit, SearchGitHubFor}
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}

import scala.concurrent.duration._
import scala.language.postfixOps


class GitHubActorSpec(_system: ActorSystem)
  extends TestKit(_system)
    with Matchers
    with WordSpecLike
    with BeforeAndAfterAll {

  implicit val materializer = ActorMaterializer()

  // needed for the future flatMap/onComplete in the end
  implicit val executionContext = system.dispatcher

  implicit val appConfig: AppConfig = Map[String,Any]()

  val maxNumOfProjcts = 10
  val doneSignal = new CountDownLatch(maxNumOfProjcts)


  def this() = this(ActorSystem("GitHubActorSpec"))

  override def afterAll: Unit = {
    shutdown(system)
  }

  "A GitHub Actor" should {
    "return immediately when no projects found" in {

      val testProbe = TestProbe()

      val githubProject = UUID.randomUUID().toString

      val githubActor = system.actorOf(GitHubActor.props(testProbe.ref)(maxNumOfProjcts)(doneSignal))

      githubActor ! SearchGitHubFor(githubProject)

      testProbe.expectMsg(60000 millis, NoGitHubProjectsFound)
    }
  }

  "A GitHub Actor" should {
    "shutdown when sent QUIT command" in {

      val testProbe = TestProbe()

      val githubProject = UUID.randomUUID().toString

      val githubActor = system.actorOf(GitHubActor.props(testProbe.ref)(maxNumOfProjcts)(doneSignal))

      githubActor ! Quit

      testProbe.expectMsg(50000 millis, ActorShuttingDown("GitHubActor"))
    }
  }

  "The Github Actor" should {
    "log that it is beginning to search  for projects" in {

      implicit val system = ActorSystem("testsystem", ConfigFactory.parseString("""
  akka.loggers = ["akka.testkit.TestEventListener"]
  """))

      val testProbe = TestProbe()

      val projectName = "Scala"

      val githubActor = system.actorOf(GitHubActor.props(testProbe.ref)(2)(doneSignal))

      EventFilter.info(s"Searching GitHub for projects with tag $projectName", occurrences = 1) intercept {
          githubActor ! SearchGitHubFor(projectName)
      }
    }
  }


  "A Twitter Actor" should {
    "log that it's about to search twitter" in {

      implicit val system = ActorSystem("testsystem", ConfigFactory.parseString("""
  akka.loggers = ["akka.testkit.TestEventListener"]
  """))

      val testProbe = TestProbe()

      val githubProject = "Scala"

      val githubActor = system.actorOf(GitHubActor.props(testProbe.ref)(2)(doneSignal))

      EventFilter.info(start = "Creating new Twitter actor to search for", occurrences = 2) intercept {
          githubActor ! SearchGitHubFor(githubProject)
      }
    }
  }

  "A Github Actor" should {
    "log when an unknow error occurs" in {

      implicit val system = ActorSystem("testsystem", ConfigFactory.parseString("""
  akka.loggers = ["akka.testkit.TestEventListener"]
  """))
      val testProbe = TestProbe()

      val twitSearch = UUID.randomUUID().toString

      val githubActor = system.actorOf(GitHubActor.props(testProbe.ref)(2)(doneSignal))

      case object WTF

      EventFilter.info("GitHub sent back UNKNOWN error so letting Driver Actor know...", occurrences = 1) intercept {
          githubActor ! WTF
      }

    }
  }
}
