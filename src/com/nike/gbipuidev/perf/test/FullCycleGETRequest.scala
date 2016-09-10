package com.nike.gbipuidev.perf.test

import io.gatling.core.Predef._;
import io.gatling.http.Predef._;


class FullCycleGETRequest extends Simulation {

  var httpTargetCluster = http.shareConnections.disableCaching
  var constantmaxDurationSeconds:Integer=120;
  var rampmaxDurationSeconds:Integer=20;
  

  val MicroserviceScenario = scenario("ALL GET Requests")
    .exec(
        addCookie(
            Cookie("NikeAuthToken", GBIPUIDevServices.nikeAuthToken)
            .withDomain(GBIPUIDevServices.domainName)
            )
    )
    .exec(GBIPUIDevGETServices.GET_ACCOUNT_GENERATE)  
    .exec(GBIPUIDevGETServices.GET_ACCOUNT_INFO)
    .exec(GBIPUIDevGETServices.GET_ACCOUNT_ROLE_USERS)
    .exec(GBIPUIDevGETServices.GET_CPSPUB_SYNC)
    .exec(GBIPUIDevGETServices.GET_CPSPUB_SYNC_REPORTS)
    .exec(GBIPUIDevGETServices.GET_CPSPUB_SYNC_REPORTS_ACTION)
    .exec(GBIPUIDevGETServices.GET_CPSPUB_SYNC_REPORTS_DETAILED)
    .exec(GBIPUIDevGETServices.GET_CPSPUB_SYNC_REPORTS_DETAILED_ACTION)
    .exec(GBIPUIDevGETServices.GET_CPSPUB_SYNC_REPORTS_SUMMARY)
    .exec(GBIPUIDevGETServices.GET_EXPORT_CSV)
    .exec(GBIPUIDevGETServices.GET_HISTORY)
    .exec(GBIPUIDevGETServices.GET_JOB_ATTACHMENT_FILE)
    .exec(GBIPUIDevGETServices.GET_JOB_ATTACHMENTS)
    .exec(GBIPUIDevGETServices.GET_JOB_BY_ID)
    .exec(GBIPUIDevGETServices.GET_JOB_CLAIM_BY_IDS)
    .exec(GBIPUIDevGETServices.GET_JOB_HISTORY_BY_ID)
    .exec(GBIPUIDevGETServices.GET_JOB_SHIPPED)
    .exec(GBIPUIDevGETServices.GET_JOB_UN_CLAIM_BY_IDS)
    .exec(GBIPUIDevGETServices.GET_JOBS)
    .exec(GBIPUIDevGETServices.GET_JOBS_HISTORY_BY_ID)
    .exec(GBIPUIDevGETServices.GET_PRODUCT_ANNOTATION_DETAILS)
    .exec(GBIPUIDevGETServices.GET_PRODUCT_ASSET_DETAILS)
    .exec(GBIPUIDevGETServices.GET_PRODUCT_DETAILS)
    .exec(GBIPUIDevGETServices.GET_PRODUCT_HISTORY_BY_ID)
    .exec(GBIPUIDevGETServices.GET_SEARCH_FACETS)
    .exec(GBIPUIDevGETServices.GET_SEARCH_JOB_FACETS)
    .exec(GBIPUIDevGETServices.GET_SEARCH_PRODUCT_FACETS)
    .exec(GBIPUIDevGETServices.GET_SEARCH_REBUILD)
    .exec(GBIPUIDevGETServices.GET_SHOOT_TYPES_CSV)
    .exec(GBIPUIDevGETServices.GET_VERSION)
    .exec(GBIPUIDevGETServices.GET_VIEW_CODES)
    
  before {
    println("Starting Full Cycle ALL GET Request Load injector test")
  }

  setUp(
    MicroserviceScenario
    .inject(
        constantUsersPerSec(1) during (constantmaxDurationSeconds seconds)) 
    .protocols(httpTargetCluster)
    )

  after {
    println("Completed Full Cycle ALL GET Request Load injector test")
  }
}
