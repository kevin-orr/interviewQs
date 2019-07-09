package mashup

import akka.http.scaladsl.model.StatusCode

object Protocol {
  // signal to stop doing stuff
  case object Quit

  // signal to print json to some file
  case class PrintJson(file2PrintJsonTo: String)

  // 
  case class GitHubProject(name: String, description: String)

  // signal that indicates no project found
  case object NoGitHubProjectsFound

  // signal to inform actor is shutting down
  case class ActorShuttingDown(id: String)

  // signal to start twitter search for some project anem and description
  case class TwitterSearch(projectNameWithDescription: GitHubProject)

  // signal representing twitter info relating to a project
  case class ProjectTweetsInfo(gitHubProjectInfo: GitHubProject, tweetInfo: List[String]) {
    override def toString: String = {
      StringBuilder
        .newBuilder
        .append("\n")
        .append(s"+++++++++++++++++ ${gitHubProjectInfo.name} ++++++++++++++++++++++++++++++++++++\n")
        .append(s"Project Description:\n")
        .append(s"${gitHubProjectInfo.description}\n")
        .append(s"${tweetInfo.size} recent tweets on ${gitHubProjectInfo.name}:\n")
        .append(tweetInfo.map(txt => s"#Text:... ${txt}").mkString(sep = "\n"))
        .toString()
    }
  }

  // signal to start searching github
  case class SearchGitHubFor(projectName: String)

  // signal representing some kind of bad repsonse from github
  case class BadGitHubResponse(httpCode:StatusCode){
    override def toString: String = {
      s"GitHub returned an error so shutting down: HTTP code=$httpCode\n"
    }
  }

  // signal representing some kind of bad repsonse from twitter
  case class BadTwitterResponse(msg:String) {
    override def toString: String = {
      s"Twitter search error: $msg\n"
    }
  }

  // signal represeting something really bad from github
  case object UnknownGitHubFailure
}

