package com.nike.footwear.test;

import io.gatling.core.Predef._;
import io.gatling.http.Predef._;

object DCRSFootwearService {

  val baseURL = "https://dcrsbracompositeservice-developer.sandbox.dbt.nikecloud.com/v1/";

  //this method is to load all countries
  val findAllCountriesService = exec(
    http("DCRS - find all countries")
      .get(baseURL + "countries")
      .check(status.in(200 to 210)))

  // this method is to load US locations
  val findAllLocationsByCountry = exec(
    http("DCRS - find locations by country")
      .get(baseURL + "anonymous/locations?alpha2=US")
      .check(status.in(200 to 210)))

  //this method is to get expereince via options    
  val findExperience = exec(
    http("DCRS - get experience via options")
      .options(baseURL + "anonymous/experience")
      .check(status.in(200 to 210)))

  var experiencePayLoad = "{\"locale\":\"en_us\",\"locationId\":\"339EF669C2474B2EE05336680C0A6639\",\"version\":\"1.0\"}";
  //this method is to add experience      
  val addExperience = exec(
    http("DCRS - adding experience")
      .post(baseURL)
      .body(StringBody(experiencePayLoad)).asJSON
      .check(status.in(200 to 210)))

  // this method is to get referencedata by location and country
  val getReferencedata = exec(
    http("DCRS - get referencedata by location and country")
      .get(baseURL + "anonymous/referencedata?locationId=339EF669C2474B2EE05336680C0A6639&locale=en_us&includeProducts=false")
      .check(status.in(200 to 210)))

  // this method is to CALCULATE data by region,rib,unit
  val getCalculate = exec(
    http("DCRS - CALCULATE data by region,rib,unit")
      .get(baseURL + "calculate?rib=26&cup=6&unit=IN&impact=Medium&region=usa")
      .check(status.in(200 to 210)))

  //this method is to get expereince via options    
  val findExperienceInfoById = exec(
    http("DCRS - get experience by id via options")
      .options(baseURL + "anonymous/experience/9e7040f9-ed6d-40e5-bbe6-d9d6961f3459")
      .check(status.in(200 to 210)))

  var experienceInfoPayLoad = "{\"completionDate\":\"2016-06-10T18:45:53.140Z\",\"braRegion\":\"usa\",\"ribSize\":26,\"cupSize\":6,\"measurementUnits\":\"IN\",\"braSize\":\"30B\",\"alphaSize\":\"XS\",\"supportLevelId\":\"MEDIUM\",\"purchased\":true}";
  //this method is to add experience      
  val addExperienceInfo = exec(
    http("DCRS - adding experience info")
      .put(baseURL + "anonymous/experience/9e7040f9-ed6d-40e5-bbe6-d9d6961f3459")
      .body(StringBody(experienceInfoPayLoad)).asJSON
      .check(status.in(200 to 210)))

}