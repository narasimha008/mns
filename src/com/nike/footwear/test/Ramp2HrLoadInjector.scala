package com.nike.footwear.test

import io.gatling.core.Predef._;
import io.gatling.http.Predef._;

class Ramp2HrLoadInjector extends Simulation {

  var httpTargetCluster = http.shareConnections.disableCaching
  //val ipfeeder = csv(DCRSFootwearService.microserviceurls).circular
  //val ipfeed = feed(ipfeeder)
  var maxDuration:Integer=2;
  var loadDuration:Integer=2;

  val MicroserviceScenario = scenario("DCRS FOOT WEAR SERVICES - RAMP AND LOAD")
    .exec(DCRSFootwearService.findAllCountriesService)
    .pause(1)
    .exec(DCRSFootwearService.findAllLocationsByCountry)
    .pause(1)
    .exec(DCRSFootwearService.findExperience)
    .pause(1)
    .exec(DCRSFootwearService.addExperience)
    .pause(1)
    .exec(DCRSFootwearService.getReferencedata)
    .pause(1)
    .exec(DCRSFootwearService.getCalculate)
    .pause(1)
    .exec(DCRSFootwearService.findExperienceInfoById)
    .pause(1)
    .exec(DCRSFootwearService.addExperienceInfo)

  before {
    println("Starting Gatling ramp and load injector test")
    //println("Testing @ " + peakRPS + " UPS with rampTime: " + rampTime)
  }

  setUp(
    MicroserviceScenario
    .inject(constantUsersPerSec(2) during (loadDuration hours)) 
    .protocols(httpTargetCluster)
    ).maxDuration(maxDuration hours)

  after {
    println("Completed Gatling ramp and load injector test")
  }
}
