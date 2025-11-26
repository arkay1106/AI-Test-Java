package nvr.sat.testutils;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
/**
 * This class contains all the methods to retrieve the TestRail configuration properties from testrail.proerties
 * 
 * 
 */
public class TestRailConfigReader {
	private Properties propTestRailProperties;
	private static TestRailConfigReader testRailConfigReader;
 
	private TestRailConfigReader() {
		BufferedReader reader;    	
		String strPropertyFilePath = GlobalVars.glStrTestRailConfigFolder + "//"  + GlobalVars.glStrTestRailPropertyFile;
     	System.out.println("TestRail Config file: " + strPropertyFilePath);
        try {
             reader = new BufferedReader(new FileReader(strPropertyFilePath));
             propTestRailProperties = new Properties();
             try {
            	 propTestRailProperties.load(reader);
                 reader.close();
             } catch (IOException e) {
            	 e.getMessage();
            	 e.getCause();
                 e.printStackTrace();
             }
         }catch (FileNotFoundException e) {
        	 e.getMessage();
             e.printStackTrace();
             throw new RuntimeException("TestRailConfigReader.TestRailConfigReader: testRail.properties file not found at " + strPropertyFilePath);
         } 
	}
    public static TestRailConfigReader getInstance() {
    	if(testRailConfigReader == null) {
    		testRailConfigReader = new TestRailConfigReader();
    	}
        return testRailConfigReader;
    }
    public String getTestRailBaseUrl() {
        String strBaseUrl = propTestRailProperties.getProperty("testRailURL");
        if(strBaseUrl != null) {
        	System.out.println("TestRailConfigReader.getTestRailBaseUrl: TestRail base URL : " + strBaseUrl);
        	return strBaseUrl;
        }else {
        	throw new RuntimeException("TestRailConfigReader.getTestRailBaseUrl : Property 'testRailURL' not specified in the testRail.properties file.");
        }
        
    }
    
    public String getTestRailEndPoint() {
        String strEndPoint = propTestRailProperties.getProperty("testRailEndPoint");
        if(strEndPoint != null) {
        	System.out.println("TestRailConfigReader.getTestRailEndPoint: TestRail endpoint : " + strEndPoint);
        	return strEndPoint;
        }else {
        	throw new RuntimeException("TestRailConfigReader.getTestRailBaseUrl : Property 'testRailEndPoint' not specified in the testRail.properties file.");
        }
    }
    
    public String getTestRailProjectId() {
        String strProjectId = propTestRailProperties.getProperty("testRailProjectId");
        if(strProjectId != null) {
        	System.out.println("TestRailConfigReader.getTestRailProjectId: TestRail project id : " + strProjectId);
        	return strProjectId;
        }else {
        	throw new RuntimeException("TestRailConfigReader.getTestRailProjectId : Property 'testRailProjectId' not specified in the testRail.properties file.");
        }
    }

    public String getTestRailSuiteId() {
        String strProjectId = propTestRailProperties.getProperty("testRailSuiteId");
        if(strProjectId != null) {
        	System.out.println("TestRailConfigReader.getTestRailProjectId: TestRail Suite id : " + strProjectId);
        	return strProjectId;
        }else {
        	throw new RuntimeException("TestRailConfigReader.getTestRailSuiteId : Property 'testRailSuiteId' not specified in the testRail.properties file.");
        }
    }

    public String getTestRailUser() {
        String strUser = propTestRailProperties.getProperty("testRailUser");
        if(strUser != null) {
        	System.out.println("TestRailConfigReader.getTestRailUser: TestRail User Id : " + strUser);
        	return strUser;
        }else {
        	throw new RuntimeException("TestRailConfigReader.getTestRailBaseUrl : Property 'testRailUser' not specified in the testRail.properties file.");
        }
    }
    
    public String getTestRailAPIKey() {
        String strAPIKey = propTestRailProperties.getProperty("testRailAPIKey");
        if(strAPIKey != null) {
        	System.out.println("TestRailConfigReader.getTestRailUser: TestRail API Key : " + strAPIKey);
        	return strAPIKey;
        }else {
        	throw new RuntimeException("TestRailConfigReader.getTestRailBaseUrl : Property 'testRailAPIKey' not specified in the testRail.properties file.");
        }
    }
    
    public boolean getTestRailSwitch() {
        boolean strTestRailOnOff = Boolean.parseBoolean(propTestRailProperties.getProperty("testRailOnOff"));
        if(strTestRailOnOff == true || strTestRailOnOff == false) {
        	System.out.println("TestRailConfigReader.getTestRailSwitch: TestRail OnOff Switch : " + strTestRailOnOff);
        	return strTestRailOnOff;
        }else {
        	throw new RuntimeException("TestRailConfigReader.getTestRailBaseUrl : Property 'testRailOnOff' not specified in the testRail.properties file.");
        }
    }
}