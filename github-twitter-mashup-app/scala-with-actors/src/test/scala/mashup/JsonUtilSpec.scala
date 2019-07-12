package mashup

import java.io.File

import mashup.Protocol.ActorShuttingDown
import mashup.JsonUtil._
import org.joda.time.DateTime
import org.scalatest.FlatSpec

import scala.io.Source
import scala.util.{Failure, Success, Try}

class JsonUtilSpec extends FlatSpec {

  import JsonUtil._

  "A proper type" should "can be converted to json string without error" in {

    val someType = Map[String, String]("1" -> "one")

    Try[String] {
      toJson(someType)
    } match {
      case Success(json:String) => assert(json.nonEmpty);
      case Failure(_)           => assert(false)
    }
  }

  "A proper type with subtypes" should "can be converted to json string without error" in {

    val someType = Map[String, String]("1" -> "one")

    val json:String = Try[String] {
      toJson(someType)
    } match {
      case Success(json:String) => assert(json.nonEmpty); json
      case Failure(_)           => assert(false); "[}"
    }

    assert(json.contains("1"))
    assert(json.contains("one"))
    assert(!json.contains("2"))
  }

  "A json string representing a proper type with subtypes" should "be converted to correct type" in {

    type MapS2 = Map[String, String]

    val expected = Map[String, String]("1" -> "one")

    val jsonString = "{\"1\":\"one\"}"

    val map2s:MapS2 = Try[MapS2] {
      fromJson[MapS2](jsonString)
    } match {
      case Success(json:MapS2) => assert(true); json
      case Failure(_)          => assert(false); Map[String, String]()
    }

    assert(map2s == expected)

  }
}
