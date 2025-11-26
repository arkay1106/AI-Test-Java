package nvr.sat.stepDefinitions;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.TimeoutException;

import nvr.sat.objectMethods.NavigationMethods;
import nvr.sat.objectMethods.WebDriverMethods;
import nvr.sat.testutils.FileReader;
import nvr.sat.testutils.GlobalVars;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.core.gherkin.Feature;
import io.cucumber.java.AfterStep;
//import io.cucumber.java.After;
//import io.cucumber.java.Before;
//import io.cucumber.java.Scenario;
import io.restassured.RestAssured;
import io.restassured.response.Response;


public class GlobalSteps {
	/**
	 * This class contains all the step definitions related to common steps used across the project
	 * 
	 */
	public static Response response;
	public static FileReader objTestDataFile;
	static Logger logger = LogManager.getLogger(GlobalSteps.class);
	
	/**
	 * This hook runs before every scenario.
	 * 
	 */
	@Before
	public void beforeScenario(Scenario scenario) {
		try {
			logger.debug("-------------------------------------------------------------------------------------------");
			//Get the current feature name during run time. This name used to verify the testcase/feature name in testConfig file and also used to build the testdata file path
			logger.debug("GlobalSteps.beforeScenario -> Start...");
	        logger.debug("getid " + scenario.getId());
	        logger.debug(scenario.getId().trim());
	        int intEnd=(scenario.getId().lastIndexOf(":")) - 8;
	        //int intStart=(scenario.getId().indexOf("/features/")) + 10;
	        int intStart=(scenario.getId().lastIndexOf("/")) + 1;
	        GlobalVars.glStrFeatureFileName = scenario.getId().trim().substring(intStart,intEnd);
	        //GlobalVars.glStrFeatureFileName = "VerifyGetListUsers";// "VerifyCreateUser";
	        GlobalVars.glStrScenarioName = scenario.getName();
	        logger.debug("Feature Name ="+GlobalVars.glStrFeatureFileName);
	        logger.debug("===========================================================================================");
	        logger.debug("Scenario Name ="+GlobalVars.glStrScenarioName);
	        logger.debug("===========================================================================================");
	        logger.debug("GlobalSteps.beforeScenario -> End...");
		}catch(Exception e) {
			logger.debug("GlobalSteps.beforeScenario Catch block -> " + e.getMessage());
			logger.debug(ExceptionUtils.getStackTrace(e));
			
		}
	}
	
	/**
	 * This step loads the configuration for the feature from testConfig.xlsx file.
	 * 
	 */
	@Given("I load the test configuration for \"(.*)\"$")
	public void loadFeatureConfiguration(String testConfigName) {
	    // Write code here that turns the phrase above into concrete actions
		try {
			//Load the configuration if it is not already loaded for the same feature file
			if(!GlobalVars.glStrFeatureFileName.equals(GlobalVars.glStrPrevFeatureFileName)) {
				logger.debug("-------------------------------------------------------------------------------------------");
			    logger.debug("GlobalSteps.loadConfiguration -> Start...");
			    //Load test configuration for current feature file
			    nvr.sat.testutils.Configuration.loadTestConfiguration(GlobalVars.glStrFeatureFileName, testConfigName);
			    logger.debug("GlobalSteps.loadConfiguration -> End...");
			}
		}catch(Exception e) {
			logger.debug("GlobalSteps.loadConfiguration Catch block -> Loading configuration failed.");
			logger.debug(e.getMessage().toString());
			logger.debug(ExceptionUtils.getStackTrace(e));
			Assert.assertTrue(false);
		}
	}
	
	/**
	 * This step will set a Component for a feature file. 
	 * 
	 */
	@Given("The Test Component is set to \"(.*)\"$")
	public void setTestComponent(String testComponent) {
	    // Write code here that turns the phrase above into concrete actions
		try {
			//Load the configuration if it is not already loaded for the same feature file
			if(!GlobalVars.glStrFeatureFileName.equals(GlobalVars.glStrPrevFeatureFileName)) {
				logger.debug("-------------------------------------------------------------------------------------------");
			    logger.debug("GlobalSteps.setTestComponent -> Start...");
			    //Load test configuration for current feature file
			    GlobalVars.glStrComponent = testComponent;
			    logger.debug("GlobalSteps.setTestComponent -> End...");
			}
		}catch(Exception e) {
			logger.debug("GlobalSteps.setTestComponent Catch block -> setting Test Component failed.");
			logger.debug(e.getMessage().toString());
			logger.debug(ExceptionUtils.getStackTrace(e));
			Assert.assertTrue(false);
		}
	}
	
	/**
	 * This step loads the given test data sheet.
	 * 
	 */
	@Given("I load the testdata for request body from sheet \"([^\"]*)\"$" )
	@Then("I load the testdata for expected response from sheet \"([^\"]*)\"$")
	public void loadTestDataFile(String strSheetName) {
	    // Write code here that turns the phrase above into concrete actions
		try {
			logger.debug("-------------------------------------------------------------------------------------------");
		    logger.debug("GlobalSteps.loadTestDataFile -> Start...");
		    //Load test data file for current feature file
		    objTestDataFile = new FileReader(GlobalVars.glStrTestDatFileWithPath, strSheetName);
		    //Load test data column headers
		    nvr.sat.testutils.Configuration.readColumnHeaders(GlobalVars.glStrTestDatFileWithPath, strSheetName);
		    logger.debug("GlobalSteps.loadTestDataFile -> End...");
		}catch(Exception e) {
			logger.debug("GlobalSteps.loadTestDataFile Catch block -> Loading configuration failed.");
			logger.debug(e.getMessage().toString());
			logger.debug(ExceptionUtils.getStackTrace(e));
			Assert.assertTrue(false);
		}
	}
	
	

	/**
	 * This step sets the base environment URL.
	 * 
	 */
	@Given("I set the environment base URL from configuration file")
	public void setBaseURL() {
	    // Write code here that turns the phrase above into concrete actions
		try {
			logger.debug("-------------------------------------------------------------------------------------------");
			logger.debug("GlobalSteps.setURL -> Start...");
			logger.debug("BaseURL: " + GlobalVars.glStrBaseURL);
			RestAssured.baseURI = GlobalVars.glStrBaseURL;
			logger.debug("GlobalSteps.setURL -> End...");
		}catch(Exception e) {
			logger.debug("GlobalSteps.setURL Catch block -> Setting URL failed.");
			logger.debug(e.getMessage().toString());
			logger.debug(ExceptionUtils.getStackTrace(e));
			Assert.assertTrue(false);
		}    
	}
	
	
	/**
	 * This step sets the base environment URL to 8081.
	 * 
	 */
	@Given("I set the environment base URL to 8081")
	public void setBaseURL8081() {
	    // Write code here that turns the phrase above into concrete actions
		try {
			logger.debug("-------------------------------------------------------------------------------------------");
			logger.debug("GlobalSteps.setURL -> Start...");
			logger.debug("http://localhost:8081");
			RestAssured.baseURI = "http://localhost:8081";
			logger.debug("GlobalSteps.setURL -> End...");
		}catch(Exception e) {
			logger.debug("GlobalSteps.setURL Catch block -> Setting URL failed.");
			logger.debug(e.getMessage().toString());
			logger.debug(ExceptionUtils.getStackTrace(e));
			Assert.assertTrue(false);
		}    
	}
	
	/**
	 * This step verifies the response status code with the given expected status code
	 * 
	 */
	@Then("I verify the response status code as \"([^\"]*)\"$" )
	//@Then("I verify the response status code as {int}")
	public void verifyStatusCode(String strExpStatusCode) {
	    // Write code here that turns the phrase above into concrete actions
		try {
			logger.debug("-------------------------------------------------------------------------------------------");
			logger.debug("GlobalSteps.verifyStatusCode -> Start...");
			Assert.assertEquals(Integer.parseInt(strExpStatusCode), response.getStatusCode());
			logger.debug("GlobalSteps.verifyStatusCode -> End...");
		}catch(Exception e) {
			logger.debug("GlobalSteps.verifyStatusCode Catch block -> Verify the status code.");
			logger.debug(e.getMessage().toString());
			logger.debug(ExceptionUtils.getStackTrace(e));
			Assert.assertTrue(false);
		} 
			
	}
	@And("I wait for \"(.*)\" milliseconds$")
	public void waitFor(String strWaitTimeinMills) {
		try {
			Thread.sleep(Integer.parseInt(strWaitTimeinMills));
		}catch(Exception e) {
			logger.debug("GlobalSteps.waitFor Catch block -> Wait foe gven time.");
			logger.debug(e.getMessage().toString());
			logger.debug(ExceptionUtils.getStackTrace(e));
		}
		
		
	}
	
	/**
	 * This step verifies that there isn't a response.  Can be used if a response is not expected. 
	 * 
	 */
	@Then("there should not be a response")
	public void noConnection() {
			Assert.assertTrue((GlobalSteps.response == null));
	}
	
	
	/**
	 * This hook runs after every scenario.
	 * 
	 */
	@After
	public void afterScenario(Scenario scenario) {
		try {
			logger.debug("-------------------------------------------------------------------------------------------");
			logger.debug("GlobalSteps.afterScenario -> Start...");
			//TO DO
			//Write code to log the result and response in TestRail
			logger.debug("Scenario status: " + scenario.getStatus());
			if(response != null) {
			logger.debug("Response: " + response.asString());
			}
			GlobalVars.glStrPrevFeatureFileName = GlobalVars.glStrFeatureFileName;
			objTestDataFile = null;
			response = null;
			logger.debug("GlobalSteps.afterScenario -> End...");
		}catch(Exception e) {
			logger.debug(e.getMessage().toString());
			logger.debug(ExceptionUtils.getStackTrace(e));
		}
	}
}
