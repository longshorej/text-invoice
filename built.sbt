name := "text-invoice"

packAutoSettings

version := "0.1.0"

scalaVersion := "2.12.1"

organization := "info.longshore"

scalacOptions ++= Vector(
  "-deprecation",
  "-encoding",
  "UTF-8",
  "-feature",
  "-Xfatal-warnings",
  "-Xlint",
  "-Yno-adapted-args",
  "-Yrangepos",
  "-Ywarn-dead-code",
  "-Ywarn-numeric-widen",
  "-Ywarn-value-discard",
  "-Xfuture",
  "-Ywarn-unused-import",
  "-language:existentials",
  "-language:higherKinds",
  "-language:implicitConversions",
  "-unchecked"
)

libraryDependencies ++= Vector(
  "io.argonaut"   %% "argonaut" % "6.2-RC2",
  "org.scala-sbt" %% "io"       % "1.0.0-M9"
)

scalacOptions in Test ++= Vector("-Yrangepos")

scalacOptions in (Compile, console) ~= (_ filterNot (_ == "-Ywarn-unused-import"))
