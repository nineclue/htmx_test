package server

import cats.effect.*
import org.http4s.* 
import org.http4s.dsl.io.*
import com.comcast.ip4s.*
import org.http4s.ember.server.*
import org.http4s.implicits.*
import org.http4s.headers.{Origin, `Content-Type`}
import upickle.default.write
import org.http4s.dsl.impl.QueryParamDecoderMatcher

// installed tailwindcss & daisyui using npm install -D 
// npx tailwindcss -i input.css -o htest/jvm/src/main/resources/htest.css

object TestServer extends IOApp:
    private val textHtmlType = MediaType.unsafeParse("text/html")

    import fs2.io.file.Path
    
    object MinMatcher extends QueryParamDecoderMatcher[Int]("min")
    object MaxMatcher extends QueryParamDecoderMatcher[Int]("max")
    object CountMatcher extends QueryParamDecoderMatcher[Int]("count")

    val service = HttpRoutes.of[IO] {
        case GET -> Root =>
            Ok(Contents.index.toString, `Content-Type`(textHtmlType))

        // contents for multiple pages, this project uses only step 0 & 1
        case GET -> Root / "step" / IntVar(i) if (i >= 0 && i <= 1) =>
            Ok(Contents.heroContent(i).toString)

        // called by step / 1 route, make random numbers and returns json with the numbers & stats
        case request@GET -> Root / "randoms" :? MinMatcher(min) +& MaxMatcher(max) +& CountMatcher(count) =>
            // println(s"let's make random numbers : $min $max $count")
            val rs = Data.randoms(min, max, count)
            val (p5, lows, highs, _) = BoxData(rs)
            Ok(write(Map("values" -> rs, "boxdata" -> p5, "outliers" -> (lows ++ highs))))

        // compiled scala.js code (Apex.scala)
        case request@GET -> Root / "apex.js" =>
            StaticFile.fromPath(Path("./htest/js/target/scala-3.3.0/htest-fastopt/main.js"), Some(request)).getOrElseF(NotFound())

        // css & js files
        case request@GET -> Root / "assets" / file =>
            StaticFile.fromResource(file, Some(request)).getOrElseF(NotFound())
    }

    private val portNum = 7878
    def run(as: List[String]): IO[ExitCode] = 
        EmberServerBuilder
            .default[IO]
            .withHost(ipv4"0.0.0.0")
            .withPort(Port.fromInt(portNum).get)
            .withHttpApp(service.orNotFound)
            .build
            .use(_ => IO.print(s"Running server.\nVisit at http://localhost:$portNum\n") *> IO.never)
            .as(ExitCode.Success)