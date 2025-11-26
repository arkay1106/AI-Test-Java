package nvr.sat.utils;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
/**
 * This class contains all the methods to UI Properties.
 * 
 * 
 */
public class ConfigReader {
	private Properties properties;
	private static ConfigReader configReader;
	private final String strPropertyFilePath = "config.properties";
 
	private ConfigReader() {
		BufferedReader reader;    	
		System.out.println("Config file: " + strPropertyFilePath);
        reader = new BufferedReader(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream(strPropertyFilePath)));
		 properties = new Properties();
		 try {
			 properties.load(reader);
		     reader.close();
		 } catch (IOException e) {
			 e.getMessage();
			 e.getCause();
		     e.printStackTrace();
		 } 
	}
    public static ConfigReader getInstance() {
    	if(configReader == null) {
    		configReader = new ConfigReader();
    	}
        return configReader;
    }
    public String getWebUrl() {
        String webURL = properties.getProperty("webURL");
        if(webURL != null) {
        	System.out.println("ConfigReader.getWebUrl: TestRail base URL : " + webURL);
        	return webURL;
        }else {
        	throw new RuntimeException("ConfigReader.getWebUrl : Property 'webURL' not specified in the config.properties file.");
        }
        
    }
    
    public Browsers getWebBrowser() {
        String webBrowser = properties.getProperty("webBrowser");
        if(webBrowser == null || webBrowser.equalsIgnoreCase("chrome")) {
        	return Browsers.CHROME;
        }else if(webBrowser.equalsIgnoreCase("ie")){
        	return Browsers.IE;
        }else if(webBrowser.equalsIgnoreCase("firefox")){
        	return Browsers.FIREFOX;
        }else if(webBrowser.equalsIgnoreCase("edge")) {
        	return Browsers.EDGE;
        }else if (webBrowser.equalsIgnoreCase("Headless")){
        	return Browsers.HEADLESS;
        }else {
        	throw new RuntimeException("ConfigReader.getWebBrowser : Property 'webBrowser' specified in the config.properties file is not valid.");
        }
    }
    
    public Environments getEnvironment() {
        String env = properties.getProperty("environment");
        if(env == null || env.equalsIgnoreCase("local")) {
        	return Environments.LOCAL;
        }else if(env.equalsIgnoreCase("dev")) {
        	return Environments.DEV;
        }else if(env.equalsIgnoreCase("impl")) {
        	return Environments.IMPL;
        }else {
        	throw new RuntimeException("ConfigReader.getEnvironment : Property 'environment' in the config.properties file is not valid.");
        }
    }

    public String getWebUsername() {
        String webUsername = properties.getProperty("webUsername");
        if(webUsername != null) {
        	System.out.println("ConfigReader.getWebUsername: Username Fetched");
        	return webUsername;
        }else {
        	throw new RuntimeException("ConfigReader.getWebUsername : Property 'webUsername' not specified in the config.properties file.");
        }
    }
    
    public String getWebPassword() {
        String webPassword = properties.getProperty("webPassword");
        if(webPassword != null) {
        	System.out.println("ConfigReader.getWebUsername: Password Fetched");
        	return webPassword;
        }else {
        	throw new RuntimeException("ConfigReader.getWebPassword : Property 'webPassword' not specified in the config.properties file.");
        }
    }
}