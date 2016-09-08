package com.gocream.perf.test

import io.gatling.core.Predef._;
import io.gatling.http.Predef._;

class GetScountPlayerCount extends Simulation {

  var httpTargetCluster = http.shareConnections.disableCaching
  var constantmaxDurationSeconds:Integer=120;
  var rampmaxDurationSeconds:Integer=20;

  val MicroserviceScenario = scenario("GOCREAM-Get SCOUT Player Count")
    .exec(GOCreamService.getScountPlayerCount)
    

  before {
    println("Starting Get SCOUT Player Count Load injector test")
  }

  setUp(
    MicroserviceScenario
    .inject(rampUsersPerSec(10) to 20 during rampmaxDurationSeconds,
        constantUsersPerSec(2) during (constantmaxDurationSeconds seconds)) 
    .protocols(httpTargetCluster)
    )

  after {
    println("Completed Get SCOUT Player Count Load injector test")
  }
}
