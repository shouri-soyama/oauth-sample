import sbt._
import Keys._
import org.scalatra.sbt._
import org.scalatra.sbt.PluginKeys._

object HaxeElmSampleBuild extends Build {
  val Organization = "youku_s"
  val Name = "OAuth Sample"
  val Version = "0.1.0-SNAPSHOT"
  val ScalaVersion = "2.11.6"
  val ScalatraVersion = "2.3.1"

  lazy val project = Project (
    "haxe-elm-sample",
    file("."),
    settings = ScalatraPlugin.scalatraSettings ++ Seq(
      organization := Organization,
      name := Name,
      version := Version,
      scalaVersion := ScalaVersion,
      resolvers ++= Seq(
        "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"
      ),
      libraryDependencies ++= Seq(
        "javax.servlet" % "javax.servlet-api" % "3.1.0",
        "org.eclipse.jetty" % "jetty-webapp" % "9.1.5.v20140505" % "container",
        "com.typesafe" % "config" % "0.4.0",
        "org.json4s" %% "json4s-ext" % "3.2.11",
        "org.json4s" %% "json4s-jackson" % "3.2.11",
        "org.scalatra" %% "scalatra" % ScalatraVersion,
        "org.scalatra" %% "scalatra-json" % ScalatraVersion,
        "com.google.api-client" % "google-api-client" % "1.20.0",
        "com.google.apis" % "google-api-services-oauth2" % "v2-rev93-1.20.0",
        "com.google.http-client" % "google-http-client-jackson" % "1.20.0"
      ),
      ivyScala := ivyScala.value map { _.copy(overrideScalaVersion = true) },
      scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")
    )
  )
}
