package nvr.sat.factories;

import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
//import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.remote.CapabilityType;

import nvr.sat.utils.Browsers;
import nvr.sat.utils.ConfigReader;
import nvr.sat.utils.Environments;

import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.v85.network.Network;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
public class WebDriverFactory {
	//private WebDriver driver;
	private static WebDriver driver;
	private static Environments env;
	private static Browsers browser;
	
	public WebDriverFactory() {
		env = ConfigReader.getInstance().getEnvironment();
		browser = ConfigReader.getInstance().getWebBrowser();
		System.out.println("hello browser:" + browser);
	}
	
	public WebDriver getWebDriver() {
		if(null == driver) {
			driver = createNewWebDriver();
		}
		return driver;
	}
	
	private WebDriver createNewWebDriver() {
		System.setProperty("webdriver.http.factory", "jdk-http-client");

		switch(browser) {
		case CHROME :
			System.out.println("Using chrome Driver:@@@@@@@@@@@@@@@ " );
			WebDriverManager.chromedriver().setup();
			ChromeOptions capability = new ChromeOptions();
			capability.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS,true);
			Map<String, Object> prefs = new HashMap<>();
	        // Disable password manager + credentials service
	        prefs.put("credentials_enable_service", false);
	        prefs.put("profile.password_manager_enabled", false);

	        // Disable save-password and change-password bubbles
	        capability.addArguments("--disable-save-password-bubble");
	        capability.addArguments("--disable-password-manager-reauthentication");
	        capability.addArguments("--disable-features=PasswordManagerOnboarding,PasswordManagerRedesign,PasswordManagerSigninIntercept");
	        
	        // Disable leak detection and password check warnings
	        capability.addArguments("--disable-features=PasswordLeakDetection,PasswordCheck,PasswordManagerOnboarding,PasswordManagerRedesign,PasswordManagerSigninIntercept");

	        // Disable infobars and notifications for extra stability
	        capability.addArguments("--disable-infobars");
	        capability.addArguments("--disable-notifications");
	        capability.addArguments("--start-maximized");

	        capability.addArguments(
	                "--disable-save-password-bubble",
	                "--disable-features=PasswordManagerOnboarding,PasswordManagerRedesign,PasswordManagerSigninIntercept,PasswordLeakDetection,PasswordCheck,SafetyHub,SafeBrowsingEnhancedProtection",
	                "--disable-password-manager-reauthentication",
	                "--disable-notifications",
	                "--disable-infobars",
	                "--disable-extensions",
	                "--no-first-run",
	                "--no-default-browser-check",
	                "--password-store=basic", // fallback password backend (no UI)
	                "--user-data-dir=" + System.getProperty("user.dir") + "/chrome-temp" // clean profile
	        );
	        
	        capability.setExperimentalOption("prefs", prefs);
			driver = new ChromeDriver(capability);
			break;
		case IE :
			WebDriverManager.iedriver().setup();
			driver = new InternetExplorerDriver();
			break;
		case EDGE :
			File localDriver = new File("drivers/msedgedriver.exe");
	        if (localDriver.exists()) {
	            System.out.println("Using local EdgeDriver: ########### " + localDriver.getAbsolutePath());
	            System.setProperty("webdriver.edge.driver", localDriver.getAbsolutePath());
	        } else {
	            System.out.println("Local EdgeDriver not found, trying WebDriverManager...");
	            WebDriverManager.edgedriver()
	                    .cachePath("drivers")        // download to drivers/ folder
	                    .avoidBrowserDetection()     // reduces online checks
	                    .setup();
	        }
			driver = new EdgeDriver();
			break;
		case FIREFOX :
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			break;
		case HEADLESS:
			WebDriverManager.chromedriver().setup();
			ChromeOptions co = new ChromeOptions();
			co.addArguments("--headless");
			driver = new ChromeDriver(co);
			//driver = new HtmlUnitDriver();
			break;
		}

		return driver;
	}
	
	public void clearCache() throws InterruptedException {;
		switch(browser) {
		case CHROME :
			((ChromeDriver) driver).getDevTools().createSession();
			((ChromeDriver) driver).getDevTools().send(Network.clearBrowserCache());
			((ChromeDriver) driver).getDevTools().send(Network.clearBrowserCookies());
			break;
		case IE :
			//Need to figure out how to clear the cache on IE
			break;
		case EDGE :
			((EdgeDriver) driver).getDevTools().createSession();
			((EdgeDriver) driver).getDevTools().send(Network.clearBrowserCache());
			((EdgeDriver) driver).getDevTools().send(Network.clearBrowserCookies());
			break;
		case FIREFOX :
			((FirefoxDriver) driver).getDevTools().createSession();
			((FirefoxDriver) driver).getDevTools().send(Network.clearBrowserCache());;
			((FirefoxDriver) driver).getDevTools().send(Network.clearBrowserCookies());
		case HEADLESS:
			((ChromeDriver) driver).getDevTools().createSession();
			((ChromeDriver) driver).getDevTools().send(Network.clearBrowserCache());
			((ChromeDriver) driver).getDevTools().send(Network.clearBrowserCookies());
			break;
		}
		
		
	}
	
	public void closeWebDriver() {
		driver.close();

	}
	
}
