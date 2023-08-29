val sharedSettings = Seq(scalaVersion := "3.3.0")

val http4sVersion = "0.23.23"

/*
   there should be directory with same name as project name
    htest --- jvm / src/main/scala
           |- js  / src/main/scala
*/
      
lazy val htest = crossProject(JSPlatform, JVMPlatform)
    .crossType(CrossType.Full)
    .settings(sharedSettings)
    .jsSettings(
      // Compile / mainClass := Some("apex.Apex"),   
      libraryDependencies ++= Seq(
        "org.scalablytyped" %%% "apexcharts" % "3.41.1-c6beb6",
      )
    )
    .jvmSettings(
      // Compile / run / mainClass := Some("server.TestServer"),
      libraryDependencies ++= Seq(
        "org.http4s" %% "http4s-dsl" % http4sVersion,
        "org.http4s" %% "http4s-ember-server" % http4sVersion,
        "org.http4s" %% "http4s-ember-client" % http4sVersion,
        "com.lihaoyi" %% "scalatags" % "0.12.0",
        "com.lihaoyi" %% "upickle" % "3.1.2",
      )
    )
