
name := "client-game"

version := "0.1"

scalaVersion := "2.12.4"
resolvers += Opts.resolver.sonatypeSnapshots
resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"

val akkaV = "2.5.8"
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-stream" % "2.5.8",
  "com.typesafe.akka" %% "akka-http" % "10.0.11",
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.0.11",
  "com.typesafe.akka" %% "akka-stream-testkit" % "2.5.8" % Test,
  "com.typesafe.akka" %% "akka-http-testkit" % "10.0.11" % Test,
  "org.scalafx" %% "scalafx" % "8.0.144-R12",
"org.scalatest" %% "scalatest" % "3.0.0" % Test
)

testOptions in Test := Seq(Tests.Argument(TestFrameworks.JUnit, "-a", "-v"))
javaOptions in Test += "-Dconfig.file=conf/application.test.conf"
javaOptions := Seq("-Xdebug", "-Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005")

unmanagedResourceDirectories in Compile += (baseDirectory.value / "conf")
