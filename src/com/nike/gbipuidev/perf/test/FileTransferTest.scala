package com.nike.gbipuidev.perf.test

import io.gatling.core.Predef._
import io.gatling.http.Predef._;
import io.gatling.http.protocol.HttpProtocolBuilder.toHttpProtocol
import org.apache.commons.net.ftp.FTPReply
import org.apache.commons.net.ftp.FTPClient
import java.io.InputStream
import java.io.FileInputStream
import java.io.File
import java.io.IOException
import java.io.FileNotFoundException
import org.apache.commons.net.ftp.FTPSClient
import org.apache.commons.net.ftp.FTP
import scala.concurrent.forkjoin.ThreadLocalRandom

class FileTransferTest extends Simulation {

	var httpTargetCluster = http.shareConnections.disableCaching
			var constantmaxDurationSeconds:Integer=120;
	var rampmaxDurationSeconds:Integer=20;

	
	val MicroserviceScenario = scenario("File Transfor FTP")
			.exec(
					addCookie(
							Cookie("NikeAuthToken", GBIPUIDevFTPServices.nikeAuthToken)
							.withDomain(GBIPUIDevFTPServices.domainName)
							)
					)
			.exec(GBIPUIDevFTPServices.getGeneratePassword)
			.pause(1)
			.exec(
					session =>  {
						var ftp:FTPSClient = new FTPSClient();
					ftp.setDataTimeout(3600000); 
					ftp.connect("10.9.204.7",2121);
					var reply:Integer = ftp.getReplyCode(); 
					if (!FTPReply.isPositiveCompletion(reply)) { 
						ftp.disconnect(); 
						throw new Exception("Exception in connecting to FTP Server"); 
					} 
					var password = session("plaintextPassword").as[String];
					var userName = "gbipsuperuser@gmail.com";
					var styleColor = ThreadLocalRandom.current().nextLong(100000L,999999L)
          var assetView = ThreadLocalRandom.current().nextLong(100L,999L)
					println("plaintextPassword ..>>>>>>>>>>>>>>>> = "+password);
					ftp.login(userName, password);
					ftp.setFileType(FTP.BINARY_FILE_TYPE); 
					ftp.enterLocalPassiveMode(); 
					var input:InputStream = new FileInputStream(new File("/users/rnandi/GBIP/nikegbip/jmeter/resources/819840-612_A.tif"));
					var remoteLocation:String = "/Alfresco/Nike/Assets/Incoming/"; 
					var assetName:String = styleColor + "_" +assetView + ".tif"; 
					//vars.put("assetName", assetName); 
					ftp.storeFile(remoteLocation + assetName, input); 
					input.close();
					//println("FILE "+assetName+" PUSHED TO SERVER SUCCESSFULLY....")
					if(ftp.isConnected()) { 
						try { 
							ftp.logout(); 
							ftp.disconnect(); 
						} catch {
  						case ex: FileNotFoundException =>{
  							println("Missing file exception")
  						} 
						} 
					}
					println("FILE "+assetName+" PUSHED TO SERVER SUCCESSFULLY....")
					session
					}
					)

			before {
		println("Starting File Transfor FTP Load injector test")
	}

	setUp(
			MicroserviceScenario
			.inject(rampUsersPerSec(1) to 2 during rampmaxDurationSeconds,
					constantUsersPerSec(2) during (constantmaxDurationSeconds seconds)) 
			.protocols(httpTargetCluster)
			)

	after {
		println("Completed File Transfor FTP Request Load injector test")
	}

}