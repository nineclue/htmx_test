This is a test / demo of web app using scala as both backend and frontend

### backend
using http4s as server, scalatags as html rendering

### frontend
* html from server using scalatags
* css using daisyui & tailwindcss
* javascripts : scala.js, htmx, apexcharts (chart drawings)

### make
* server : sbt htestJVM/run
* javascript compile : sbt htestJS/fastLinkJS
* css : npx tailwindcss -i input.css -o htest/jvm/src/main/resources/htest.css
