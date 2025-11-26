package nvr.sat.testutils;
import java.io.*;
import java.util.Objects;
import java.util.Properties;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * This class contains the framework supporting methods related to loading configuration file, reading configuration file etc
 * @since :
 */
public class Configuration {
	/**
	 * This method reads the given configuration file and checks for the test/feature configuration in the file. Loads the test configuration into a map if found.
	 * @author :
	 * @since : 
	 */
	
	static Logger logger = LogManager.getLogger(Configuration.class);
	
	private static Properties getPropertyFile(String configFilePath, String FeatureName) {
		String PropertiesFile = FeatureName + ".properties";
		Properties prop = null;
		try {
			GlobalVars.glMapTestConfig.clear();
			prop = readPropertyFile(PropertiesFile, configFilePath);
			
	      } catch(FileNotFoundException fnfe) {
		         fnfe.printStackTrace();
		      } catch(IOException ioe) {
		         ioe.printStackTrace();
		      } finally {
		        
		      }

		return prop;
		
	}
	
	private static Properties readPropertyFile(String fileName, String configFilePath) throws IOException {
		Properties prop = null;
		FileInputStream fis = null;
		
		try {
			fis = new FileInputStream(configFilePath + "/" + fileName);
	        prop = new Properties();
	        prop.load(fis);
	      } catch(FileNotFoundException fnfe) {
	         fnfe.printStackTrace();
	      } catch(IOException ioe) {
	         ioe.printStackTrace();
	      } finally {
	        fis.close();
	      }

		return prop;
		
	}
	
	private static boolean getTestConfiguration(String strConfigFilePath,String strSheetName, String strFeatureName) {
		boolean boolResult = false;
		try {
			Object strCellValue,strColumnHeaderValue;
			int intMatchingRowNum = 0;
			GlobalVars.glMapTestConfig.clear();
			FileReader objExcel = new FileReader(strConfigFilePath, strSheetName);
			if(objExcel.getRowCount() > 0) {
				//Get the matching row number from test config file
				for(int intRow=0;intRow<objExcel.getRowCount();intRow++){
					strCellValue = objExcel.getCellValue(intRow, 1);
					logger.debug("strCellValue = " + strCellValue + "");
					if(strCellValue.toString().trim().equalsIgnoreCase(strFeatureName.toString().trim())){
						intMatchingRowNum = intRow;
						boolResult = true;
						break;
					}
				}
				//Check if the matching row found
				if(intMatchingRowNum > 0){
					boolResult = true;
					logger.debug("Configuration.getTestConfiguration keyword: Testcase name '" + strFeatureName  + "' found in Test Configuration file '" + strConfigFilePath  + ".");
					//Get the test case configuration information for the matched row
					for(int intCol = 0; intCol < objExcel.getColumnCount(); intCol++){
						strColumnHeaderValue = objExcel.getCellValue(0, intCol);
						strCellValue = objExcel.getCellValue(intMatchingRowNum, intCol);
						logger.debug("strColumnHeaderValue = " + strColumnHeaderValue);
						logger.debug("strCellValue = " + strCellValue);
						GlobalVars.glMapTestConfig.put(strColumnHeaderValue,strCellValue);
						logger.debug("Configuration.getTestConfiguration keyword: Key = " + strColumnHeaderValue + " ; Value = " + strCellValue);
					}
				}else {
					boolResult = true;
					logger.debug("Configuration.getTestConfiguration keyword: Testcase name '" + strFeatureName  + "' not found in Test Configuration file '" + strConfigFilePath  + ".");
				}
			}else{
				logger.debug("Configuration.getTestConfiguration keyword: Test Coniguration file '" + strConfigFilePath  + "' doesn't have any configurations.");
				return false;
			}
			objExcel = null;
		}catch(Exception e){
			logger.debug(ExceptionUtils.getStackTrace(e));
		}
		return boolResult;
	}
	/**
	 * This method loads the given configuration file
	 * @author :
	 * @since :
	 */
	public static void loadTestConfiguration(String strFeatureName, String testConfigfile){
		//String strConfigFilePath = "C://MCAM//Cucumber-RestAssured-POC//APITestAutomation//config//testConfig.xls";
		String strProjDir;
		strProjDir = System.getProperty("user.dir");
		String strConfigFilePath = strProjDir + "//" + GlobalVars.glStrTestConfigFolder + "//" + GlobalVars.glStrTestConfigFile;
		String strTestConfigFilePath = strProjDir + "//" + GlobalVars.glStrTestConfigFolder + "//";
		String strSheetName = GlobalVars.glStrConfigSheetName;
		try{
			logger.debug("Configuration.loadTestConfiguration -> Start...");
			System.out.println("strTestConfigFilePath: " + strTestConfigFilePath);
			System.out.println("testConfigfile: " + testConfigfile);
			Properties prop = getPropertyFile(strTestConfigFilePath, testConfigfile);
			//GetTestConfiguration for the current testcase/feature
			if(prop == null){
				logger.debug("Configuration.loadTestConfiguration -> : Issue in retrieving the Test Configuration for testcase/feature name : " + strFeatureName);
			}else{
					//Load all the test configuration parameters for the test case/feature into global variables
					//Set the enviroment Variable
			    	String env = prop.getProperty("Env");
			    	GlobalVars.glStrComponent = prop.getProperty("Component");
					// Check environment variable for TC_BASEURL value, if empty use the test configuration file
					if (Objects.isNull(System.getenv("TC_BASEURL"))){
						GlobalVars.glStrBaseURL = prop.getProperty(env + "BaseURL");
					} else {
						GlobalVars.glStrBaseURL = System.getenv("TC_BASEURL");
					}
					
					String MultipleURLS = prop.getProperty("APIURLS");
					if(MultipleURLS != null) {
						String[] SplitURLS = MultipleURLS.split(",");
						
						for(String urls:SplitURLS) {
							
							String[] urlKeyValue = urls.split(":");
							String urlKey = urlKeyValue[0].trim().toLowerCase();
							String urlValue = urlKeyValue[1].trim();
							logger.debug(urls +  " Key is:" + urlKey + " Value is:" + urlValue);
							GlobalVars.glMultipleURL.put(urlKey, urlValue);
							
						}
					}
					
					GlobalVars.glStrAPIEndPoint = prop.getProperty("APIEndPoint");
					GlobalVars.glStrAPIHealthEndPoint = prop.getProperty("HealthCheckEndPoint");
					GlobalVars.glStrAPISwaggerEndPoint = prop.getProperty("APISwaggerEndPoint");
					GlobalVars.glStrJWTToken = prop.getProperty(env + "Token");
					GlobalVars.glStrJWTInvalidToken = prop.getProperty(env + "TokenInvalid");
					GlobalVars.glStrJWTExpiredToken = prop.getProperty(env + "TokenExpired");
					GlobalVars.glStrJWTExpiringToken =  prop.getProperty(env + "TokenExpiring");
					GlobalVars.glStrJWTSingleContractsToken = prop.getProperty(env + "TokenSingleContract");
					GlobalVars.glStrJWTNoContractsToken = prop.getProperty(env + "TokenNoContract");
					logger.debug("Base URL: " + GlobalVars.glStrBaseURL);
					logger.debug("API End Point: " + GlobalVars.glStrAPIEndPoint);
					GlobalVars.glStrTestDatFileName = strFeatureName.toString().trim() + ".xlsx";
					GlobalVars.glStrTestDatFileWithPath = strProjDir + GlobalVars.glStrTestDataFolder + GlobalVars.glStrComponent.trim() + "//" + GlobalVars.glStrTestDatFileName;
					GlobalVars.glStrTestRailConfigFolder = strProjDir + GlobalVars.glStrTestRailConfigRootFolder + GlobalVars.glStrComponent.trim();
					logger.debug("TestData file:" + GlobalVars.glStrTestDatFileWithPath);
					logger.debug("glStrTestRailConfigFolder : " + GlobalVars.glStrTestRailConfigFolder);
					
					//UI Test Automation
					GlobalVars.webClaimsAuthorUsername = prop.getProperty("webClaimsAuthorUsername");
					GlobalVars.webAdminUsername = prop.getProperty("webAdminUsername");
					GlobalVars.webClaimsRulesUsername = prop.getProperty("webClaimsRulesUsername");
					GlobalVars.webClaimsAdminUsername = prop.getProperty("webClaimsAdminUsername");
					GlobalVars.webURL = prop.getProperty("webURL");
					GlobalVars.webUsername = prop.getProperty("webUsername");
					GlobalVars.webPassword = prop.getProperty("webPassword");
					logger.debug("webUsername : " + GlobalVars.webUsername);
					logger.debug("webPassword : " + GlobalVars.webPassword);
					
			}
			logger.debug("Configuration.loadTestConfiguration -> Done...");
		}catch(Exception e){
			logger.debug("Configuration.loadTestConfiguration Catch block -> .......Exception occurred. PrintStackTrace : " + e.getMessage());
			logger.debug(e.getCause().toString());
		}
	}
	/**
	 * This method reads the column headers from the given test data excel file
	 * @author :
	 * @since : 
	 */
	public static void readColumnHeaders(String strFileName, String strSheetName) {
		try {
			FileReader objExcel = new FileReader(strFileName, strSheetName);
			GlobalVars.glMapTestDataColumnHeaders.clear(); 
	        for(int i = 0; i < objExcel.getColumnCount(); i++)
	        {
	        	//Read each column header and add to a map Key = ColumnHeaderName and Value=cellindex 
	        	GlobalVars.glMapTestDataColumnHeaders.put(objExcel.getCellValue(0, i),i);
	        }
	        objExcel = null;
	        logger.debug(GlobalVars.glMapTestDataColumnHeaders.keySet().toString());
	        logger.debug(GlobalVars.glMapTestDataColumnHeaders.values().toString());
		}catch(Exception e) {
			logger.debug(ExceptionUtils.getStackTrace(e));
		}
	}
	/**
	 * This method returns the value from a specific cell in excel file
	 * @author :
	 * @since :
	 */
	public static String getTestDataValue(String strFileName, String strSheetName,String strTDColumnHeaderName, int intRowNum) {
		String strCellValue = "";
		int intColumnNumber = -1;
		try {
			FileReader objExcel = new FileReader(strFileName, strSheetName);
			//Get column number by column name
			if(null != GlobalVars.glMapTestDataColumnHeaders.get(strTDColumnHeaderName)) {
				intColumnNumber = Integer.parseInt(GlobalVars.glMapTestDataColumnHeaders.get(strTDColumnHeaderName).toString()); //Cell starts from 0
			}else {
				logger.debug("Configuration.getTestDataValue -> Coulumn header " + strTDColumnHeaderName  + " not found in the testdata file.");
			}
			//Get the cell date based on column and row position
			strCellValue = objExcel.getCellValue(intRowNum, intColumnNumber).toString();	
			objExcel = null;
		}catch (Exception e) {
			logger.debug(ExceptionUtils.getStackTrace(e));
		}
		return strCellValue;
	}
	/**
	 * This method returns the value from a specific cell in excel file
	 * @author : 
	 * @since : 
	 */
	public static String getTestDataValue(FileReader objExcel,String strTDColumnHeaderName, int intRowNum) {
		String strCellValue = "";
		int intColumnNumber = -1;
		try {
			//Get column number by column name
			if(null != GlobalVars.glMapTestDataColumnHeaders.get(strTDColumnHeaderName)) {
				intColumnNumber = Integer.parseInt(GlobalVars.glMapTestDataColumnHeaders.get(strTDColumnHeaderName).toString()); //Cell starts from 0
			}else {
				logger.debug("Configuration.getTestDataValue -> Coulumn header " + strTDColumnHeaderName  + " not found in the testdata file.");
			}
			//Get the cell data based on column and row position
			strCellValue = objExcel.getCellValue(intRowNum, intColumnNumber).toString();	
		}catch (Exception e) {
			logger.debug(ExceptionUtils.getStackTrace(e));
		}
		return strCellValue;
	}	   
	/**
	 * This method returns the properties object of a specific file
	 * @author :
	 * @since : 
	 */
	public static Properties readTestDataPropertiesFile(String fileName) {
		String strProjDir;
		strProjDir = System.getProperty("user.dir");
		String strTestConfigFilePath = strProjDir + "//src//test//resources//testdata//" + GlobalVars.glStrComponent + "//" ;


		Properties prop = getPropertyFile(strTestConfigFilePath, fileName);
	    return prop;
	   }
}
