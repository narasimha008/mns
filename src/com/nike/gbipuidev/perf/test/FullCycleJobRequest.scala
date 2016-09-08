package com.nike.gbipuidev.perf.test

import io.gatling.core.Predef._;
import io.gatling.http.Predef._;


class FullCycleJobRequest extends Simulation {

  var httpTargetCluster = http.shareConnections.disableCaching
  var constantmaxDurationSeconds:Integer=120;
  var rampmaxDurationSeconds:Integer=20;
  

  val MicroserviceScenario = scenario("Create Job Request")
    .exec(
        addCookie(
            Cookie("NikeAuthToken", GBIPUIDevServices.nikeAuthToken)
            .withDomain(GBIPUIDevServices.domainName)
            )
    )
    .pause(1)
    .exec(GBIPUIDevServices.postCreateJobRequest)  
    .pause(5)
    .exec(GBIPUIDevServices.postJobsClaimRequest)
    .pause(5)
    .exec(GBIPUIDevServices.postJobsUnClaimRequest)
    .pause(1)
    .exec(GBIPUIDevServices.postJobsCompleteRequest)
    .pause(1)
    .exec(GBIPUIDevServices.postBrowsingJobsRequest)
    
  before {
    println("Starting Full Cycle Request Load injector test")
  }

  setUp(
    MicroserviceScenario
    .inject(
        constantUsersPerSec(1) during (constantmaxDurationSeconds seconds)) 
    .protocols(httpTargetCluster)
    )

  after {
    println("Completed Full Cycle Request Load injector test")
  }
}
