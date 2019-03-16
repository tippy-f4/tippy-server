name := """server"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.8"

lazy val defaultDependencies = {
  val scalikeJdbcDependencies = {
    val scalikeJdbcVersion = "3.2.3"
    List(
      "mysql" % "mysql-connector-java" % "5.1.36",
      "org.scalikejdbc" %% "scalikejdbc"        % scalikeJdbcVersion,
      "org.scalikejdbc" %% "scalikejdbc-config" % scalikeJdbcVersion,
      "org.scalikejdbc" %% "scalikejdbc-play-initializer" % "2.6.0-scalikejdbc-3.2"
    )
  }
  
  List(guice, jdbc) ++ scalikeJdbcDependencies
}

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.1" % Test
libraryDependencies ++= defaultDependencies

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.example.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.example.binders._"
