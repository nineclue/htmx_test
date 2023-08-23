package server

import scalatags.Text.all.*

object Contents:
    val index = "<!DOCTYPE html>" + html(
        data.theme := "light",
        head(
            meta(charset := "UTF-8"),
            title := "HTMX / ScalaTags & Http4s / Scala.js & ApexCharts",
            meta(name := "viewport", 
                content := "width=device-width, initial-scale=1.0"),
            script(src := "apex.js"),
            script(src := "/assets/htmx.min.js"),
            link(href := "/assets/htest.css", rel := "stylesheet")
        ),
        body(
            div(cls := "hero min-h-screen bg-base-200", 
            heroContent(0)
            )
        )
    )

    def heroContent(i: Int, dynamicView: Boolean = true, reverse: Boolean = false) = 
        val texts = Seq(
            ("Welcome!", "This is a test app written in scala, using http4s / scala.js / htmx / apexcharts. We'll demo boxplot of random values.", "Next"),
            ("Graph", "Let's make random numbers!", "Make")
        )
        val hclass = "hero-content" ++ (
            (dynamicView, reverse) match
                case (false, _) => ""
                case (true, false) => "flex-col lg:flex-row"
                case (true, true) => "flex-col lg:flex-row-reverse"
        )
        val buttonModifier = 
            if i < 1 then 
                Seq(data.hx.target := "#hcontent", data.hx.get := s"/step/${i + 1}") 
            else 
                Seq(onclick := "Apex.hello()")
        def sliderWithLabel(elmId: String, minVal: Int = 0, maxVal: Int = 100, defVal: Int = 25) = 
            div(cls := "form-control",
                span(label(cls := "label", elmId.capitalize), label(id := s"${elmId}label", cls := "label", defVal)),
                input(id := elmId, tpe := "range", min := minVal, max := maxVal, value := defVal, cls := "range",  step := 25, oninput := s"Apex.update('$elmId', '${elmId}label')"))
        val form = 
            if i < 1 then
                Seq.empty
            else
                Seq(
                    div(cls := "card flex-col shadow-2xl bg-base-100",
                        div(cls := "card-body",
                            sliderWithLabel("min"),
                            sliderWithLabel("max", defVal = 75),
                            div(cls := "form-control",
                                span(label(cls := "label", "Count"), label(id := "countlabel", cls := "label", 5)),
                                input(id := "count", tpe := "range", min := 10, max := 30, value := 20, cls := "range",  step := 5, oninput := "Apex.update('count', 'countlabel')"))
                            )))

        div(id := "hcontent", cls := hclass, 
            h1(cls := "text-5xl font-bold", texts(i)._1),
            p(cls := "py-6", texts(i)._2),
            form,
            button(cls := "btn btn-primary py-2", texts(i)._3)(buttonModifier),
        )

    val dummy = 
        div(
            div(cls := "text-center text-2xl font-bold ", "HTML Test with Scala.js"),
            button(id := "target", 
                cls := "btn btn-accent text-xl object-center",
                data.hx.get := "/clicked", 
                data.hx.trigger := "click", 
                data.hx.target := "#target",
                "안녕? 여러분!!!"),
            button(cls := "btn", onclick := "Apex.hello()", "call scala.js function"),
            blockquote(cls := "text-2xl font-semibold italic text-center text-slate-900",
                "When you look ",
                span(cls := "before:block before:absolute before:-inset-1 before:-skew-y-3 before:bg-pink-500 relative inline-block",
                    span(cls := "relative text-white", "annoyed")),
                " all the time, people think that you're busy."),
            div(cls := "join object-center",
                input(cls := "join-item btn", `type` := "radio", name := "options", aria.label := "스칼라"),
                input(cls := "join-item btn", `type` := "radio", name := "options", aria.label := "고"),
                input(cls := "join-item btn", `type` := "radio", name := "options", aria.label := "파이썬"))

        )