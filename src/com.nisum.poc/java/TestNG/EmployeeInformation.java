package TestNG;

import Employee.*;
import org.testng.annotations.Test;

public class EmployeeInformation extends GETEmployee {

        @Test
        public void validateGetEmployee() {
            String response = retriveEmployee();
        }
    }
