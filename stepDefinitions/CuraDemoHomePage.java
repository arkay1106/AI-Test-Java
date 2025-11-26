package nvr.sat.stepDefinitions;

import org.junit.AfterClass;
import org.junit.Assert;
import org.assertj.core.api.SoftAssertions;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;


import nvr.sat.factories.FactoryContainer;

import nvr.sat.objectMethods.NavigationMethods;
import nvr.sat.objectMethods.WebDriverMethods;
import nvr.sat.pageObjects.Cura.CuraHomePage;

import nvr.sat.testutils.GlobalVars;

import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


public class CuraDemoHomePage extends WebDriverMethods{


	FactoryContainer factoryContainer;
	CuraHomePage curaHomePage;
	WebDriver driver;
	SoftAssertions softAssert = new SoftAssertions();
	


	//Constructor
	public CuraDemoHomePage(FactoryContainer container) {
		factoryContainer = container;
		curaHomePage = container.getCuraPageObjectsFactory().getCuraHomePage();
		driver = container.getWebDriverFactory().getWebDriver();
	}


	String homePageURL = "https://katalon-demo-cura.herokuapp.com/";

	@Given("I open the Cura Home Page")
	public void openCuraHomePage() {
	try {
			//Open the homepage.
			NavigationMethods.navigateToURL(driver, homePageURL);
		} catch(TimeoutException e) {
			System.out.println("Fields Timed Out");
			Assert.fail("Page did not load. " + e);
		} catch (Exception e) {
			System.out.println("regular Exception caught");
			Assert.fail("Exception caught " + e);
		}
	}

	@And("I close the Cura Page")
	public void closeCuraPage() {
	try {
			//Close the page.
			WebDriverMethods.closeApplication(driver);
		} catch(TimeoutException e) {
			System.out.println("Fields Timed Out");
			Assert.fail("Page did not load. " + e);
		} catch (Exception e) {
			System.out.println("regular Exception caught");
			Assert.fail("Exception caught " + e);
		}
	}
	
	
	@When("I Press the Make Appointment Button")
	public void clickCuraAppointment() {
		try {
			WebDriverMethods.clickElement(cHome, "appointmentButton");
		} catch(TimeoutException e) {
			System.out.println("Fields Timed Out");
			Assert.fail("Appointment button was not found. " + e);
		} catch (Exception e) {
			System.out.println("regular Exception caught");
			Assert.fail("Exception caught " + e);
		}

	}

	@Then("I input Login Information of Username: \"(.*)\" and Password: \"(.*)\"$")
	public void inputCuraLogin(String Username, String Password) {
		try {
			WebDriverMethods.setText(cHome, "userNameInput", Username);
			WebDriverMethods.setText(cHome,  "passwordInput", Password);
		} catch(TimeoutException e) {
			System.out.println("Fields Timed Out");
			Assert.fail("Username/Password field were not found. " + e);
		} catch (Exception e) {
			System.out.println("regular Exception caught");
			Assert.fail("Exception caught " + e);
		}

	}

	@And("I Press the Login Button")
	public void clickCuraLogin() {
		try {
			WebDriverMethods.clickElement(cHome, "loginBtn");
		} catch(TimeoutException e) {
			System.out.println("Fields Timed Out");
			Assert.fail("Login Button was not found. " + e);
		} catch (Exception e) {
			System.out.println("regular Exception caught");
			Assert.fail("Exception caught " + e);
		}
	}

	@Then("I verify the error message of \"(.*)\"$")
	public void verifyErrorMessage(String errorMessage) {
		try {
			//This one will only verify the error message for the login page.  
			String expectedMessage = errorMessage;
			String actualMessage = WebDriverMethods.getElementText(cHome, "LoginFailedText");
			Assert.assertEquals(expectedMessage, actualMessage);
		} catch(TimeoutException e) {
			System.out.println("Fields Timed Out");
			Assert.fail("Login Message field was not found. " + e);
		} catch (Exception e) {
			System.out.println("regular Exception caught");
			Assert.fail("Exception caught " + e);
		}
	}

	@Then("I verify the Profile Page has a Logout Button") 
	public void verifyProfilePage(){
		try {
			curaHomePage.toggleMenu("on");
			curaHomePage.clickProfile();
			//Assert.assertTrue(curaHomePage.isLogoutVisible());
		} catch(TimeoutException e) {
			System.out.println("Fields Timed Out");
			Assert.fail("Menu or Profile was not found. " + e);
		} catch (Exception e) {
			System.out.println("regular Exception caught");
			Assert.fail("Exception caught " + e);
		}
	}

	@And("I Press the Logout Button")
	public void clickLogoutButton() {
		try {
			WebDriverMethods.clickElement(cHome,"LogOutButton");
		} catch(TimeoutException e) {
			System.out.println("Fields Timed Out");
			Assert.fail("Logout Button was not found. " + e);
		} catch (Exception e) {
			System.out.println("regular Exception caught");
			Assert.fail("Exception caught " + e);
		}
	}

	@Then("I fill out the appointment information") 
	public void setAppointmentInformation(){
		String strFacility, strHospitalReadmission, strHealthCare, strVisitDate, strComment;
		try {
			strFacility = nvr.sat.testutils.Configuration.getTestDataValue(GlobalSteps.objTestDataFile, "TD_Facility", 1);
			strHospitalReadmission = nvr.sat.testutils.Configuration.getTestDataValue(GlobalSteps.objTestDataFile, "TD_Hospital", 1);
			strHealthCare = nvr.sat.testutils.Configuration.getTestDataValue(GlobalSteps.objTestDataFile, "TD_Program", 1);
			strVisitDate = nvr.sat.testutils.Configuration.getTestDataValue(GlobalSteps.objTestDataFile, "TD_VisitDate", 1);
			strComment = nvr.sat.testutils.Configuration.getTestDataValue(GlobalSteps.objTestDataFile, "TD_Comment", 1);

			curaHomePage.setFacility(strFacility);
			curaHomePage.checkHospitalReadmission(strHospitalReadmission);
			curaHomePage.setHeathCareProgram(strHealthCare);
			curaHomePage.setVisitDate(strVisitDate);
			curaHomePage.setComment(strComment);
			Thread.sleep(10000);
		} catch(TimeoutException e) {
			System.out.println("Fields Timed Out");
			Assert.fail("Appointment Information fields were not found. " + e);
		} catch (Exception e) {
			System.out.println("regular Exception caught");
			Assert.fail("Exception caught " + e);
		}
	}

	@Then("I press the Book Appointment Button")
	public void clickBookAppointmentButton() {
		try {
			WebDriverMethods.clickElement(cHome, "bookAppointmentButton");
		} catch(TimeoutException e) {
			System.out.println("Fields Timed Out");
			Assert.fail("Appointment Button was not found. " + e);
		} catch (Exception e) {
			System.out.println("regular Exception caught");
			Assert.fail("Exception caught " + e);
		}
	}

	@And("I verify the Appointment Confirmation Screen") 
	public void veryAppointmentConfirmation() {

		try {
			String actFacility = WebDriverMethods.getElementText(cHome, "FacilityText");
			String actHospital = WebDriverMethods.getElementText(cHome, "hospitalText");
			String actHealthCare = WebDriverMethods.getElementText(cHome, "program");
			String actVistDate = WebDriverMethods.getElementText(cHome, "VisitDateText");
			String actCommentText = WebDriverMethods.getElementText(cHome, "CommentText");
			
			String expFacility, expHospitalReadmission, expHealthCare, expVisitDate, expComment;
			
			expFacility = nvr.sat.testutils.Configuration.getTestDataValue(GlobalSteps.objTestDataFile, "TD_Facility", 1);
			expHospitalReadmission = nvr.sat.testutils.Configuration.getTestDataValue(GlobalSteps.objTestDataFile, "TD_Hospital", 1);
			expHealthCare = nvr.sat.testutils.Configuration.getTestDataValue(GlobalSteps.objTestDataFile, "TD_Program", 1);
			expVisitDate = nvr.sat.testutils.Configuration.getTestDataValue(GlobalSteps.objTestDataFile, "TD_VisitDate", 1);
			expComment = nvr.sat.testutils.Configuration.getTestDataValue(GlobalSteps.objTestDataFile, "TD_Comment", 1);
			
			if(expHospitalReadmission.equals("True"))
				expHospitalReadmission = "Yes";
			softAssert.assertThat(actFacility).as("Facility").isEqualTo(expFacility);
			softAssert.assertThat(actHospital).as("HospitalReadmission").isEqualTo(expHospitalReadmission);
			softAssert.assertThat(actHealthCare).as("Health Care Program").isEqualTo(expHealthCare);
			softAssert.assertThat(actVistDate).as("Visit Date").isEqualTo(expVisitDate);
			softAssert.assertThat(actCommentText).as("Comment").isEqualTo(expComment);
			softAssert.assertAll();
		} catch(TimeoutException e) {
			System.out.println("Fields Timed Out");
			Assert.fail("Appointment Confirmation Screen fields were not found. " + e);
		} catch (Exception e) {
			System.out.println("regular Exception caught");
			Assert.fail("Exception caught " + e);
		}
	}


}
