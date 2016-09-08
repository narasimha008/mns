package com.nike.gbipuidev.perf.test

import io.gatling.core.Predef._;
import io.gatling.http.Predef._;

class PostSearchJobRequest extends Simulation {

  var httpTargetCluster = http.shareConnections.disableCaching
  var constantmaxDurationSeconds:Integer=120;
  var rampmaxDurationSeconds:Integer=20;
  
  val inputfeeder = csv(GBIPUIDevServices.csvFileName).random;
  val inputfeed = feed(inputfeeder)

  val MicroserviceScenario = scenario("Browse Job Request")
    .exec(
        addCookie(
            Cookie("NikeAuthToken", GBIPUIDevServices.nikeAuthToken)
            .withDomain(GBIPUIDevServices.domainName)
            )
    )
    .pause(1)
    .exec(inputfeed,GBIPUIDevServices.postSearchingJobsRequest)    

  before {
    println("Starting Search Job Request Load injector test")
  }

  setUp(
    MicroserviceScenario
    .inject(rampUsersPerSec(10) to 20 during rampmaxDurationSeconds,
        constantUsersPerSec(2) during (constantmaxDurationSeconds seconds)) 
    .protocols(httpTargetCluster)
    )

  after {
    println("Completed Search Job Request Load injector test")
  }
}
