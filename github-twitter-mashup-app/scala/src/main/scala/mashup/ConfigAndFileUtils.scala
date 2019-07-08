package mashup

import java.io.{File, PrintWriter}

import com.danielasfregola.twitter4s.entities.{AccessToken, ConsumerToken}
import org.joda.time.DateTime

import scala.io.Source
import scala.util.Try

object ConfigAndFileUtils {

  // the loan pattern for closing stream etc.
  def using[A <: { def close(): Unit }, B](resource: A)(f: A => B): B =
    try {
      f(resource)
    } finally {
      resource.close()
    }

  // loads up a configuration file with various expected properties.
  // by defaul it will look for a file called config.properties
  def loadConfigFile (configFileName :String = "config.properties") : Try[Map[String,Any]] = Try {

    val properties  = using(Source.fromFile(configFileName)) { source =>
      (for (line <- source.getLines
            if !line.trim.matches("")
            if !line.trim.matches("#.*")) yield line
        ).toList
        .map(s => s.split("=")(0)->s.split("=")(1))
        .toMap
    }

    // so if we got here then we found config file and attemtped to load (possibly none) properties from file
    // However, if we don't have any props then we blow up
    require(!properties.isEmpty, s"Configuration file ${configFileName} cannot be empty")

    // so we got some properties but we need to make sure we have these prefedined props
    val expectedKeys = List("consumer.token.key", "consumer.token.secret", "access.token.key", "access.token.secret")

    // so loop around our expected list of properties and see if we're missing any
    expectedKeys.foreach(property =>
      require(properties.contains(property), s"Missing property: ${property}")
    )

    // alternatively we could have just checked all expected props were present but then if any were missing
    // the error reported wouldn't have detailed which was missing...
    //    require(expectedKeys.filterNot(properties.contains).isEmpty, s"Config file must contain all $expectedKeys")

    // ok, so we've got all our properties and potentially some more so let's return the configuration
    Map("consumer" -> ConsumerToken(properties("consumer.token.key"), properties("consumer.token.secret")),
        "access" -> AccessToken(properties("access.token.key"), properties("access.token.secret")),
        "github.project" -> properties.getOrElse("github.project", "Reactive"),
        "timeout" -> properties.getOrElse("app.wait.timeout", "30"))
  }

  // stream the object to file in json format and return the file name
  def saveJsonToFile(someObject: Any, fileNameToSaveTo :String = s"twits-${DateTime.now}.json"): String = {

    import java.io._

    using (new PrintWriter(new File(fileNameToSaveTo))) { printWriter =>
      printWriter.write(JsonUtil.toJson(someObject))
      // don't forget to return the file name
      fileNameToSaveTo
    }
  }
}
