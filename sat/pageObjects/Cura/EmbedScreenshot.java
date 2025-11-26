package nvr.sat.pageObjects.Cura;

import io.cucumber.java.Scenario;
import io.cucumber.java.it.Data;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;




import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import nvr.sat.factories.WebDriverFactory;

public class EmbedScreenshot {

	private static WebDriver driver = new WebDriverFactory().getWebDriver();
	
	public static String getBase64ScreenShot() throws IOException{
	
		String screenshotBase64="";
		File src = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("mmddyyyy_HHmmss"); 
		FileUtils.copyFile(src, new File(System.getProperty("user.dir")+"\\screenshotdir" + "image_" + date + ".png"));
		return screenshotBase64;
	}
	
	public static byte[] getByteScreenshot() throws IOException{
		File src = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		byte[] fileContent = FileUtils.readFileToByteArray(src);
		return fileContent;
	}
	
/*	public void tearDown(Scenario scenario) {
	    if (scenario.isFailed()) {
	      // Take a screenshot...
	      final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
	   // embed it in the report.
	      scenario.embed(screenshot, "image/png"); 
	    }
	}*/

	public static void screenshot(Scenario scenario, long ms)
	{

	try {
	    TakesScreenshot ts = (TakesScreenshot) driver;
	    File source = ts.getScreenshotAs(OutputType.FILE);
	    FileUtils.copyFile(source, new File("./test-output/screenshots/"+ms+"cura.png"));
	 //    byte[] data = FileUtils.readFileToByteArray(source);
	  //  byte[] data1 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
	  //  System.out.println("ScreenShot Taken");
	  //  scenario.attach(data, "image/png", "");
	    
	} 
	catch (Exception e) 
	{
		//e.printStackTrace();
	    System.out.println("Exception while taking ScreenShot "+e.getMessage());
	}
	}	
}
