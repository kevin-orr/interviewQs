name := "git-twit"

version := "0.1"

scalaVersion := "2.12.7"

exportJars := true

mainClass in (Compile, packageBin) := Some("mashup.AppWithActors")

// set the main class for the main 'run' task
// change Compile to Test to set it for 'test:run'
mainClass in (Compile, run) := Some("mashup.AppWithActors")


resolvers += "Maven central" at "http://repo1.maven.org/maven2/"

lazy val akkaVersion = "2.5.12"

libraryDependencies ++= Seq(
  "com.danielasfregola" %% "twitter4s" % "5.5",
  "com.typesafe.akka" %% "akka-actor" % "2.5.18",
  "com.typesafe.akka" %% "akka-testkit" % "2.5.18" % Test,
  "com.typesafe.akka" %% "akka-http" % "10.1.5",
  "com.typesafe.akka" %% "akka-http-testkit" % "10.1.5" % Test,
  "com.typesafe.akka" %% "akka-stream" % "2.5.18",
  "com.typesafe.akka" %% "akka-stream-testkit" % "2.5.18" % Test,
  "org.scalatest" %% "scalatest" % "3.0.1" % "test",
  "com.fasterxml.jackson.core" % "jackson-databind" % "2.9.7",
  "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.9.7"
)
