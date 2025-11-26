package nvr.sat.factories;

import org.openqa.selenium.WebDriver;

import nvr.sat.pageObjects.Cura.CuraHomePage;

public class CuraPageObjectsFactory {
	//Selenium driver object
	private WebDriver driver;
	
	//Instance of all page objects
	private CuraHomePage curaHomePage;

	//Constructor
	public CuraPageObjectsFactory(WebDriver driver) {
		this.driver = driver;
	}
	
	//Returns CURAPage object
	public CuraHomePage getCuraHomePage() {
		if(null == curaHomePage) {
			curaHomePage = new CuraHomePage(driver);
			return curaHomePage;
		}else {
			return curaHomePage;
		}
	}

}
