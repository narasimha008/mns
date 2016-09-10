package com.nike.gbipuidev.perf.test;

import io.gatling.core.Predef._;
import io.gatling.http.Predef._;
import io.gatling.http.HeaderNames;
import scala.concurrent.forkjoin.ThreadLocalRandom;

object GBIPUIDevGETServices {

  val csvFileName: String = "BulkJobUodateSimpleTest.csv";
  val domainName: String = ".nike.com";
  val nikeAuthToken: String = "eyJraWQiOiJ0b2tlbktleSIsImFsZyI6IlJTMjU2In0.eyJpc3MiOiJOaWtlIEdCSVAiLCJhdWQiOiJOaWtlIEdCSVAgVXNlcnMiLCJleHAiOjE0NjI5OTg0MjIsImp0aSI6IlFLZUJYVDc0Uzc2QTQyRG5ITXYyV2ciLCJpYXQiOjE0NjI5OTEyMjIsIm5iZiI6MTQ2Mjk5MTEwMiwic3ViIjoiYWRtaW4iLCJHcm91cHMiOlsiQXBwbGljYXRpb24uQ0RULkRldk9wcy5HQklQLkFydEJ1eWVyIiwiQXBwbGljYXRpb24uQ0RULkRldk9wcy5HQklQLkRpZ2lUZWNoIiwiQXBwbGljYXRpb24uQ0RULkRldk9wcy5HQklQLkltYWdlRWRpdG9yIiwiQXBwbGljYXRpb24uQ0RULkRldk9wcy5HQklQLk1lcmNoT3BzIiwiQXBwbGljYXRpb24uQ0RULkRldk9wcy5HQklQLlBob3RvZ3JhcGhlciIsIkFwcGxpY2F0aW9uLkNEVC5EZXZPcHMuR0JJUC5TdHVkaW9PcHMiLCJBcHBsaWNhdGlvbi5DRFQuRGV2T3BzLkdCSVAuU3R1ZGlvU3R5bGlzdCIsIkFwcGxpY2F0aW9uLkNEVC5EZXZPcHMuR0JJUC5TdXBlcnVzZXIiLCJBcHBsaWNhdGlvbi5FWFRVU1JNR1JVSS5HQklQLkFydEJ1eWVyIiwiQXBwbGljYXRpb24uRVhUVVNSTUdSVUkuR0JJUC5EaWdpVGVjaCIsIkFwcGxpY2F0aW9uLkVYVFVTUk1HUlVJLkdCSVAuSW1hZ2VFZGl0b3IiLCJBcHBsaWNhdGlvbi5FWFRVU1JNR1JVSS5HQklQLk1lcmNoT3BzIiwiQXBwbGljYXRpb24uRVhUVVNSTUdSVUkuR0JJUC5QaG90b2dyYXBoZXIiLCJBcHBsaWNhdGlvbi5FWFRVU1JNR1JVSS5HQklQLlN0dWRpb09wcyIsIkFwcGxpY2F0aW9uLkVYVFVTUk1HUlVJLkdCSVAuU3R1ZGlvU3R5bGlzdCIsIkFwcGxpY2F0aW9uLkVYVFVTUk1HUlVJLkdCSVAuU3VwZXJ1c2VyIiwiYWRtaW5pc3RyYXRvcnMiXX0.GxxPRt6y2pBgHswg6NSHl3z-fVKQtbnlVQ-P67UFD0aeArmhSUW9M8TBqSdAIEjRQ5_bsEHYU0L3q34G3ukBcRjhR93c5h4n9vCdRhaBTzIGOm0SPHfHGWXJGaCA49oznxwheSiN0_Cz2HbfwSkdlLkD0aTZMlkuMYuRwaLGHh7ro-xIujjRdQDFiWYsZ3W8H5lfsGxA1Zc_I02weabyicAOcjDlbtFVjkgLph-9Ib8xilVX9_qF2ZPrpr6tjVOe-q4XEA7XQHkGea-W2fQGhcFXqV4NGkUX34PM8iHLrW7tyj_iaqgB8uD9-aMf4nlt7cs1YLEF3iVMHPzNsnn0cQ";
  val hostLoc = "http://gbipapiuat.sandbox.dbt.svs.nike.com:80/api"

  val getHistory = exec(
    http("Get History")
      .get(hostLoc + "/history?page=5&pageSize=10")
      .check(status.in(200 to 210)))

  val GET_JOBS = exec(
    http("Get JOBS")
      .get(hostLoc + "/jobs")
      .check(status.in(200 to 210)))

  val GET_JOB_BY_ID = exec(
    http("Get JOB BY ID")
      .get(hostLoc + "/jobs/${jobId}")
      .check(status.in(200 to 210)))

  val GET_JOB_CLAIM_BY_IDS = exec(
    http("Get JOB CLAIM BY JOB ID AND TASK ID")
      .get(hostLoc + "/jobs/${jobId}/${taskId}/claim")
      .check(status.in(200 to 210)))

  val GET_JOB_UN_CLAIM_BY_IDS = exec(
    http("Get JOB UNCLAIM BY JOB ID AND TASK ID")
      .get(hostLoc + "/jobs/${jobId}/${taskId}/unclaim")
      .check(status.in(200 to 210)))

  val GET_JOBS_HISTORY_BY_ID = exec(
    http("Get JOBS HISTORY BY JOB ID")
      .get(hostLoc + "/jobs/${jobId}/history")
      .check(status.in(200 to 210)))

  val GET_JOB_SHIPPED = exec(
    http("Get JOB HSHIPPED")
      .get(hostLoc + "/jobs/shipped")
      .check(status.in(200 to 210)))

  val GET_PRODUCT_DETAILS = exec(
    http("Get PRODUCT DETAILS")
      .get(hostLoc + "/products/${identifier}/details")
      .check(status.in(200 to 210)))

  val GET_PRODUCT_ASSET_DETAILS = exec(
    http("Get PRODUCT ASSET DETAILS")
      .get(hostLoc + "/products/${productId}/assets/${imageName}/${version}")
      .check(status.in(200 to 210)))

  val GET_PRODUCT_ANNOTATION_DETAILS = exec(
    http("Get PRODUCT ANNOTATION DETAILS")
      .get(hostLoc + "/products/${productId}/assets/${imageName}/annotations/${version}")
      .check(status.in(200 to 210)))

  val GET_JOB_ATTACHMENTS = exec(
    http("Get JOB ATTACHEMENTS")
      .get(hostLoc + "/jobs/${jobId}/attachments")
      .check(status.in(200 to 210)))

  val GET_JOB_ATTACHMENT_FILE = exec(
    http("Get JOB ATTACHEMENT FILE")
      .get(hostLoc + "/jobs/${jobId}/attachments/${fileName}")
      .check(status.in(200 to 210)))

  val GET_SEARCH_FACETS = exec(
    http("Get SEARCH FACETS")
      .get(hostLoc + "/search/facets")
      .check(status.in(200 to 210)))

  val GET_SEARCH_JOB_FACETS = exec(
    http("Get SEARCH JOB FACETS")
      .get(hostLoc + "/search/jobs/facets")
      .check(status.in(200 to 210)))

  val GET_SEARCH_PRODUCT_FACETS = exec(
    http("Get SEARCH PRODUCT FACETS")
      .get(hostLoc + "/search/products/facets")
      .check(status.in(200 to 210)))

  val GET_SEARCH_REBUILD = exec(
    http("Get SEARCH REBUILD")
      .get(hostLoc + "/search/rebuild")
      .check(status.in(200 to 210)))

  val GET_EXPORT_CSV = exec(
    http("Get EXPORT CSV")
      .get(hostLoc + "/view/export/csv")
      .check(status.in(200 to 210)))

  val GET_SHOOT_TYPES_CSV = exec(
    http("Get SHOOT TYPES")
      .get(hostLoc + "/view/shoottypes/${productIdentifier}")
      .check(status.in(200 to 210)))

  val GET_VIEW_CODES = exec(
    http("Get VIEW CODES")
      .get(hostLoc + "/view/codes")
      .check(status.in(200 to 210)))

  val GET_ACCOUNT_GENERATE = exec(
    http("Get ACCOUNT GENERATE")
      .get(hostLoc + "/account/generate")
      .check(status.in(200 to 210)))

  val GET_ACCOUNT_INFO = exec(
    http("Get ACCOUNT INFO")
      .get(hostLoc + "/account/info")
      .check(status.in(200 to 210)))

  val GET_ACCOUNT_ROLE_USERS = exec(
    http("Get ACCOUNT ROLE USERS")
      .get(hostLoc + "/account/roles/${roleName}/users")
      .check(status.in(200 to 210)))

  val GET_CPSPUB_SYNC = exec(
    http("Get CPSPUB SYNC")
      .get(hostLoc + "/cpspub/sync")
      .check(status.in(200 to 210)))

  val GET_CPSPUB_SYNC_REPORTS = exec(
    http("Get CPSPUB SYNC REPORTS")
      .get(hostLoc + "/cpspub/sync/reports")
      .check(status.in(200 to 210)))

  val GET_CPSPUB_SYNC_REPORTS_DETAILED = exec(
    http("Get CPSPUB SYNC REPORTS DETAILED")
      .get(hostLoc + "/cpspub/sync/reports/detailed")
      .check(status.in(200 to 210)))

  val GET_CPSPUB_SYNC_REPORTS_DETAILED_ACTION = exec(
    http("Get CPSPUB SYNC REPORTS DETAILED ACTION")
      .get(hostLoc + "/cpspub/sync/reports/detailed/${action}")
      .check(status.in(200 to 210)))

  val GET_CPSPUB_SYNC_REPORTS_SUMMARY = exec(
    http("Get CPSPUB SYNC REPORTS SUMMARY")
      .get(hostLoc + "/cpspub/sync/reports/summary")
      .check(status.in(200 to 210)))

  val GET_CPSPUB_SYNC_REPORTS_ACTION = exec(
    http("Get CPSPUB SYNC REPORTS ACTION")
      .get(hostLoc + "/cpspub/sync/reports/summary/${action}")
      .check(status.in(200 to 210)))

  val GET_VERSION = exec(
    http("Get VERSION")
      .get(hostLoc + "/version")
      .check(status.in(200 to 210)))

  val GET_HISTORY = exec(
    http("Get HISTORY")
      .get(hostLoc + "/history")
      .check(status.in(200 to 210)))

  val GET_PRODUCT_HISTORY_BY_ID = exec(
    http("Get PRODUCT HISTORY BY ID")
      .get(hostLoc + "/product/${productId}/history")
      .check(status.in(200 to 210)))

  val GET_JOB_HISTORY_BY_ID = exec(
    http("Get JOB HISTORY BY ID")
      .get(hostLoc + "/product/${jobId}/history")
      .check(status.in(200 to 210)))

}