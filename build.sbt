import Dependencies._

ThisBuild / scalaVersion     := "2.13.1"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "de.htwg.se"

scalacOptions in ThisBuild ++= Seq("-unchecked", "-deprecation", "-feature")

lazy val root = (project in file("."))
  .settings(
    name := "de.htwg.se.sogo",
    libraryDependencies += scalaTest % Test,
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.1.1" % "test"
  )

libraryDependencies += "net.codingwell" %% "scala-guice" % "4.2.9"

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
