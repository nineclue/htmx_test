package server

import cats.effect.*
import org.http4s.* 
import org.http4s.dsl.io.*
import com.comcast.ip4s.*
import org.http4s.ember.server.*
import org.http4s.implicits.*
import org.http4s.headers.{Origin, `Content-Type`}
import java.time.LocalTime
import java.time.format.DateTimeFormatter

// installed tailwindcss & daisyui and running 
// npx tailwindcss -i input.css -o src/main/resources/output.css --watch

object TestServer extends IOApp:
    private val textHtmlType = MediaType.unsafeParse("text/html")
    private val timeFormatter = DateTimeFormatter.ofPattern("HH mm")

    import fs2.io.file.Path
    
    val service = HttpRoutes.of[IO] {
        case GET -> Root =>
            Ok(Contents.index.toString, `Content-Type`(textHtmlType))

        case GET -> Root / "clicked" =>
            Ok(LocalTime.now().format(timeFormatter))

        case GET -> Root / "step" / IntVar(i) =>
            Ok(Contents.heroContent(i).toString)

        case request@GET -> Root / "apex.js" =>
            StaticFile.fromPath(Path("./htest/js/target/scala-3.3.0/htest-fastopt/main.js"), Some(request)).getOrElseF(NotFound())

        case request@GET -> Root / "assets" / file =>
            StaticFile.fromResource(file, Some(request)).getOrElseF(NotFound())
        
    }

    def run(as: List[String]): IO[ExitCode] = 
        EmberServerBuilder
            .default[IO]
            .withHost(ipv4"0.0.0.0")
            .withPort(port"7878")
            .withHttpApp(service.orNotFound)
            .build
            .use(_ => IO.never)
            .as(ExitCode.Success)