package com.nike.footwear.test

import io.gatling.core.Predef._;
import io.gatling.http.Predef._;

class Request100Injector extends Simulation {

  var httpTargetCluster = http.shareConnections.disableCaching
  //val ipfeeder = csv(DCRSFootwearService.microserviceurls).circular
  //val ipfeed = feed(ipfeeder)

  val MicroserviceScenario = scenario("DCRS FOOT WEAR SERVICES - 100 REQUEST INJECTOR")
    .repeat(100) { 
	exec(DCRSFootwearService.findAllCountriesService)
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
 }
    

  before {
    println("Starting Gatling request 100 injector test")
  }

  setUp(
    MicroserviceScenario.inject(atOnceUsers(1)).protocols(httpTargetCluster))

  after {
    println("Completed Gatling request 100 injector test")
  }
}
