package com.nike.footwear.test

import io.gatling.core.Predef._;
import io.gatling.http.Predef._;

class FastBreakRampInjector extends Simulation {
  var httpTargetCluster = http.shareConnections.disableCaching
  //val ipfeeder = csv(DCRSFootwearService.microserviceurls).circular
  //val ipfeed = feed(ipfeeder)

  val dcrsFootWearserviceScenario = scenario("DCRS FOOT WEAR SERVICES - FAST BREAk")
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
    println("FAST BREAK INJECTOR TEST STARTED*************");
  }

  setUp(
    dcrsFootWearserviceScenario.inject(rampUsersPerSec(10) to (20) during (10)).protocols(httpTargetCluster))

  after {
    println("FAST BREAK INJECTOR TEST FINISHED******");
  }
}