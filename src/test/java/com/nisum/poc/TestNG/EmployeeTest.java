package com.nisum.poc.TestNG;

import com.nisum.poc.Employee.*;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

public class EmployeeTest {

    private static Logger log = Logger.getLogger(String.valueOf(EmployeeTest.class));
    protected static Properties prop = null;
    static {
        try {
            prop = ReadProperties.loader_properties();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @BeforeMethod
    public void setUp(){
        RestAssured.baseURI = prop.getProperty("URI");
    }

    @Test(priority=0)
    public void validateGetEmployee() {
        GETEmployee.retriveemployee(1);
    }

    @Test(priority=1)
    public void validatecreateEmployee() {
        POSTEmployee.createemployee(1);
    }

    @Test(priority=2)
    public void validateupdateteEmployee() {
        PUTEmployee.updateEmployee(1);
    }
    @Test(priority=3)
    public void validateDeleteEmployee() {
        DeleteEmployee.deleteEmployee(1);

    }
}