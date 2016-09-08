package com.gocream.perf.test

import io.gatling.core.Predef._;
import io.gatling.http.Predef._;

class GetScountPlayerList extends Simulation {

  var httpTargetCluster = http.shareConnections.disableCaching
  var constantmaxDurationSeconds:Integer=120;
  var rampmaxDurationSeconds:Integer=20;

  val MicroserviceScenario = scenario("GOCREAM-Get SCOUT Player List")
    .exec(GOCreamService.getScountPlayerList)
    

  before {
    println("Starting Get SCOUT Player List Load injector test")
  }

  setUp(
    MicroserviceScenario
    .inject(rampUsersPerSec(10) to 20 during rampmaxDurationSeconds,
        constantUsersPerSec(2) during (constantmaxDurationSeconds seconds)) 
    .protocols(httpTargetCluster)
    )

  after {
    println("Completed Get SCOUT Player List Load injector test")
  }
}
