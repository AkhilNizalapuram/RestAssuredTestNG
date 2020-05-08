package com.nisum.poc.TestNG;

import com.nisum.poc.Weather.*;
import io.restassured.RestAssured;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

public class WeatherTest {
    private static Logger log = Logger.getLogger(String.valueOf(EmployeeTest.class));
    protected static Properties prop = null;
    static {
        try {
            prop = PropertyClass.loader_properties();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @BeforeMethod(description = "Initializes URI",enabled = true)
    public void setUp(){
        RestAssured.baseURI = prop.getProperty("URI");
    }

    @Test(description = "This method is used for validation of retriving weather details ",enabled = false,groups= {"Exclude Groups"})
    public void validategetWeather() {
        GETWeather.getweather();
    }

    @Test(description = "This method is used for validation of creating weather details ",priority=1,enabled = true,groups= {"Include Groups"})
    public void validatecreateWeather() throws IOException {
        POSTWeather.createWeather();
    }

    @Test(description = "This method is used for validation of updating weather details ",priority=2,enabled = true,groups= {"Include Groups"})
    public void validateupdateteWeather() throws IOException {
        PUTWeather.updateWeather();
    }

    @Test(description = "This method is used for validation of deleting weather details ",priority=3,enabled = true,groups= {"Include Groups"})
    public void validateDeleteWeather() {
        DELETEWeather.deleteWeather();

    }

    @AfterMethod(description = "This method is used for validation of weather test class",enabled = true)
    public void complete(){
        log.info("Validated weather test class successfully");
    }
}
