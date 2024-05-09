ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.13"

lazy val root = (project in file("."))
  .settings(
    name := "StrayCat",
    assembly / mainClass := Some("StrayCat"),
    assembly / assemblyJarName := s"${name.value}.jar",
  )
libraryDependencies ++= Seq(
  "org.scala-lang.modules" %% "scala-swing" % "3.0.0",
  "net.portswigger.burp.extensions" % "montoya-api" % "2023.12.1"
)
