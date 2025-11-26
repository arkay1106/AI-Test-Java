package nvr.sat.objectMethods;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ClickMethods {
	/** Global method to click a link or a button
	 */
	public void clickElement(WebElement webElement)
	{
		webElement.click();
	}
}
