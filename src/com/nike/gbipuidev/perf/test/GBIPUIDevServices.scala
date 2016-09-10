package com.nike.gbipuidev.perf.test;

import io.gatling.core.Predef._;
import io.gatling.http.Predef._;
import io.gatling.http.HeaderNames;
import scala.concurrent.forkjoin.ThreadLocalRandom;

object GBIPUIDevServices {
  
  val csvFileName:String = "BulkJobUodateSimpleTest.csv";  
  val domainName:String = ".nike.com";
  val nikeAuthToken:String = "eyJraWQiOiJ0b2tlbktleSIsImFsZyI6IlJTMjU2In0.eyJpc3MiOiJOaWtlIEdCSVAiLCJhdWQiOiJOaWtlIEdCSVAgVXNlcnMiLCJleHAiOjE0NjI5OTg0MjIsImp0aSI6IlFLZUJYVDc0Uzc2QTQyRG5ITXYyV2ciLCJpYXQiOjE0NjI5OTEyMjIsIm5iZiI6MTQ2Mjk5MTEwMiwic3ViIjoiYWRtaW4iLCJHcm91cHMiOlsiQXBwbGljYXRpb24uQ0RULkRldk9wcy5HQklQLkFydEJ1eWVyIiwiQXBwbGljYXRpb24uQ0RULkRldk9wcy5HQklQLkRpZ2lUZWNoIiwiQXBwbGljYXRpb24uQ0RULkRldk9wcy5HQklQLkltYWdlRWRpdG9yIiwiQXBwbGljYXRpb24uQ0RULkRldk9wcy5HQklQLk1lcmNoT3BzIiwiQXBwbGljYXRpb24uQ0RULkRldk9wcy5HQklQLlBob3RvZ3JhcGhlciIsIkFwcGxpY2F0aW9uLkNEVC5EZXZPcHMuR0JJUC5TdHVkaW9PcHMiLCJBcHBsaWNhdGlvbi5DRFQuRGV2T3BzLkdCSVAuU3R1ZGlvU3R5bGlzdCIsIkFwcGxpY2F0aW9uLkNEVC5EZXZPcHMuR0JJUC5TdXBlcnVzZXIiLCJBcHBsaWNhdGlvbi5FWFRVU1JNR1JVSS5HQklQLkFydEJ1eWVyIiwiQXBwbGljYXRpb24uRVhUVVNSTUdSVUkuR0JJUC5EaWdpVGVjaCIsIkFwcGxpY2F0aW9uLkVYVFVTUk1HUlVJLkdCSVAuSW1hZ2VFZGl0b3IiLCJBcHBsaWNhdGlvbi5FWFRVU1JNR1JVSS5HQklQLk1lcmNoT3BzIiwiQXBwbGljYXRpb24uRVhUVVNSTUdSVUkuR0JJUC5QaG90b2dyYXBoZXIiLCJBcHBsaWNhdGlvbi5FWFRVU1JNR1JVSS5HQklQLlN0dWRpb09wcyIsIkFwcGxpY2F0aW9uLkVYVFVTUk1HUlVJLkdCSVAuU3R1ZGlvU3R5bGlzdCIsIkFwcGxpY2F0aW9uLkVYVFVTUk1HUlVJLkdCSVAuU3VwZXJ1c2VyIiwiYWRtaW5pc3RyYXRvcnMiXX0.GxxPRt6y2pBgHswg6NSHl3z-fVKQtbnlVQ-P67UFD0aeArmhSUW9M8TBqSdAIEjRQ5_bsEHYU0L3q34G3ukBcRjhR93c5h4n9vCdRhaBTzIGOm0SPHfHGWXJGaCA49oznxwheSiN0_Cz2HbfwSkdlLkD0aTZMlkuMYuRwaLGHh7ro-xIujjRdQDFiWYsZ3W8H5lfsGxA1Zc_I02weabyicAOcjDlbtFVjkgLph-9Ib8xilVX9_qF2ZPrpr6tjVOe-q4XEA7XQHkGea-W2fQGhcFXqV4NGkUX34PM8iHLrW7tyj_iaqgB8uD9-aMf4nlt7cs1YLEF3iVMHPzNsnn0cQ";
  val hostLoc = "http://gbipapiuat.sandbox.dbt.svs.nike.com:80/api"
  
  def productIdGenerator() :Long = {
    var startNum = ThreadLocalRandom.current().nextLong(1L,10000000L)
    return startNum
  }
  
  def styleColour() :String = {
    var startNum = ThreadLocalRandom.current().nextLong(100000L,999999L)
    var endNum = ThreadLocalRandom.current().nextLong(100L,999L)
    return startNum+"-"+endNum
  }

  val postCreateJobRequest = exec(
    http("Create Job Request")
      .post(hostLoc+"/jobs")
      .body(StringBody(session => """{"productDBId":"""" + productIdGenerator() + """" ,"priority":0,"businessPartner":"GS","launchDate":"","jobType":"Standard"}""")).asJSON
      .check(status.in(200 to 210),jsonPath("$.id").saveAs("jobId"),jsonPath("$.tasks[0].id").saveAs("taskId")))
      
  var postBrowsingJobsRequestPayLoad = "{\"term\":\"*\",\"filters\":\"\",\"page\":0,\"size\":50}";
  val postBrowsingJobsRequest = exec(
    http("Browsing Jobs Request")
      .post(hostLoc+"/search")
      .body(StringBody(postBrowsingJobsRequestPayLoad)).asJSON
      .check(status.in(200 to 210)))
      
  var postJobsClaimRequestPayLoad = """[{"jobId": "${jobId}","taskId": "${taskId}","username": "gbipsuperuser@gmail.com"}]""";
  val postJobsClaimRequest = exec(
    http("Jobs Claim Request")
      .post(hostLoc+"/jobs/claim")
      .body(StringBody(postJobsClaimRequestPayLoad)).asJSON
      .check(status.in(200 to 210)))

  var postJobsUnClaimRequestPayLoad = """{"jobId": "${jobId}","taskId": "${taskId}"}""";
  val postJobsUnClaimRequest = exec(
    http("Jobs Un Claim Request")
      .post(hostLoc+"/jobs/unclaim")
      .body(StringBody(postJobsUnClaimRequestPayLoad)).asJSON
      .check(status.in(200 to 210)))
      
  var postSearchingJobsRequestPayLoad = """{"term": "${term}","filters": "${filters}","page": ${page},"size": ${size},"sort": "${sort}"}""";
  val postSearchingJobsRequest = exec(
    http("Searching Jobs Request")
      .post(hostLoc+"/search")
      .body(StringBody(postSearchingJobsRequestPayLoad)).asJSON
      .check(status.in(200 to 210)))
      
  var postJobsCompletePayLoad="""{
      "tasksToComplete":
      [
        {
          "jobId": "${jobId}",
          "taskId": "${taskId}"
        }
      ]
    }""";
   val postJobsCompleteRequest = exec(
    http("Jobs Complete Request")
      .post(hostLoc+"/jobs/complete")
      .body(StringBody(postJobsCompletePayLoad)).asJSON
      .check(status.in(200 to 210)))
  
  val getHistory = exec(
    http("Get History")
      .get(hostLoc+"/history?page=5&pageSize=10")
      .check(status.in(200 to 210)))
}