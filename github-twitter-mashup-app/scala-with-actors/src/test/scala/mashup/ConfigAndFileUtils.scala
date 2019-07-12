package mashup

import java.io.File

import mashup.Protocol.ActorShuttingDown
import mashup.JsonUtil._
import org.joda.time.DateTime
import org.scalatest.FlatSpec

import scala.io.Source
import scala.util.{Failure, Success, Try}

class ConfigAndFileUtilsSpec extends FlatSpec {

  import ConfigAndFileUtils._

  "A json file" should "be saved when given name" in {

    val command = ActorShuttingDown(123.toString)

    val jsonFileName = s"${DateTime.now}.json"

    saveJsonToFile(command, jsonFileName)

    Try[Any] {
      val f = new File(jsonFileName)
    } match {
      case Success(file) => assert(true);
      case Failure(_) => assert(false)
    }

    assert((new File(jsonFileName)).delete())
  }

  "A json file" should "be saved when name not explicitly given" in {

    val command = ActorShuttingDown(123.toString)

    val jsonFileName = saveJsonToFile(command)

    Try[Any] {
      val f = new File(jsonFileName)
    } match {
      case Success(file) => assert(true)
      case Failure(_) => assert(false)
    }
    assert((new File(jsonFileName)).delete())
  }

  "A json file that is saved" should "be represent valid json" in {

    val command = ActorShuttingDown(123.toString)

    val jsonFileName = saveJsonToFile(command)

    val jsonString = Try[String] {
      Source.fromFile(jsonFileName).mkString
    } match {
      case Success(str:String) => assert(str.nonEmpty); str
      case Failure(_)          => assert(false); "[}"
    }

    val t = fromJson[ActorShuttingDown](jsonString)

    assert(t.id == "123")

  }

  "A type that is saved" should "be valid json" in {

    val expected = Map[String,String]("1" -> "one")

    val jsonFileName = saveJsonToFile(expected)

    val jsonString = Try[String] {
      Source.fromFile(jsonFileName).mkString
    } match {
      case Success(str:String) => assert(str.nonEmpty); str
      case Failure(_)          => assert(false); "[}"
    }

    val t = fromJson[Map[String,String]](jsonString)

    assert(t == expected)

  }

  "A badly formed config.properties missing a property" should "fail to load" in {

      ConfigAndFileUtils.loadConfigFile("./src/test/resources/missing-prop-config.properties")
        match {
          // expect a failure
          case Failure(ex:IllegalArgumentException) => assert(ex.getMessage.contains("Missing property: access.token.secret"))
            // anything else is bad mojo and we should fail
          case _ => assert(false, "We shouldn't get here as config file has a missing property")
        }

  }
}
