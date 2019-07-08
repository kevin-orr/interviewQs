package mashup

object Protocol {

  case object Quit

  case class ActorShuttingDown(id: String)
  
  case object UnknownGitHubFailure
}
