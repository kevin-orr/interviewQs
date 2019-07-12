package mashup

import mashup.ConfigAndFileUtils.loadConfigFile
import mashup.Protocol._

import java.util.concurrent.{CountDownLatch, TimeUnit}

import scala.util.{Failure, Success}


/**
  * Driver for API mashup of GitHub and Twitter APIs.
  * Allows user to enter a search word for "Reactive" projects on GitHub, then for each project
  * it searchs for tweets that mention it.
  * You should output a summary of each project with a short list of recent tweets, in JSON format.
  */
object AppWithActors {

  def main(args: Array[String]): Unit = {

    val banner =
      """
    ===============================================================================================
        This App will search Github for the top 10 'Reactive' (by default) projects, then for each
        project it will search for a few of the most recent tweets that mention that project.

        You can configure the App via the configuration file 'config.properties' which the App will
        look for in same directory that the uber jar lives.

        You can configure the following details:
            # The Twitter consumer and access tokens - these are required to run app
            consumer.token.key=???
            consumer.token.secret=???
            access.token.key=???
            access.token.secret=???
            # A project name to search GitHub - optional - defaul is 'Reactive'
            github.project=Reactive
            # A timeout, in seconds, to wait for all results to come back - optional - default is 30 
            app.wait.timeout=20 
            

        When complete any response will be written to 'twits-<timestamp>.json' in same directory, 
        for example: twits-2018-12-03T21:16:39.741Z.json

    =================================================================================================
        """.stripMargin

    println(banner)

    withTiming {
      // no. of projects
      val maxNumOfProjects = 10

      // as we're running as fire and forget once off app we need a way to sync up all twitter responses
      // so that we're not hanging around for 30 secs or whatever
      val doneSignal = new CountDownLatch(maxNumOfProjects)

      import AppImplicitsAndConfig._

      val driver = system.actorOf(DriverActor.props(maxNumOfProjects), name = "driver")

      val github = system.actorOf(GitHubActor.props(driver)(maxNumOfProjects)(doneSignal), name = "github")

      val githubProject = appConfig("github.project").asInstanceOf[String]

      // lets get the list of Reactive project names from github and fire off the search of twitter
      github ! SearchGitHubFor(githubProject)

      println(s"The App will wait at most ${appConfig("timeout").toString.toInt} seconds (see config.properties) to gather responses before shutting down.")

      // main app has to wait until either it gets a signal that all work is donw or we timeout waiting for results
      // even if we timeout we may have got some (mabe not all) results
      doneSignal.await(appConfig("timeout").toString.toInt, TimeUnit.SECONDS)

      driver ! PrintJson

      system.terminate()
    }

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


