  
package nvr.sat.testutils;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONObject;
import org.junit.Assert;

import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.TestRunStarted;
import io.cucumber.plugin.event.TestRunFinished;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.Result;
import io.cucumber.plugin.event.Status;
import io.cucumber.plugin.event.TestCase;
import io.cucumber.plugin.event.TestCaseFinished;

/**
 * The Test Rail Integration.  Listens for specific events and performs the Test Rail API calls 
 * To send the results of the Test Cases to Test Rail.
 * 
 *
 */
public class TestRailListener implements ConcurrentEventListener {
	
	private APIClient client;
	private JSONObject clientJson;
	private boolean testRailStarted;
	private List<Map> testCases = new ArrayList<>();
	private Set<String> testCaseSelection = new HashSet<String>();
	private List<String> testRailErrors = new ArrayList<>();
	
	private TestRailConfigReader objTRConfigReader;

	private String testRailUrl;
	private String testRailProjectId;
	private String testRailSuiteId;
	private String testRailUser;
	private String testRailKey;
	
	//Below boolean switch variable will be used to control the test results logging into TestRail. Not used right now.
	private boolean testRailOnOffSwitch;

	public void setEventPublisher(EventPublisher publisher) {
		publisher.registerHandlerFor(TestRunStarted.class, event -> {
			handleTestRunStarted(event);

		});
		publisher.registerHandlerFor(TestCaseFinished.class, event -> {
			try {
				handleTestCaseFinished(event);


			} catch (IOException | APIException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}); 
		publisher.registerHandlerFor(TestRunFinished.class, event -> {
			try {
				handleTestRunFinished(event);
			} catch (IOException | APIException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		});
	}

	/***
	 * Event handle for when the Test RunStarts.  Starts the Test Rail connection
	 * 
	 *  @param TestRunStarted
	 */
	private void handleTestRunStarted(TestRunStarted event) {
		testRailStarted = false;
	}

	/***
	 * Event handle for when the Test Run Finishes.  Takes the Map of the Test Cases and submit the post request to Test Rail.
	 * 
	 *  @param TestRunFinished
	 */
	private void handleTestRunFinished(TestRunFinished event) throws MalformedURLException, IOException, APIException {
		if(!testCases.isEmpty()) {
			TestCase tcName = (TestCase)testCases.get(0).get(2);
			String scenarioName = tcName.getName();
			String suiteId = testRailSuiteId;
			
			String getSuiteName = "get_suite/" + suiteId;
			JSONObject testSuitejsonResponse = (JSONObject) client.sendGet(getSuiteName);
			String suiteName = (String) testSuitejsonResponse.get("name");
			String testRunName = "Automated Test Run for Test Suite Id : " + suiteName + ", ran @ " + java.time.LocalDate.now();
			String projectId = testRailProjectId;
			
     		if(testCases.size() == testCaseSelection.size()) {
     			createTestRun(suiteId, testRunName, projectId, testCaseSelection);
     			
				TestCase testcase;
				Result result;
				for(Map<?, ?> var : testCases) {
					result = (Result) var.get(1);
					testcase = (TestCase) var.get(2);
					testRailPostRequest(testcase, result);
					} 			
				} else {
					testRailErrors.add("Test Case Selection was not the same size as Test Cases.  This may be due to a duplicate Test Case. Test Run was not created.");
					for(String str : testRailErrors) 
						System.err.println(str);
				}
     		}
		}

	/***
	 * Event handle for when the Test Case Finishes.  Takes the TestCase and Results and stores them in a Map to be used later.
	 * 
	 *  @parms TestCaseFinish
	 */
	private void handleTestCaseFinished(TestCaseFinished event) throws MalformedURLException, IOException, APIException  {
		if(testRailStarted == false ) {
			//Initialize the Test Rail configuration
			objTRConfigReader = TestRailConfigReader.getInstance();	
			testRailUrl = objTRConfigReader.getTestRailBaseUrl();
			testRailProjectId = objTRConfigReader.getTestRailProjectId();
			testRailSuiteId = objTRConfigReader.getTestRailSuiteId();
			testRailUser = objTRConfigReader.getTestRailUser();
			testRailKey = objTRConfigReader.getTestRailAPIKey();
			testRailOnOffSwitch = objTRConfigReader.getTestRailSwitch();
			testRailStartUp();
		}
		TestCase testCase = event.getTestCase();
		Result result = event.getResult();
		Map<Integer, Object> testData = new HashMap<Integer, Object>();
		testData.put(1, result);
		testData.put(2, testCase);
		testCases.add(testData);
		String testCaseId = getTestId(testCase.getName());
		
		boolean testCaseAdded = false;
		
		//Add a method to verify the Test Case ID exists within Test Rail. If it exists, add the testCase.
		try {
			String getTestCase = "get_case/" + testCaseId;
			client.sendGet(getTestCase);
			testCaseAdded = testCaseSelection.add(testCaseId);
		} catch (APIException e) {
			testRailErrors.add("Was not able to get " + testCaseId + ". " + testCase.getName() + " has an invalid Test Case ID.");
		}
		
		
		if(!testCaseAdded) {
			testRailErrors.add(testCase.getName() + " was not added. Duplicate Test Case ID.");
		}
	}
	
	/***
	 * Sends a Post Request to add results for a Test Rail Test Case.
	 * 
	 *  @parms Test Case, Results
	 */
	private void testRailPostRequest(TestCase testCase, Result result) throws MalformedURLException, IOException, APIException {
		String ScenarioName = testCase.getName();
		Status status = result.getStatus();
		Throwable error = result.getError();
		int hypenStart = ScenarioName.indexOf(' ');
		String TestCaseId = ScenarioName.substring(4,hypenStart);

		Map<String, Comparable> data = new HashMap<String, Comparable>();
		//Sets the results passed on the Status. 
		if(status.equals(Status.PASSED)) {
			data.put("status_id", 1);
			data.put("comment", ScenarioName + " passed.");
		}
		else if(status.equals(Status.FAILED)) {
			data.put("status_id", 5);
			data.put("comment", ScenarioName + " failed, with error:  " + error);
		}
		else if(status.equals(Status.SKIPPED)) {
			data.put("status_id", 3);
			data.put("comment", ScenarioName + " was skipped");
		}   



		String testRun = clientJson.get("id").toString();
		String postTestCase = "add_result_for_case/" + testRun + "/" + TestCaseId;

		
		if(!isNullorEmpty(TestCaseId)) {
			try {
				client.sendPost(postTestCase, data);
			} catch (APIException e) {
				System.out.println("Was not able to upload for " + TestCaseId);
				testCaseSelection.remove(TestCaseId);
				
			}
		}
	}

	/***
	 * Connects to the Test Run client using a URL, User, and API Key. 
	 * 
	 */
	private void testRailStartUp() {
		client = new APIClient(testRailUrl);
		client.setUser(testRailUser);
		client.setPassword(testRailKey);
		testRailStarted = true;
	}
	
	/***
	 * Returns the TestId from a Scenario Name.  
	 * 
	 * @param String
	 * @returns String 
	 */
	private String getTestId(String scenarioName) {
		int hypenStart = scenarioName.indexOf(' ');
		return scenarioName.substring(4,hypenStart);
	}
	

	
	/***
	 * Creates the TestRail Test Run.  
	 * 
	 * @param String, String, String, and List<String> 
	 */

	private void createTestRun(String suiteID, String testRunName, String projectId, Set<String> testCaseSelection) {

		Map<String, Object> data = new HashMap<String, Object>();

		data.put("suite_id", suiteID);
		data.put("name", testRunName);
		data.put("include_all", false);
		data.put("case_ids", testCaseSelection);

		try {
			clientJson = (JSONObject) client.sendPost("add_run/" + projectId, data);
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (APIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	/***
	 * Determines if a String is Null
	 * 
	 * @param String 
	 * @return boolean
	 */

	private boolean isNullorEmpty(String str) {
		if(str != null && !str.trim().isEmpty()) {
			return false;
		}
		return true;
	}

}