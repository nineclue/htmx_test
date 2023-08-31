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
3. javascript compile 
    need to install scalablytyped CLI (stc) to make scala.js facade of ApexCharts
    need to install cs to install scalablytyped CLI (stc) [https://scalablytyped.org/docs/cli]
    ```
    npm install apexcharts
    npm install typescript
    stc 
    ```
    sbt htestJS/fastLinkJS
4. server compile & run : sbt htestJVM/run
5. open http://localhost:7878

### screenshot
![Screenshot](https://github.com/nineclue/htmx_test/blob/9faf7441ed944b910f5e229f2975a14681e17865/htmx_test_run.gif)

### bugs & todo
* ~~boxplot is not displayed at first, once zoom in/out of browser it is displayed correctly~~
* ~~display two charts in flex-row~~
