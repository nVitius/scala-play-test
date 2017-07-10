name := """play-scala-seed"""
organization := "com.nvitius"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.2"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.0" % Test
libraryDependencies += "net.ruippeixotog" %% "scala-scraper" % "2.0.0-RC2"

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.nvitius.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.nvitius.binders._"
