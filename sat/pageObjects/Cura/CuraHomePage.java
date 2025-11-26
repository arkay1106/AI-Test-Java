package nvr.sat.pageObjects.Cura;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import nvr.sat.objectMethods.*;;


public class CuraHomePage {
	WebDriver driver;
	WebDriverWait wait;
	
	//Constructor
	public CuraHomePage(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		PageFactory.initElements(driver,this);
		
	}
	
	JavascriptExecutor executor = (JavascriptExecutor) driver;

	//Locaters	
	@FindBy(id="btn-make-appointment")
	private WebElement appointmentButton;
	
	@FindBy(id="txt-username")
	private WebElement userNameInput;
	
	@FindBy(id="txt-password")
	private WebElement passwordInput;
	
	@FindBy(id="btn-login")
	private WebElement loginBtn;
	
	@FindBy(className="text-danger")
	private WebElement LoginFailedText;
	
	@FindBy(id="menu-toggle")
	private WebElement MenuToggle;
	
	@FindBy(id="sidebar-wrapper")
	private WebElement SideBar;
	
	@FindBy(css =".sidebar-nav > li >  a")
	private List<WebElement> sideBarElements;
	
	@FindBy(xpath = "//a[contains(@class, 'btn') and text()='Logout']")
	private WebElement LogOutButton;
	
	@FindBy(id="combo_facility")
	private WebElement Facility;
	
	@FindBy(id="chk_hospotal_readmission")
	private WebElement checkHospotal;
	
	@FindBy(css=".form-group > div > label")
	private List<WebElement> HealthcareProgram;
	
	@FindBy(id="txt_visit_date")
	private WebElement VisitDate;
	
	@FindBy(id="txt_comment")
	private WebElement Comments;
	
	@FindBy(id="btn-book-appointment")
	private WebElement bookAppointmentButton;
	
	@FindBy(id="summary")
	private WebElement SummaryInfo;
	
	@FindBy(id="facility")
	private WebElement FacilityText;
	
	@FindBy(id="hospital_readmission")
	private WebElement hospitalText;
	
	@FindBy(name="programs")
	private List<WebElement> programText;
	
	@FindBy(id="program")
	private WebElement program;
	
	@FindBy(id="visit_date")
	private WebElement VisitDateText;
	
	@FindBy(id="comment")
	private WebElement CommentText;
	
	@FindBy(linkText = "Go to Homepage")
	private WebElement GoToHomePageLink;
	
	//object Methods
	public void toggleMenu(String menuSwitch) {
		wait.until(ExpectedConditions.visibilityOf(MenuToggle));
		switch(menuSwitch.toLowerCase()) {
		case "on":
			if(!(hasClass(SideBar, "active"))) {
				MenuToggle.click();
			} else {
				//The Switch is already on.  Do Nothing
			}
			break;
		case "off" :
			if(hasClass(SideBar, "active")) {
				MenuToggle.click();
			} else {
				//The Switch is already off.  Do Nothing
			}
			break;
		}

		
	}
	
	public boolean hasClass(WebElement element, String classToSearch) {
		String classes = element.getAttribute("class");
		for (String c : classes.split(" ")) {
			if(c.equals(classToSearch)) {
				return true;
			}
		}
		
		return false;
	}

	public void clickProfile() {
		wait.until(ExpectedConditions.visibilityOfAllElements(sideBarElements));
		for(WebElement var : sideBarElements) {
			if (var.getText().equals("Profile")) { 
				var.click();
				break;
				}
		}
	
	}

	public boolean isLogoutVisible() {
		return LogOutButton.isDisplayed();
	}


	public void setFacility(String facilityToSet) {
		wait.until(ExpectedConditions.visibilityOf(Facility));
		Select selectObject = new Select(Facility);
		selectObject.selectByValue(facilityToSet);
		
	}

	public void checkHospitalReadmission(String appointmentHospital) {
		wait.until(ExpectedConditions.visibilityOf(checkHospotal));
		if(appointmentHospital.equals("True")) {
			//If the checkbox is not selected, click it.
			if(!checkHospotal.isSelected())
				checkHospotal.click();
		} else {
			//If the checkbox is already selected, click it to unselect it.
			if(checkHospotal.isSelected())
				checkHospotal.click();
		}
		
	}

	public void setHeathCareProgram(String HealthCareToSet) {
		wait.until(ExpectedConditions.visibilityOfAllElements(HealthcareProgram));
		String radioToSet = "radio_program_" + HealthCareToSet.trim().toLowerCase();
		for(WebElement var : HealthcareProgram) {
			System.out.println(var.getText());
			if (var.getText().equals(HealthCareToSet)){ 
				var.click();
				}
		}
		
	}

	public void setVisitDate(String dateToSet) {
		wait.until(ExpectedConditions.visibilityOf(VisitDate));
		VisitDate.sendKeys(dateToSet);
		
	}

	public void setComment(String commentToSet) {
		wait.until(ExpectedConditions.visibilityOf(Comments));
		Comments.sendKeys(commentToSet);
		
	}




}