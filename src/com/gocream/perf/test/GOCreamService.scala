package com.gocream.perf.test;

import io.gatling.core.Predef._;
import io.gatling.http.Predef._;
import io.gatling.http.HeaderNames

object GOCreamService {

  val getScountPlayerList = exec(
    http("Get SCOUT Player List")
      .get("http://test-web.nike.com/k11/scout/player/list")
      .check(status.in(200 to 210)))
      
  val getScountPlayerCount = exec(
    http("Get SCOUT Player Count")
      .get("http://test-web.nike.com/k11/scout/player/count")
      .check(status.in(200 to 210)))

  var postScountPlayerStatusUpdatePayLoad = "{\"id\":\"123\",\"status\":\"status\"}";
  val postScountPlayerStatusUpdate = exec(
    http("Post SCOUNT Player Status Update")
      .post("http://test-web.nike.com/k11/scout/player/status/update")
      .body(StringBody(postScountPlayerStatusUpdatePayLoad)).asJSON
      .check(status.in(200 to 210)))  
      
   var postScountPlayerMovementUpdatePayLoad = "{\"id\":\"123\",\"status\":\"status\"}";
   val postScountPlayerMovementUpdate = exec(
    http("Post SCOUNT Player Movement Update")
      .post("http://test-web.nike.com/k11/scout/player/playermoments/update")
      .body(StringBody(postScountPlayerMovementUpdatePayLoad)).asJSON
      .check(status.in(200 to 210))) 

  val getScountPlayerGamesById = exec(
    http("Get SCOUT Player Games By Id")
      .get("http://test-web.nike.com/k11/scout/player/${gameId}/games")
      .check(status.in(200 to 210)))


}