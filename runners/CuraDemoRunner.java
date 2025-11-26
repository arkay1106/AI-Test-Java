package nvr.sat.runners;
import org.junit.runner.RunWith;


import org.junit.AfterClass;


import nvr.sat.factories.WebDriverFactory;
import io.cucumber.junit.*;


 
@RunWith(Cucumber.class)
@CucumberOptions(
 features = "src/test/resources/features/CuraDemo/",
 glue = "nvr.sat.stepDefinitions",
	       monochrome = true,
	       tags = "@FunctionalTest and not @Local",
	       plugin= {"pretty", "json:target/cucumber.json", "junit:target/cucumber.xml", "html:target/Cucumber.html", "de.monochromata.cucumber.report.PrettyReports:target/pretty-cucumber"}
 		   //plugin= {"pretty", "json:target/cucumber.json", "junit:target/cucumber.xml", "html:target/Cucumber.html", "de.monochromata.cucumber.report.PrettyReports:target/pretty-cucumber", "nvr.sat.testutils.TestRailListener"}
 )
public class CuraDemoRunner {

@AfterClass
    public static void teardown() {
    	/*WebDriverFactory Factory = new WebDriverFactory();
        if (Factory.getWebDriver() != null) {
        	Factory.closeWebDriver();
        }*/
		System.out.print("In @AfterClass of CuraDemoRunner");
    }
}