package mashup

import mashup.ConfigAndFileUtils.loadConfigFile

import scala.util.{Failure, Success}


/**
  * Driver for API mashup of GitHub and Twitter APIs.
  * Allows user to enter a search word for "Reactive" projects on GitHub, then for each project
  * it searchs for tweets that mention it.
  * You should output a summary of each project with a short list of recent tweets, in JSON format.
  */
object AppWithActors {

  type AppConfig = Map[String, Any]

  implicit val appConfig: AppConfig = getConfigProps

  def main(args: Array[String]): Unit = {

    val banner =
      """
    ===========================================================================================
        This App will search Github for top 10 'Reactive' projects (by default) then for each
        project it will then search for a few of the most recent tweets that mention that project.

        You can configure the App via the configuration file 'properties.config' which the App will
        look for in same directory that the uber jar lives.

        You can configure the following details:
            # The Twitter consumer and access tokens
            consumer.token.key=???
            consumer.token.secret=???
            access.token.key=???
            access.token.secret=???
            # A project name to search GitHub - defaul is 'Reactive'
            github.project=Reactive

        When complete any response will be written to 'twits.json' in same directory.

    ===========================================================================================
        """.stripMargin

    println(banner)

    withTiming {
      // no tasks to execute - yet!
    }

  }

  def getConfigProps: AppConfig = {
    var props = loadConfigFile() match {
      case Success(configProps) => configProps
      case Failure(msg) => println(msg); System.exit(0)
    }
    props.asInstanceOf[AppConfig]
  }

  // We could do more logging of metrics here perhaps
  def withTiming(body: => Unit): Unit = {
    // start timing from, wait for it, now...
    val startTime = System.currentTimeMillis
    // now execute the task(s)
    body
    // and record how long it took
    val endTime = System.currentTimeMillis
    // log it
    println(
      s"""
            FINSHED!

            App took ${(endTime - startTime) / 1000} seconds to get results - please check twits-{date-time}.json.
        """)
  }
}


