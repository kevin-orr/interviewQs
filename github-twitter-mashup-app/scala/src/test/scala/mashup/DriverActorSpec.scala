package mashup

import java.io.File
import java.util.UUID
import java.util.concurrent.CountDownLatch

import akka.actor.{ActorKilledException, ActorSystem}
import akka.stream.ActorMaterializer
import akka.testkit.{EventFilter, TestKit, TestProbe}
import com.typesafe.config.ConfigFactory
import mashup.AppImplicitsAndConfig.AppConfig
import mashup.Protocol._
import mashup.JsonUtil.fromJson
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}

import scala.concurrent.duration._
import scala.language.postfixOps
import org.joda.time.DateTime

import scala.io.Source
import scala.util.{Failure, Success, Try}


class DriverActorSpec(_system: ActorSystem)
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

  def this() = this(ActorSystem("mashup"))

  override def afterAll: Unit = {
    shutdown(system)
  }

  "The Driver Actor" should {
    "store results from twitter search when recieves ProjectTweetsInfo" in {

      implicit val system = ActorSystem("testsystem", ConfigFactory.parseString("""
        akka.loggers = ["akka.testkit.TestEventListener"]
      """))
      val testProbe = TestProbe()

      val githubProject = UUID.randomUUID().toString

      val driver = system.actorOf(DriverActor.props(maxNumOfProjcts))

      // send message
      driver ! ProjectTweetsInfo(GitHubProject("Some Project Name", "Some description"), List("#tweet1", "#tweet2"))
      // expect nothing
      testProbe.expectNoMessage(6000 millis)
      // create json file to store results
      val jsonFile = s"${DateTime.now}.json"
      // tell driver to print results out to file
      driver ! PrintJson(jsonFile)
      // need to shut down so we can inspect file
      shutdown(system)
      // now try and load the file as string
      val jsonString = Try[String] {
        Source.fromFile(jsonFile).mkString
      } match {
        case Success(str:String) => assert(str.nonEmpty); str
        case Failure(_)          => assert(false); "[}"
      }
      // if we get here then we've found json file so now transform back into object
      val t = fromJson[List[ProjectTweetsInfo]](jsonString)
      // if all went well we should have an object that matches what we sent to driver
      assert(t.head == ProjectTweetsInfo(GitHubProject("Some Project Name", "Some description"), List("#tweet1", "#tweet2")))

      // cleanup the json file
      assert((new File(jsonFile)).delete())
    }
  }

  "The Driver Actor" should {
    "log info before sending Quit command to GitHub actor when recieves BadGitHubResponse" in {

      implicit val system = ActorSystem("testsystem", ConfigFactory.parseString("""
  akka.loggers = ["akka.testkit.TestEventListener"]
  """))
      val testProbe = TestProbe()

      val githubProject = UUID.randomUUID().toString

      val driver = system.actorOf(DriverActor.props(maxNumOfProjcts))

      EventFilter.info("Unknow GitHub response", occurrences = 1) intercept {
          driver ! UnknownGitHubFailure
      }

      testProbe.expectNoMessage(6000 millis)
    }
  }

  "The Driver Actor" should {
    "log when an unknow error occurs" in {

      implicit val system = ActorSystem("testsystem", ConfigFactory.parseString("""
  akka.loggers = ["akka.testkit.TestEventListener"]
  """))
      val testProbe = TestProbe()

      val githubProject = UUID.randomUUID().toString

      val driver = system.actorOf(DriverActor.props(maxNumOfProjcts))
      case object WTF

      EventFilter.info("Got an unknown failure...\n", occurrences = 1) intercept {
          driver ! WTF
      }

      testProbe.expectNoMessage(6000 millis)
    }
  }

  "The Driver Actor" should {
    "log id when sent ActorShuttingDown" in {

      implicit val system = ActorSystem("testsystem", ConfigFactory.parseString("""
  akka.loggers = ["akka.testkit.TestEventListener"]
  """))

      val testProbe = TestProbe()

      val githubProject = UUID.randomUUID().toString

      val driver = system.actorOf(DriverActor.props(maxNumOfProjcts))

      EventFilter.info("Twitter Actor 11 has been shut down", occurrences = 1) intercept {
          driver ! ActorShuttingDown("11")
      }

      testProbe.expectNoMessage(6000 millis)
    }
  }

  "The Driver Actor" should {
    "log sent NoGitHubProjectsFound" in {

      implicit val system = ActorSystem("testsystem", ConfigFactory.parseString("""
  akka.loggers = ["akka.testkit.TestEventListener"]
  """))

      val testProbe = TestProbe()

      val githubProject = UUID.randomUUID().toString

      val driver = system.actorOf(DriverActor.props(maxNumOfProjcts))

      EventFilter.info("No GitHub projects found! Shutting down", occurrences = 1) intercept {
          driver ! NoGitHubProjectsFound
      }

      testProbe.expectNoMessage(6000 millis)
    }
  }

  "The Driver Actor" should {
    "print info to json file when sent PrintJson" in {

      val testProbe = TestProbe()

      val githubProject = UUID.randomUUID().toString

      val driver = system.actorOf(DriverActor.props(maxNumOfProjcts))

      val jsonFile = s"test-print-json-to-file-${DateTime.now}.json"

      driver ! PrintJson(jsonFile)

      Try[Any] {
        val f = new File(jsonFile)
      } match {
        case Success(file) => assert(true)
        case Failure(_) => assert(false)
      }

      testProbe.expectNoMessage(5000 millis)
      shutdown(system)

      assert((new File(jsonFile)).delete())

    }
  }

}
