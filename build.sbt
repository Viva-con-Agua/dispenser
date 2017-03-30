name := """dispenser"""

version := "0.0.1"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test
)

//Plugins for docker
addSbtPlugin("se.marcuslonnberg" % "sbt-docker" % "1.4.1")

//mustache plugin
resolvers += Resolver.url(
  "bintray-sbt-plugin-michaelallen",
  url("https://dl.bintray.com/michaelallen/sbt-plugins/")
)(Resolver.ivyStylePatterns)

resolvers += "bintray-maven-michaelallen" at "https://dl.bintray.com/michaelallen/maven/"

addSbtPlugin("io.michaelallen.mustache" %% "sbt-mustache" % "0.2")

enablePlugins(sbtdocker.DockerPlugin)

//create docker and setup dockerfile
dockerfile in docker := new Dockerfile {
  from("alpine")
}
name := "dispenser"
version := "0.0.1"
imageNames in docker := Seq(ImageName(s"${name.value}:${version.value}"))
