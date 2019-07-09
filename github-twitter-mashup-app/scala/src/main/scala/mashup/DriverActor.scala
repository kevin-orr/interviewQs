package mashup

import akka.actor.{Actor, Props}
import mashup.Protocol._

// An actor that acts as a driver
class DriverActor(count: Int)
  extends Actor
    with akka.actor.ActorLogging {

  // drivers state - will be updated tweet info on projects as messages come in
  var metrics : List[ProjectTweetsInfo] = List[ProjectTweetsInfo]()

  override def receive: Receive = {

    // received command to print results as json
    case PrintJson => ConfigAndFileUtils.saveJsonToFile(metrics)
    // received command to print results as json to a specific file
    case PrintJson(filename) => ConfigAndFileUtils.saveJsonToFile(metrics, filename)
    // ok, received signal to add a bunch of tweet info about a project to sate
    case projectTweets: ProjectTweetsInfo => metrics = (projectTweets :: metrics); sender ! Quit
    // informed that no projects found that match criteria
    case NoGitHubProjectsFound => log.info("No GitHub projects found! Shutting down"); sender ! Quit; 
    // received an error from the twitter actor
    case twitterError: BadTwitterResponse => log.info("Recieved error from twitter", twitterError); sender ! Quit
    // received an error from the github actor
    case gitHubError: BadGitHubResponse => log.info("Recieved error from Github", gitHubError); sender ! Quit; 
    // received signal informing that a twitter actor has been shut down
    case ActorShuttingDown(id) => log.info(s"Twitter Actor $id has been shut down")
    // received an unknow error from the github actor
    case UnknownGitHubFailure => log.info("Unknow GitHub response"); sender ! Quit; 
    // everything else just log as error
    case _ => log.info("Got an unknown failure...\n");
  }

}

object DriverActor {

  def props(count:Int): Props = Props(new DriverActor(count))

}
