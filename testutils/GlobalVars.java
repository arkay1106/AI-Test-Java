package nvr.sat.testutils;

import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * This class contains all the global variables used in the framework
 *
 */
public class GlobalVars {
	//Below global variables used for TestCase/Feature Configuration
	public static final String glStrTestConfigFolder = "//src//test//resources//testconfig//";
	public static final String glStrTestConfigFile = "testConfig.xlsx";
	public static final String glStrConfigSheetName = "Sheet1";
	public static HashMap<Object, Object> glMapTestConfig = new HashMap<Object, Object> ();
	public static String glStrComponent;
	public static String glStrBaseURL;
	public static String glStrToken;
	public static String glStrAPIEndPoint;
	public static String glStrAPISwaggerEndPoint;
	public static String glStrJWTToken;
	public static String glStrJWTExpiredToken;
	public static String glStrJWTInvalidToken;
	public static String glStrJWTExpiringToken;
	public static String glStrJWTNoContractsToken;
	public static String glStrJWTSingleContractsToken;
	//Below global variables used for testdata 
	public static final String glStrTestDataFolder = "//src//test//resources//testdata//";
	public static String glStrTestDatFileWithPath;
	public static String glStrTestDatFileName;
	public static HashMap<Object, Object> glMapTestDataColumnHeaders = new HashMap<Object, Object> ();
	//Below global variables used for feature file
	public static String glStrFeatureFileName;
	public static String glStrPrevFeatureFileName;
	public static String glStrScenarioName;
	//Below global variables used for TestRail properties
	public static final String glStrTestRailConfigRootFolder = "//src//test//resources//testRailConfig//";
	public static String glStrTestRailConfigFolder;
	public static String glStrAPIHealthEndPoint;
	public static HashMap<String, String> glMultipleURL = new HashMap<String, String> ();
	public static final String glStrTestRailPropertyFile = "testRail.properties";
	
	
	//UI Test Automation
	public static String webClaimsAuthorUsername;
	public static String webAdminUsername;
	public static String webClaimsRulesUsername;
	public static String webClaimsAdminUsername;
	public static String webURL;
	
	public static String webUsername;
	public static String webPassword;
	//public static String webComponent;
}
