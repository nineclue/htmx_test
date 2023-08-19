import scala.scalajs.js
import scala.scalajs.js.annotation.*

import org.scalajs.dom

object Demo:
    @main
    def LiveChart(): Unit =
    dom.document.querySelector("#app").innerHTML = s"""
        <div>
        <h1>Hello Scala.js! 반가워요, 스칼라.js</h1>
        <div class="card">
            <button id="counter" type="button"></button>
        </div>
        </div>
    """

    setupCounter(dom.document.getElementById("counter"))
end Test

def setupCounter(element: dom.Element): Unit =
    var counter = 0

    def setCounter(count: Int): Unit =
        counter = count
        element.innerHTML = s"count is $counter"

    element.addEventListener("click", e => setCounter(counter + 1))
    setCounter(0)
end setupCounter