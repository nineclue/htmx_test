This is a test / demo of web app using scala as both backend and frontend

### backend
using http4s as server, scalatags as html rendering

### frontend
* html from server using scalatags
* css using daisyui & tailwindcss
* javascripts : scala.js, htmx, apexcharts (chart drawings)

### prepare, make & run
1. download htmx.min.js, apexcharts.js and copy these to htest/jvm/src/main/resources
2. css compile : npx tailwindcss -i input.css -o htest/jvm/src/main/resources/htest.css
3. javascript compile : sbt htestJS/fastLinkJS
4. server compile & run : sbt htestJVM/run

### bugs & todo
* ~~boxplot is not displayed at first, once zoom in/out of browser it is displayed correctly~~
* ~~display two charts in flex-row~~