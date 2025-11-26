package nvr.sat.objectMethods;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;

public class NavigationMethods {
	/** Global method to navigate to a given URL
	 * @throws InterruptedException 
	 */
	public static void navigateToURL(WebDriver driver, String url) 
	{ try {
		driver.get(url);
		driver.manage().window().maximize();
		//driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		//Thread.sleep(5000); 
	}catch (Exception e) 
	{ // TODO Auto-generated catch block e.printStackTrace()
	}
	}
	/** Global method to get current URL
	 */
	public static String getCurrentURL(WebDriver driver) 
	{
		return driver.getCurrentUrl();
	}
	
	/** Global method to go forward on the browser
	 */
	public static void navigateForward(WebDriver driver) 
	{
		driver.navigate().forward();
	}
	
	/** Global method to go back on the browser
	 */
	public static void navigateBack(WebDriver driver) 
	{
		driver.navigate().back();
	}
	
	/** Global method to refresh browser
	 */
	public static void refreshBrowser(WebDriver driver) 
	{
		driver.navigate().refresh();
	}

}
