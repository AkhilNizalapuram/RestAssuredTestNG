import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;

@CucumberOptions(
        features = "Employee",
        glue = {"com.nisum.poc"},
        tags = {""},
        plugin = {"pretty", "html:target/cucumber_target.html", "json:target/cucumber.json"})
public class TestNG extends AbstractTestNGCucumberTests {

}


