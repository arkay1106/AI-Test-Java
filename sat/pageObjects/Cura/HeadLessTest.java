package nvr.sat.pageObjects.Cura;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
//import org.openqa.selenium.htmlunit.HtmlUnitDriver;



import io.github.bonigarcia.wdm.WebDriverManager;

public class HeadLessTest {
	
	public static void main(String[] args) {
		WebDriverManager.chromedriver().setup();
		ChromeOptions co=new ChromeOptions();
		co.addArguments("--headless");
		WebDriver dr = new ChromeDriver(co);
		dr.get("https://katalon-demo-cura.herokuapp.com/");
		System.out.println(dr.getTitle());
		dr.close();
		dr.quit();
		
		
/*		WebDriver dr = new HtmlUnitDriver();
		dr.get("https://katalon-demo-cura.herokuapp.com/");
		System.out.println(dr.getTitle());
		dr.close();
		dr.quit();*/
		
	}

}
