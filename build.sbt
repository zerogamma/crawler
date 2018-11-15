lazy val xml_parsing_snippets = (project in file(".")).
  settings(

    organization := "com.agileengine",
    name := "xml",
    version := "0.0.1",
    scalaVersion := "2.11.8",
    mainClass in (Compile,packageBin) := Some("crawler"),

    credentials += Credentials(Path.userHome / ".sbt" / "credentials"),

    resolvers ++= Seq(
      Resolver.sonatypeRepo("releases"),
      Resolver.jcenterRepo
    ),

    addCompilerPlugin(
      "org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full
    ),

    //util dependencies
    libraryDependencies ++= Seq(
      "org.jsoup" % "jsoup" % "1.11.2",
      "com.typesafe.scala-logging" %% "scala-logging" % "3.9.0",
      "org.slf4j" % "slf4j-simple" % "1.6.4"
    ),

    fork in Test := true,
    parallelExecution in Test := false,
    javaOptions ++= Seq("-Xms512M", "-Xmx2048M", "-XX:MaxPermSize=2048M", "-XX:+CMSClassUnloadingEnabled")
  )