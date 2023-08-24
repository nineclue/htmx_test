import org.scalajs.dom
import org.scalajs.dom.document
import scala.scalajs.js
import js.annotation.* 
import org.scalajs.dom.{Event, InputEvent}
import org.scalajs.dom.Element
import org.scalajs.dom.HTMLInputElement

// ApexCharts facade thanksfully made by scalablytyped thansfully, let's bear weird names
import typings.apexcharts.* 
import typings.apexcharts.global.ApexCharts
import typings.apexcharts.ApexCharts.ApexOptions
import typings.apexcharts.anon.*

@JSExportTopLevel("Apex")
object Apex:
    val htmx = js.Dynamic.global.htmx

    @JSExport
    def hello() = 
        println("Hello, world")
        
    private def truncate[A](d: Double, a: A) = String.format("%.1f", d)
    @JSExport
    def setupCharts(scatterId: String, boxId : String) = 
        println("Let's setup charts")

        val baseOption = ApexOptions()
            .setSeries(js.Array())
            .setNoData(ApexNoData().setText("No Data").setStyle(FontFamilyFontSize().setFontSize("20")))
            .setXaxis(ApexXAxis().setType(apexchartsStrings.numeric))
            .setGrid(ApexGrid().setXaxis(Lines().setLines(OffsetYShow().setShow(true))).setYaxis(Lines().setLines(OffsetYShow().setShow(true))))
            .setTooltip(ApexTooltip().setTheme("dark").setY(ApexTooltipY().setFormatter(truncate)))
            .setYaxis(ApexYAxis().setLabels(Align().setFormatter(truncate)))

        val emptyScatter = 
            baseOption.setChart(ApexChart().setId("scatterChart").setType(apexchartsStrings.scatter).setToolbar(AutoSelected().setShow(false)))

        val emptyBox = 
            baseOption.setChart(ApexChart().setId("boxChart").setType(apexchartsStrings.boxPlot).setToolbar(AutoSelected().setShow(false)))

        val scatterDiv = document.getElementById(scatterId)
        val boxDiv = document.getElementById(boxId)
        val scatterChart = ApexCharts(scatterDiv, emptyScatter)
        val boxChart = ApexCharts(boxDiv, emptyBox)
        scatterChart.render()
        boxChart.render()

    @JSExport
    def gatherAndCall() = 
        val values = Seq("min", "max", "count").map((id: String) => document.getElementById(id).asInstanceOf[HTMLInputElement].value)
        val gathered = js.Dynamic.literal(min = values(0), max = values(1), count = values(2))
        val literal = js.Dynamic.literal(handler = updateChartValues, values = gathered)
        htmx.ajax("GET", "/randoms", literal)

    def updateChartValues(resp: scala.scalajs.js.Object) = 
        println(s"Hooray! Update! $resp")

    @JSExport
    def updateSlider(source: String, target: String) = 
        val s = document.getElementById(source).asInstanceOf[HTMLInputElement]
        val t = document.getElementById(target)
        t.innerText = s.value

    @JSExport
    def validateAndUpdateSlider(min: String, max: String, minChanged: Boolean) = 
        val minInput = document.getElementById(min).asInstanceOf[HTMLInputElement]
        val maxInput = document.getElementById(max).asInstanceOf[HTMLInputElement]
        val minValue = minInput.value.toInt
        val maxValue = maxInput.value.toInt
        if minValue > maxValue then
            if minChanged then
                val maxceil = maxInput.getAttribute("max").toInt
                if minValue <= maxceil then
                    maxInput.value = minValue.toString
                else
                    minInput.value = maxceil.toString
            else
                val minfloor = minInput.getAttribute("min").toInt
                if maxValue >= minfloor then
                    minInput.value = maxValue.toString
                else
                    maxInput.value = minfloor.toString
        updateSlider(min, min ++ "label")
        updateSlider(max, max ++ "label")