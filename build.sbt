val sharedSettings = Seq(scalaVersion := "3.3.0")

lazy val demo =
  // select supported platforms
  crossProject(JSPlatform, JVMPlatform)
    .crossType(CrossType.Full) // [Pure, Full, Dummy], default: CrossType.Full
    .settings(sharedSettings)
    .jsSettings(
    ) // defined in sbt-scalajs-crossproject
    .jvmSettings(/* ... */)


// Optional in sbt 1.x (mandatory in sbt 0.13.x)
lazy val demoJS     = demo.js
lazy val demoJVM    = demo.jvm
