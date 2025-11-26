package nvr.sat.stepDefinitions;


import java.util.List;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.assertj.core.api.SoftAssertions;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;



import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;

import nvr.sat.factories.*;
import nvr.sat.objectMethods.NavigationMethods;
import nvr.sat.objectMethods.WebDriverMethods;
//import nvr.sat.pageObjects.Cura.CuraHome_Ramana;
import nvr.sat.pageObjects.Cura.EmbedScreenshot;

public class CuraDemo extends WebDriverMethods{

	FactoryContainer factoryContainer;
	//CuraHome_Ramana curaHome_S;
	
	SoftAssertions softAssert = new SoftAssertions();
	WebDriverFactory wdf = new WebDriverFactory();
	WebDriver driver;
	//Constructor

	public CuraDemo(FactoryContainer container) {
		factoryContainer = container;
		//curaHome_S=container.getCuraHome().getCuraHome_Shariful();
		driver = container.getWebDriverFactory().getWebDriver();

	}

	String homePageURL = "https://katalon-demo-cura.herokuapp.com/";

	@Given("I navigate to Cura System homepage")
	public void navigateCuraHomepage() {
		driver= new WebDriverFactory().getWebDriver();
		openApplication(driver, homePageURL);
		driver.manage().deleteAllCookies();
		driver.get(homePageURL);
		System.out.println("Done: I navigate to Cura System homepage");
	}

	@When("I click Make Appointment button")
	public void clickAppointmentButton() {
		try {
		clickElement(cHome, "appointmentButton");
		System.out.println("Done: I click Make Appointment button");
		}catch (Exception E) {
			
		}
	}

	@When("I enter username {string} and password {string}")
	public void enterUserNamePassword(String username, String password) throws InterruptedException {
		try {
		setText(cHome, "userNameInput", username);
		setText(cHome, "passwordInput", password); 
		System.out.println("Done: I enter username and password");
		} catch (Exception E) {
			
		}
	}
	@When ("I click Log in button")
	public void clickLoginButton(){
		try {
			clickElement(cHome, "loginBtn");
			System.out.println("Done: I click Log in button"); 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//Scenario sc;
	@Then("I should be able to login successfully")
	public void verifyLogin()  {
		try {
			verifyElement(cHome, "bookAppointmentButton");
			System.out.println("Done: I should be able to login successfully");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Then("I select facility {string}")
	public void i_select_facility(String facility) {
		try {
			selectByText(cHome, "Facility", facility);
			System.out.println("Done: I select facility ");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Then("Enabled apply for hospital readmission button")
	public void enabled_apply_for_hospital_readmission_button() {
		try {
			handleCheckBox(cHome,"checkHospotal", true);
			System.out.println("Done: Enabled apply for hospital readmission button");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Then("Select {string} program")
	public void select_program(String program) {
		//handleCheckBox(getElement(cHome, "checkHospotal"),Boolean.TRUE);	
		try {
			//handleCheckBox(cHome, "checkHospotal", true);
			selectRadioButton(cHome, "programText", "value", program);
			System.out.println("Done: Select program");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Then("Enter visit date as {string}")
	public void enter_visit_date_as(String date) {
		//getElement(cHome, "VisitDate").sendKeys(date);
		try {
			setText(cHome, "VisitDate", date);
			System.out.println("Done: Enter visit date");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Then("click on book appointment button")
	public void click_on_book_appointment_button() {
		try {
			clickElement(cHome, "bookAppointmentButton");
			System.out.println("Done: click on book appointment button");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

	@Then("I verify the Appointment Confirmation msg")
	public void i_verify_the_appointment_confirmation_msg() {
		try {
			verifyElement(cHome, "GoToHomePageLink");
			System.out.println("Done: I verify the Appointment Confirmation msg");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Before
	public void before(){
			System.out.println("I am inside Before annotation...." + driver.toString());
			driver = new FactoryContainer().getWebDriverFactory().getWebDriver();
			SessionId s = ((RemoteWebDriver) driver).getSessionId();
			System.out.println("I am outside Before annotation...." + driver.toString());
			System.out.println("Session Id: " + s);
	}
	
	
	
}
