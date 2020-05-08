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
    @BeforeMethod(description = "Initializes URI",enabled = true)
    public void setUp(){
        RestAssured.baseURI = prop.getProperty("URI");
    }

    @Test(description = "This method is used for validation of retriving weather details ",enabled = false,groups= {"Exclude Group"})
    public void validateGetEmployee() {
        GETEmployee.retriveemployee(1);

    }

    @Test(description = "This method is used for validation of creating employee details ",priority=1,enabled = true,groups= {"Include Group"})
    public void validatecreateEmployee() {
        POSTEmployee.createemployee(1);
    }

    @Test(description = "This method is used for validation of updating employee details ",priority=2,enabled = true,groups= {"Include Group"})
    public void validateupdateteEmployee() {
        PUTEmployee.updateEmployee(1);
    }

    @Test(description = "This method is used for validation of deleting employee details ",priority=3,enabled = true,groups= {"Include Group"})
    public void validateDeleteEmployee() {
        DeleteEmployee.deleteEmployee(1);

    }
}