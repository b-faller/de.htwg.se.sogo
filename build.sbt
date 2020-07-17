import Dependencies._

ThisBuild / scalaVersion     := "2.13.1"
ThisBuild / version          := "0.2.0"
ThisBuild / organization     := "de.htwg.se"

// Disable subfolder in target directory
crossPaths := false

scalacOptions in ThisBuild ++= Seq("-unchecked", "-deprecation", "-feature")

lazy val root = (project in file("."))
  .settings(
    name := "de.htwg.se.sogo",
    libraryDependencies += scalaTest % Test,
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.1.1" % "test",
    libraryDependencies += "org.scala-lang.modules" %% "scala-swing" % "2.1.1",
  )

libraryDependencies += "net.codingwell" %% "scala-guice" % "4.2.9"
libraryDependencies += "com.typesafe.play" %% "play-json" % "2.9.0"
libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "1.3.0"

coverageExcludedPackages := ".*gui.*;.*Sogo.*;.*controllerMockImpl"