name := """dispenser"""
organization := "com.example"

version := Option(System.getProperty("version")).getOrElse("0.2.5-dev")
//version := "0.2.3"

lazy val root = (project in file(".")).enablePlugins(PlayScala).enablePlugins(SbtWeb)

scalaVersion := "2.12.4"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test


libraryDependencies ++= Seq(
  "org.reactivemongo" %% "play2-reactivemongo" % "0.12.6-play26",
	//"com.github.tototoshi" %% "play-scalate" % "0.3.0",
  "org.scalatra.scalate" %% "scalate-core" % "1.8.0",
  "org.scala-lang" % "scala-compiler" % scalaVersion.value
)

unmanagedResourceDirectories in Compile += baseDirectory.value / "app" / "views"


includeFilter in (Assets, LessKeys.less) := "*.less"
excludeFilter in (Assets, LessKeys.less) := "_*.less"



//Docker
maintainer in Docker := "Dennis Kleber"
dockerExposedPorts := Seq(9000, 9443)
dockerRepository := Some("vivaconagua")
routesGenerator := InjectedRoutesGenerator



// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.example.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.example.binders._"
