package cucumber;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

//Cucumber->TestNG or Junit
@CucumberOptions(features="src/test/java/cucumber",glue="tthoughtfocus.stepDefinations",
monochrome=true,tags="@ErrorValidationTest",plugin={"html:target/cucumber.html"})
public class TestNGTestRunner extends AbstractTestNGCucumberTests
{
	
}
