package nvr.sat.objectMethods;

import io.netty.util.Timeout;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import nvr.sat.factories.PageObjectDirectory;
import nvr.sat.factories.WebDriverFactory;

import org.apache.logging.log4j.*;


public class WebDriverMethods extends PageObjectDirectory{
	private static final Logger LOGGER = LogManager.getLogger(WebDriverMethods.class);
	private static WebDriver driver = new WebDriverFactory().getWebDriver();

	static WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(20));
	/**
	 * clickElement method: clicked on specified WebElement
	 * 
	 * @param className: Class qualified name where the test object belongs to
	 * @param objectName: WebElement name 
	 * @return true or false
	 */
	public static boolean clickElement(String className, String objectName) throws Exception{
		boolean clickElement = false;

			WebElement element = getElement(className, objectName);
			wait.until(ExpectedConditions.visibilityOf(element));
			if (element.isDisplayed()){		
				if (element.isEnabled()) {
					element.click();
					//JavascriptExecutor executor = (JavascriptExecutor)driver;
					//executor.executeScript("arguments[0].click();", element); 	
					clickElement = true;
					LOGGER.debug("clickElement Method: Clicked successfully on <" + objectName + ">");
				}else {					
					LOGGER.error("clickElement Method: WebElement <" + objectName + "> doesn't enabled in the page");
				}
				
			}else {
				LOGGER.error("clickElement Method: WebElement <" + objectName + "> doesn't display in the page");
			}

		return clickElement;
	}
	
	/**
	 * getElementText method: returns the text of a given object
	 * 
	 * @param className: Class qualified name where the test object belongs to
	 * @param objectName: WebElement name 
	 * @return String 
	 */	
	public static String getElementText(String className, String objectName) throws Exception{
		String elementText = "";
			WebElement element = getElement(className, objectName);
			wait.until(ExpectedConditions.visibilityOf(element));
			if (element.isDisplayed()){
					elementText = element.getText();				
					LOGGER.debug("getElementText Method: Text retrieved successfully on <" + objectName + ">");
				
			}else {
				LOGGER.error("getElementText Method: WebElement <" + objectName + "> isn't displayed in the page");
			}

		return elementText;
	}

	
	
	/**
	 * getAttributeValue method: returns the value of a given HTML Tag Attribute
	 * 
	 * @param className: Class qualified name where the test object belongs to
	 * @param objectName: WebElement name 
	 * @param attributeName: Attribute name
	 * @return String 
	 */	
	public static String getAttributeValue(String className, String objectName, String attributeName) throws Exception{
		String elementText = "";
			WebElement element = getElement(className, objectName);
			wait.until(ExpectedConditions.visibilityOf(element));
			if (element.isDisplayed()){
					elementText = element.getAttribute(attributeName);				
					LOGGER.debug("getAttributeValue Method: Attribute retrieved successfully on <" + objectName + ">");
				
			}else {
				LOGGER.error("getAttributeValue Method: WebElement <" + objectName + "> isn't displayed in the page");
			}
		return elementText;
	}
	
	public WebDriverMethods() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * setText method : Use this method to simulate typing into an WebElement, which may set its value.  
	 * 
	 * @param className: Class qualified name where the test object belongs to
	 * @param objectName: WebElement name
	 * @param value: character sequence what entered
	 * @return: true or false
	 */
	
	public static boolean setText(String className, String objectName, String value) throws Exception {
		boolean typeElement = false;

			WebElement element = getElement(className, objectName);
			wait.until(ExpectedConditions.visibilityOf(element));
			if (element.isDisplayed()){
				if (element.isEnabled()) {
					element.sendKeys(value);
					element.sendKeys(Keys.TAB);
					typeElement=true;					
					LOGGER.debug("Type <" + value + "> successfully in the WebElement <" + objectName + ">");
				}else {					
					LOGGER.error("WebElement <" + objectName + "> doesn't enabled in the page");
				}
				
			}else {				
				LOGGER.error("WebElement <" + objectName + "> doesn't display in the page");
			}

		return typeElement;
	}	
	
	
	/**
	 * clearText method : Use this method to clear the text of an input  
	 * 
	 * @param className: Class qualified name where the test object belongs to
	 * @param objectName: WebElement name
	 * @return: true or false
	 */
	
	public static boolean clearText(String className, String objectName) throws Exception {
		boolean typeElement = false;

			WebElement element = getElement(className, objectName);
			wait.until(ExpectedConditions.visibilityOf(element));
			if (element.isDisplayed()){
				if (element.isEnabled()) {
					element.clear();
					typeElement=true;					
					LOGGER.debug("Clear successfully in the WebElement <" + objectName + ">");
				}else {					
					LOGGER.error("WebElement <" + objectName + "> doesn't enabled in the page");
				}
				
			}else {				
				LOGGER.error("WebElement <" + objectName + "> doesn't display in the page");
			}
		return typeElement;
	}	
	
	/**
	 * verifyElement method:This method verify that the specified WebElement is display or not in the current page
	 * @param className: Class qualified name where the test object belongs to
	 * @param objectName: WebElement name
	 * @return: true or false
	 */
	public static boolean verifyElement(String className, String objectName) throws Exception{
		boolean bFound = false;

			WebElement element = getElement(className, objectName);
			wait.until(ExpectedConditions.visibilityOf(element));
			if(element.isDisplayed()){
				bFound=true;
				LOGGER.debug("verifyElement method: WebElement <" + objectName + "> displayed.");
			}else{
				LOGGER.error("verifyElement method: WebElement <" + objectName + "> doesn't display in the page");
			}

		return bFound;
	}
	
	
	/**
	 * verifyElements method:This method verify that the specified WebElements are display or not in the current page
	 * @param className: Class qualified name where the test object belongs to
	 * @param objectName: WebElement name
	 * @return: true or false
	 */
	public static boolean verifyElements(String className, String objectName) throws Exception{
		boolean bFound = false;

			List<WebElement> elementList = getElements(className, objectName);
			wait.until(ExpectedConditions.visibilityOfAllElements(elementList));
			for(WebElement element : elementList) {
				if(element.isDisplayed()){
					bFound = true;
				} else {
					bFound = false;
					break;
				}
			}
			if(bFound=true) {
			LOGGER.debug("verifyElement method: WebElements <" + objectName + "> displayed.");
			}else{
				LOGGER.error("verifyElement method: WebElements <" + objectName + "> doesn't display in the page");
			}

		return bFound;
	}


	/**
	 * selectByValue method:This method select specified value from given WebElement <drop down list>
	 * @param className: Class qualified name where the test object belongs to
	 * @param objectName: WebElement name
	 * @param value: Value you want to select
	 * @return: true or false
	 */
	public static boolean selectByValue(String className, String objectName, String value) throws Exception{
		boolean bfound = false;

			WebElement element = getElement(className, objectName);
			wait.until(ExpectedConditions.visibilityOf(element));
			Select select = new Select(element);
			select.selectByValue(value);
			bfound = true;
			LOGGER.debug("selectByValue method: Value <" + value + "> select from <" + objectName + "> dropdown list");

		return bfound;
	}
	
	/**
	 * selectByText method:This method select specified text from given WebElement <drop down list>
	 * @param className: Class qualified name where the test object belongs to
	 * @param objectName: WebElement name
	 * @param text: text value you want to select
	 * @return: true or false
	 */	
	public static boolean selectByText(String className, String objectName, String text) throws Exception{
		boolean bfound = false;

			WebElement element = getElement(className, objectName);
			wait.until(ExpectedConditions.visibilityOf(element));
			Select select = new Select(element);
			select.selectByVisibleText(text);
			bfound = true;
			LOGGER.debug("selectByText method: text <" + text + "> select from <" + objectName + "> dropdown list");

		return bfound;
	}

	/**
	 * 
	 * selectByIndex method:This method select specified index value from given WebElement <drop down list>
	 * @param className: Class qualified name where the test object belongs to
	 * @param objectName: WebElement name
	 * @param index: index number you want to select
	 * @return: true or false
	 */	
	public static boolean selectByIndex(String className, String objectName, int index) throws Exception{
		boolean bfound = false;

			WebElement element = getElement(className, objectName);
			wait.until(ExpectedConditions.visibilityOf(element));
			Select select = new Select(element);
			select.selectByIndex(index);
			bfound = true;
			LOGGER.debug("selectByIndex method: Index <" + index + "> select from <" + objectName + "> dropdown list");

		return bfound;
	}
	

	public static void selectRadioButton(String className, String objectName, String attName, String attValue) throws Exception{
		boolean objFound = false;
	
			List<WebElement> elements = getElements(className, objectName);
			wait.until(ExpectedConditions.visibilityOfAllElements(elements));
			for (WebElement element:elements){
				String attVal="";
				if (attName.equalsIgnoreCase("text")){
					attVal=element.getText().trim();
				}else{
					attVal=element.getAttribute(attName).trim();
				}
				if(attVal.contains(attValue)){
					element.click();
					objFound=true;
					LOGGER.debug("<"+ attVal +"> select successfully from WebElement <" + objectName+">.");
					break;
				}
			}
			if (objFound=false){
				LOGGER.error("<"+ attValue +"> doesn't exists in the WebElement <" + objectName+">.");
			}

	}
	
	
	/**
	 * handleCheckBox method handle (checked or unchecked) checkbox based on status parameter
	 * 
	 * @param className: Class qualified name where the test object belongs to
	 * @param objectName : WebElement name
	 * @param status: true or false, true means check the checkbox and false means uncheck the checkbox
	 */
	
	public static void handleCheckBox(String className, String objectName, boolean status) throws Exception{

			WebElement element = getElement(className, objectName);
			wait.until(ExpectedConditions.visibilityOf(element));
			if(status==true){
				if(!element.isSelected()){
					element.click();
					LOGGER.debug("WebElement <" + objectName + "> checked successfully.");
				}
			}else{
				if(element.isSelected()){
					element.click();
					LOGGER.debug("WebElement <" + objectName + "> unchecked successfully.");
				}
			}

	}
	
	/**
	 * getElement method return the WebElement from specified class.
	 * @param className: Class qualified name where the test object belongs to
	 * @param elementName: WebElement name
	 * @return: webElement
	 */
	private static WebElement getElement(String className, String elementName){
		WebElement element = null;
		try {
			Class<?> cls = Class.forName(className);
			Constructor<?> ctor = cls.getConstructors()[0];
			Field field = cls.getDeclaredField(elementName);
			field.setAccessible(true);
			Object object = field.get( ctor.newInstance(new Object[]{driver}));
			element = (WebElement) object;
		} catch (ClassNotFoundException | NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return element;
	}
	
	/**
	 * getElement method return the List of WebElement from specified class.
	 * @param className: Class qualified name where the test object belongs to
	 * @param elementName: WebElement name
	 * @return: webElement
	 */
	private static List<WebElement> getElements1(String className, String elementName){
		List<WebElement> elements = null;
		try {
			Class<?> cls = Class.forName(className);
			Constructor<?> ctor = cls.getConstructors()[0];
			Field field = cls.getDeclaredField(elementName);
			field.setAccessible(true);
			Object object = field.get( ctor.newInstance(new Object[]{driver}));
			elements = (List<WebElement>) object;
		} catch (ClassNotFoundException | NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return elements;
	}
	
	@SuppressWarnings("unchecked")
	private static List<WebElement> getElements(String className, String elementName) {
	    try {
	        // Load the class dynamically
	        Class<?> pageClass = Class.forName(className);

	        // Get the constructor that accepts WebDriver
	        Constructor<?> constructor = pageClass.getDeclaredConstructor(WebDriver.class);
	        constructor.setAccessible(true);

	        // Ensure driver is initialized
	        if (driver == null) {
	            throw new IllegalStateException("WebDriver instance is null. Initialize driver before calling getElements().");
	        }

	        // Create an instance of the Page Object
	        Object pageInstance = constructor.newInstance(driver);

	        // Get the field (element list)
	        Field field = pageClass.getDeclaredField(elementName);
	        field.setAccessible(true);

	        Object fieldValue = field.get(pageInstance);

	        // Validate type
	        if (fieldValue instanceof List<?>) {
	            List<?> list = (List<?>) fieldValue;
	            if (!list.isEmpty() && list.get(0) instanceof WebElement) {
	                return (List<WebElement>) list;
	            } else {
	                throw new IllegalArgumentException("Field '" + elementName + "' is not a List<WebElement>.");
	            }
	        } else {
	            throw new IllegalArgumentException("Field '" + elementName + "' is not a List.");
	        }

	    } catch (ClassNotFoundException e) {
	        System.err.println("Class not found: " + className);
	    } catch (NoSuchMethodException e) {
	        System.err.println("Constructor with WebDriver not found in class: " + className);
	    } catch (NoSuchFieldException e) {
	        System.err.println("Field not found: " + elementName + " in class " + className);
	    } catch (InvocationTargetException e) {
	        System.err.println("Error while invoking constructor for class: " + className);
	        e.getTargetException().printStackTrace();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    // Return empty list if anything fails
	    return Collections.emptyList();
	}
	
	/**
	 * openApplication method open specified application in given WebDriver
	 * @param driver: WebDriver
	 * @param appUrl: application URL
	 * @return: true/false
	 */
	public static boolean openApplication(WebDriver driver, String appUrl){
		boolean openApplication=false;
		try{
			driver.get(appUrl);
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));			
			openApplication=true;
			LOGGER.debug("openApplication method: Application <" + appUrl +"> open successfully.");
		}catch(Exception e){
			LOGGER.error("openApplication method return exception." + e.getMessage()); 
		}
		return openApplication;
	}

	/**
	 * closeApplication method open specified application in given WebDriver
	 * @param driver: WebDriver
	 * @param appUrl: application URL
	 * @return: true/false
	 */
	public static boolean closeApplication(WebDriver driver){
		boolean closeApplication=false;
		try{
			driver.quit();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));			
			LOGGER.debug("closeApplication method: Application closed successfully.");
		}catch(Exception e){
			LOGGER.error("closeApplication method return exception." + e.getMessage()); 
		}
		return closeApplication;
	}
	
	
	/**
	 * verifyClickable method:This method verify that the specified WebElement is enabled
	 * @param className: Class qualified name where the test object belongs to
	 * @param objectName: WebElement name
	 * @return: true or false
	 */
	
	public static boolean verifyClickable(String className, String objectName) throws Exception{
		boolean bFound = false;

			WebElement element = getElement(className, objectName);
			wait.until(ExpectedConditions.visibilityOf(element));
			if(element.isEnabled()){
				bFound=true;
				LOGGER.debug("verifyClickable method: WebElement <" + objectName + "> clickable.");
			}else{
				LOGGER.error("verifyClickable method: WebElement <" + objectName + ">  is not clickable");
			}

		return bFound;
	}
	
	
	/**
	 * verifyElementHasClass method:This method verify if a Element has a class
	 * @param className: Class qualified name where the test object belongs to
	 * @param objectName: WebElement name
	 * @param classToContain: Class Element should contain
	 * @return: true or false
	 */
	
	public static boolean verifyElementHasClass(String className, String objectName, String classToContain) throws Exception{
		boolean bFound = false;
	
			WebElement element = getElement(className, objectName);
			wait.until(ExpectedConditions.attributeToBeNotEmpty(element, "class"));
			String[] listOfClass = element.getAttribute("class").trim().split(" ");
			if(Arrays.asList(listOfClass).contains(classToContain)) {
				bFound=true;
			}

		return bFound;
	}
	
	
	/**
	 * isSelected method:This method verify that the specified WebElement is enabled
	 * @param className: Class qualified name where the test object belongs to
	 * @param objectName: WebElement name
	 * @return: true or false
	 */
	public static boolean isElementSelected(String className, String objectName) throws Exception{
		boolean bFound = false;

			WebElement element = getElement(className, objectName);
			wait.until(ExpectedConditions.visibilityOf(element));
			if(element.isSelected()){
				bFound=true;
				LOGGER.debug("verifyElement method: WebElement <" + objectName + ">  is selected.");
			}else{
				LOGGER.error("verifyElement method: WebElement <" + objectName + "> is not selected");
			}

		
		return bFound;
	}
	
	/**
	 * isEnabled method:This method verify that the specified WebElement is enabled
	 * @param className: Class qualified name where the test object belongs to
	 * @param objectName: WebElement name
	 * @return: true or false
	 */
	public static boolean isElementEnabled(String className, String objectName) throws Exception{
		boolean bFound = false;

			WebElement element = getElement(className, objectName);
			wait.until(ExpectedConditions.visibilityOf(element));
			if(element.isEnabled()){
				bFound=true;
				LOGGER.debug("verifyElement method: WebElement <" + objectName + ">  is enabled.");
			}else{
				LOGGER.error("verifyElement method: WebElement <" + objectName + "> is not enabled");
			}
		return bFound;
	}
	
	
	/**
	 * isListofelementsEnabled method:This method verify that the list of elements is enabled
	 * @param className: Class qualified name where the test object belongs to
	 * @param objectName: List of WebElement names
	 * @return: true or false
	 */
	public static boolean isListofElementsEnabled(String className, String objectName) throws Exception{
		boolean bFound = false;
	
			List<WebElement> elementList = getElements(className, objectName);
			wait.until(ExpectedConditions.visibilityOfAllElements(elementList));
			for(WebElement element : elementList) {
				if(element.isEnabled()){
					bFound = true;
				} else {
					bFound = false;
					break;
				}
			}
			if(bFound=true) {
			LOGGER.debug("verifyElement method: All WebElements <" + objectName + "> are enabled.");
			}else{
				LOGGER.error("verifyElement method: Some WebElements <" + objectName + "> are not enabled");
			}

		return bFound;
	}
	
	/**
	 * VerifyElementText method:This method verify that the list of elements is enabled
	 * @param className: Class qualified name where the test object belongs to
	 * @param objectName: List of WebElement names
	 * @return: true or false
	 */
	public static boolean verifyElementText(String className, String objectName, String textToVerify) throws Exception{
		boolean bFound = false;

			WebElement element = getElement(className, objectName);
			wait.until(ExpectedConditions.visibilityOf(element));
			if(element.getText().equals(textToVerify)){
				bFound=true;
				LOGGER.debug("verifyElementText method: WebElement <" + objectName + ">  contains the text.");
			}else{
				LOGGER.error("verifyElementText method: WebElement <" + objectName + "> does not contain the text");
			}

		return bFound;
		
	}
	
	
	/**
	 * switchToFrame method: This method switches to an Iframe based on ID.
	 * * @param iFrameName: ID of Iframe to switch to.
	 */
	public static void switchToFrame(String className, String objectName, String Iframe) {
		WebElement frameLocator = getElement(className, objectName);
		wait.until(ExpectedConditions.visibilityOf(frameLocator));
		driver.switchTo().frame(Iframe);

	}
	
	/**
	 * getSelectOptions method return the List of Strings for a Select Option
	 * @param className: Class qualified name where the test object belongs to
	 * @param elementName: WebElement name
	 * @return: List<String>
	 */
	public static List<String> getSelectOptions(String className, String elementName) throws Exception{
		List<String> elements = null;

			WebElement element = getElement(className, elementName);
			wait.until(ExpectedConditions.visibilityOf(element));
			if (element.isEnabled()){
				Select select = new Select(element);
				elements = select.getOptions().stream()
						.map(option -> option.getText())
						.collect(Collectors.toList());
				LOGGER.debug("getSelectOptions Method: Select Options recieve for <" + elementName + ">");
				
			}else {
				LOGGER.error("getSelectOptions Method: WebElement <" + elementName + "> isn't displayed in the page");
			}

		return elements;
	}


	//VerifyFieldLabel

	//VerifySelectedText [From dropdown]

	//VerifyHeaderExists

}
