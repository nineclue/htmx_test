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