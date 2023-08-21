import cats.effect.*
import org.http4s.* 
import org.http4s.dsl.io.*
import com.comcast.ip4s.*
import org.http4s.ember.server.*
import org.http4s.implicits.*
import org.http4s.headers.{Origin, `Content-Type`}
import scalatags.Text.all.*
// import htmx.HTMX.* 

// installed tailwindcss & daisyui and running 
// ./tailwindcss -i input.css -o src/main/resources/output.css

object TestServer extends IOApp:
    val index = "<!DOCTYPE html>" + html(
        data.theme := "light",
        head(
            meta(charset := "UTF-8"),
            title := "HTMX with ScalaTags & Http4s",
            meta(name := "viewport", 
                content := "width=device-width, initial-scale=1.0"),
            script(src := "/assets/htmx.min.js"),
            link(href := "/assets/output.css", rel := "stylesheet")
        ),
        body(
            button(id := "target", 
                cls := "btn btn-accent text-xl",
                data.hx.get := "/clicked", 
                data.hx.trigger := "click", 
                data.hx.target := "#target",
                "안녕? 여러분!!!"),
            blockquote(cls := "text-2xl font-semibold italic text-center text-slate-900",
                "When you look ",
                span(cls := "before:block before:absolute before:-inset-1 before:-skew-y-3 before:bg-pink-500 relative inline-block",
                    span(cls := "relative text-white", "annoyed")),
                " all the time, people think that you're busy."),
            div(cls := "join",
                input(cls := "join-item btn", `type` := "radio", name := "options", aria.label := "스칼라"),
                input(cls := "join-item btn", `type` := "radio", name := "options", aria.label := "고"),
                input(cls := "join-item btn", `type` := "radio", name := "options", aria.label := "파이썬"))
        )
    )

    private val textHtmlType = MediaType.unsafeParse("text/html")

    val service = HttpRoutes.of[IO] {
        case GET -> Root =>
            Ok(index.toString, `Content-Type`(textHtmlType))

        case GET -> Root / "clicked" =>
            Ok("Welcome to HTMX!")

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