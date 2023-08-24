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
            script(src := "/assets/htmx.min.js"),
            // script(src := "https://cdn.jsdelivr.net/npm/apexcharts"),
            script(src := "/assets/apexcharts.js"),
            script(src := "apex.js"),
            link(href := "/assets/htest.css", rel := "stylesheet")
        ),
        body(
            div(cls := "hero min-h-screen bg-base-200", 
            heroContent(0)
            )
        )
    )

    def sliderWithLabel(elmId: String, minVal: Int = 0, maxVal: Int = 100, stepVal: Int = 5, defVal: Int = 25, addF: Option[String] = None) = 
        // val fstr = s"(function () { ${addF.getOrElse("")} Apex.update('$elmId', '${elmId}label'); })()"
        val fstr = addF.getOrElse(s"Apex.updateSlider('$elmId', '${elmId}label')")
        div(cls := "form-control",
            span(label(cls := "label", elmId.capitalize), label(id := s"${elmId}label", cls := "label", defVal)),
            input(id := elmId, name := elmId, tpe := "range", min := minVal, max := maxVal, value := defVal, cls := "range",  step := stepVal, 
                oninput := fstr))

    def heroContent(i: Int, dynamicView: Boolean = true, reverse: Boolean = false) = 
        val texts = Seq(
            ("Welcome!", "This is a test app written in scala, using http4s / scala.js / htmx / apexcharts. We'll demo boxplot of random values.", "Next"),
            ("Graph", "Let's make random numbers! You can change the count and the range of random numbers.", "Make")
        )
        val hclass = "hero-content" ++ (
            (dynamicView, reverse) match
                case (false, _) => ""
                case (true, false) => " flex-col lg:flex-row"
                case (true, true) => " flex-col lg:flex-row-reverse"
        )
        val buttonModifier = 
            if i == 0 then 
                Seq(data.hx.target := "#page", data.hx.get := s"/step/${i + 1}", data.hx.swap := "outerHTML") 
            else 
                Seq(onclick := "Apex.gatherAndCall();")
        val formElem = 
            if i == 0 then
                Seq.empty
            else
                Seq(div(cls := "card flex-col shadow-xl bg-base-100 py-3",
                        div(cls := "card-body",
                            sliderWithLabel("min", addF = Some("Apex.validateAndUpdateSlider('min', 'max', true);")),
                            sliderWithLabel("max", defVal = 75, addF = Some("Apex.validateAndUpdateSlider('min', 'max', false);")),
                            sliderWithLabel("count", 10, 30, 2, 20),
                        )))
        val chartElem = 
            if i == 0 then
                Seq.empty
            else
                Seq(
                    div(id := "charts", cls := "flex-col lg:flex-row",
                        div(id := "chart1", cls := "w-4/5 lg:w-2/5"),
                        div(id := "chart2", cls := "w-4/5 lg:w-2/5")
                    ),
                    script("Apex.setupCharts('chart1', 'chart2')")
                )

        div(id := "page", cls := "flex-col", 
            div(id := "hcontent", cls := hclass, 
                h1(cls := "text-5xl font-bold", texts(i)._1),
                p(cls := "py-6", texts(i)._2),
                button(cls := "btn btn-primary ", texts(i)._3)(buttonModifier),
                formElem,
            ),
            chartElem
        )