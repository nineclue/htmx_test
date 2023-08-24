import org.scalajs.dom
import org.scalajs.dom.document
import scala.scalajs.js
import js.annotation.* 
import org.scalajs.dom.{Event, InputEvent}
import org.scalajs.dom.Element
import org.scalajs.dom.HTMLInputElement

@JSExportTopLevel("Apex")
object Apex:
    @JSExport
    def hello() = 
        println("Hello, world")
        
    @JSExport
    def update(source: String, target: String) = 
        val s = document.getElementById(source).asInstanceOf[HTMLInputElement]
        val t = document.getElementById(target)
        t.innerText = s.value

    @JSExport
    def validateSlider(min: String, max: String, minChanged: Boolean) = 
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
        update(min, min ++ "label")
        update(max, max ++ "label")

    val htmx = js.Dynamic.global.htmx