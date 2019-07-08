package mashup

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import mashup.ConfigAndFileUtils.loadConfigFile

import scala.util.{Failure, Success}

object AppImplicitsAndConfig {
  // create the ActorSystem so we can create actors
  implicit val system = ActorSystem("mashup")

  implicit val materializer = ActorMaterializer()

  // needed for the future flatMap/onComplete in the end
  implicit val executionContext = system.dispatcher

  // create an alias to save some typing
  type AppConfig = Map[String,Any]

  // create an implicit - some other objects will look for this
  implicit val appConfig: AppConfig = {
    var props = loadConfigFile( ) match {
      case Success(configProps)       => configProps
      case Failure(msg)               => println(msg); System.exit(0)
    }
    props.asInstanceOf[AppConfig]
  }
}