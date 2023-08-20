scalaVersion := "3.3.0"

val http4sVersion = "0.23.23"

lazy val testDemo = (project in file("."))
    .settings(
      libraryDependencies ++= Seq(
        "org.http4s" %% "http4s-dsl" % http4sVersion,
        "org.http4s" %% "http4s-ember-server" % http4sVersion,
        "org.http4s" %% "http4s-ember-client" % http4sVersion,
        "com.lihaoyi" %% "scalatags" % "0.12.0",
        // "com.raquo" %% "domtypes" % "17.1.0",
      )
    )