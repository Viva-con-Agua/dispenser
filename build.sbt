
name := """dispenser"""

version := "0.0.1"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

routesGenerator := InjectedRoutesGenerator

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test,
  "com.nulab-inc" %% "play2-oauth2-provider" % "1.2.0",
//  "org.reactivemongo" %% "reactivemongo" % "0.12.1",
  "org.reactivemongo" %% "reactivemongo-play-json" % "0.11.14",
  "org.reactivemongo" %% "play2-reactivemongo" % "0.11.14",
  "org.webjars" %% "webjars-play" % "2.4.0-1",
  "org.webjars" % "react" % "0.13.3"
)
//add mongoDB driver
//libraryDependencies += "org.mongodb.scala" %% "mongo-scala-driver" % "2.0.0"


enablePlugins(sbtdocker.DockerPlugin)
enablePlugins(play.sbt.PlayScala)
//create docker and setup dockerfile
dockerfile in docker := new Dockerfile {
  from("alpine")
}
//scalaWs
libraryDependencies ++= Seq(
  ws
)
name := "dispenser"
version := "0.0.1"
imageNames in docker := Seq(ImageName(s"${name.value}:${version.value}"))


