package com.gocream.perf.test

import io.gatling.core.Predef._;
import io.gatling.http.Predef._;

class GetScountPlayerGamesById extends Simulation {

  var httpTargetCluster = http.shareConnections.disableCaching
  var constantmaxDurationSeconds:Integer=120;
  var rampmaxDurationSeconds:Integer=20;
  val ipfeeder = csv("game-ids.csv").random;
  val ipfeed = feed(ipfeeder)
  
  val MicroserviceScenario = scenario("GOCREAM-Get SCOUT Player Games By Id")
    .exec(ipfeed,GOCreamService.getScountPlayerGamesById)
    

  before {
    println("Starting Get SCOUT Player Games By Id Load injector test")
  }

  setUp(
    MicroserviceScenario
    .inject(rampUsersPerSec(10) to 20 during rampmaxDurationSeconds,
        constantUsersPerSec(2) during (constantmaxDurationSeconds seconds)) 
    .protocols(httpTargetCluster)
    )

  after {
    println("Completed Get SCOUT Player Games By Id Load injector test")
  }
}
