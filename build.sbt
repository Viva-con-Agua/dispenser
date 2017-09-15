import com.typesafe.sbt.packager.docker.Cmd

name := """dispenser"""

version := "0.1.2"

lazy val root = (project in file(".")).enablePlugins(PlayScala).enablePlugins(DockerPlugin)

scalaVersion := "2.11.7"

routesGenerator := InjectedRoutesGenerator

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
	"com.typesafe.play" %% "play-mailer" % "3.0.1",
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test,
  "com.nulab-inc" %% "play2-oauth2-provider" % "1.2.0",
//  "org.reactivemongo" %% "reactivemongo" % "0.12.1",
  "org.reactivemongo" %% "reactivemongo-play-json" % "0.11.14",
  "org.reactivemongo" %% "play2-reactivemongo" % "0.11.14",
  "org.webjars" %% "webjars-play" % "2.4.0-1",
  "org.webjars" % "react" % "0.13.3",
  "com.github.tototoshi" %% "play-scalate" % "0.3.0",
  "org.scalatra.scalate" %% "scalate-core" % "1.7.1",
  "org.scala-lang" % "scala-compiler" % scalaVersion.value,
	"net.codingwell" %% "scala-guice" % "4.0.0",
	"net.ceedubs" %% "ficus" % "1.1.2",
	//"org.webjars" % "bootstrap" % "3.3.4",
	"com.adrianhurt" %% "play-bootstrap" % "1.2-P25-B3"

)
//add mongoDB driver
//libraryDependencies += "org.mongodb.scala" %% "mongo-scala-driver" % "2.0.0"

//includeFilter in (Assets, LessKeys.less) := "vca.less" // | "bar.less"
includeFilter in (Assets, LessKeys.less) := "*.less"
excludeFilter in (Assets, LessKeys.less) := "_*.less"

enablePlugins(play.sbt.PlayScala)
//create docker and setup dockerfile

unmanagedResourceDirectories in Compile += baseDirectory.value / "app" / "views"

//scalaWs
libraryDependencies ++= Seq(
  ws
)

maintainer in Docker := "Dennis Kleber"

dockerExposedPorts := Seq(9000, 9443)

dockerRepository := Some("vivaconagua")
