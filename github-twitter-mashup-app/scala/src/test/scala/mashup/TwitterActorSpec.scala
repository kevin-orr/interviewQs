package mashup

import java.util.UUID
import java.util.concurrent.CountDownLatch

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.testkit.{EventFilter, TestKit, TestProbe}
import com.danielasfregola.twitter4s.entities.{AccessToken, ConsumerToken}
import com.typesafe.config.ConfigFactory
import mashup.AppImplicitsAndConfig.AppConfig
import mashup.Protocol._
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}

import scala.concurrent.duration._
import scala.language.postfixOps


class TwitterActorSpec(_system: ActorSystem)
  extends TestKit(_system)
    with Matchers
    with WordSpecLike
    with BeforeAndAfterAll {

  implicit val materializer = ActorMaterializer()

  implicit val executionContext = system.dispatcher

  implicit val appConfig: AppConfig = Map("consumer" -> ConsumerToken("consumer.token.key", "consumer.token.secret"),
    "access" -> AccessToken("access.token.key", "access.token.secret"),
    "github.project" -> "Reactive",
    "timeout" -> "30")

  val maxNumOfProjcts = 10
  val doneSignal = new CountDownLatch(maxNumOfProjcts)


  def this() = this(ActorSystem("Mashup"))

  override def afterAll: Unit = {
    shutdown(system)
  }

  "A Twitter Actor" should {
    "return immediately when no projects found" in {

      val testProbe = TestProbe()

      val twitSearch = UUID.randomUUID().toString

      val twitterActor = system.actorOf(TwitterActor.props(twitSearch)(1)(testProbe.ref)(doneSignal))

      twitterActor ! TwitterSearch(GitHubProject(twitSearch, "some description"))

      testProbe.expectMsg(50000 millis, BadTwitterResponse(s"Failed to get response from twitter for $twitSearch"))

    }
  }

  "A Twitter Actor" should {
    "shutdown when sent QUIT command" in {

      val testProbe = TestProbe()

      val twitSearch = UUID.randomUUID().toString

      val twitterActor = system.actorOf(TwitterActor.props(twitSearch)(1)(testProbe.ref)(doneSignal))

      twitterActor ! Quit

      testProbe.expectMsg(50000 millis, ActorShuttingDown(twitSearch))
    }

    "A Twitter Actor" should {
      "log when an unknow error occurs" in {

        implicit val system = ActorSystem("testsystem", ConfigFactory.parseString("""
  akka.loggers = ["akka.testkit.TestEventListener"]
  """))
        val testProbe = TestProbe()

        val twitSearch = UUID.randomUUID().toString

        val twitterActor = system.actorOf(TwitterActor.props(twitSearch)(1)(testProbe.ref)(doneSignal))

        case object WTF

        EventFilter.info("Recieved unknown error from twitter", occurrences = 1) intercept {
            twitterActor ! WTF
        }

      }
    }
  }

}
