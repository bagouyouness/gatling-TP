

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._
import io.gatling.app.Gatling

class GatlingTP1 extends Simulation {
  val httpProtocol = http
    .baseUrl("http://computer-database.gatling.io")
    .proxy(Proxy("10.0.3.102", 8080).httpsPort(8080))
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
    .doNotTrackHeader("1")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")
  
 	val scn = scenario("prototype")
		.exec(http("prototype_0")
			.get("/computers"))
		.pause(3)
		.exec(http("prototype_1")
			.get("/computers/new"))
		.pause(15)
		.exec(http("prototype_3")
			.post("/computers")
			.formParam("name", "HP pro 2500")
			.formParam("introduced", "2019-01-01")
			.formParam("discontinued", "2019/01/01")
			.formParam("company", "1")
			.check(status.is(400)))
		.pause(6)
		.exec(http("prototype_6")
			.get("/computers/516"))
		.pause(1)
		.exec(http("prototype_7")
			.post("/computers/516/delete"))

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}