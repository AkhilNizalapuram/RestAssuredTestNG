package com.nisum.poc.Employee;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.json.JSONObject;
import org.junit.Assert;
import java.util.logging.Logger;
import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertEquals;


public class POSTEmployee {

    private static Logger log = Logger.getLogger(String.valueOf(POSTEmployee.class));

    public static void createemployee(int empid) {

        JSONObject employee = new JSONObject();
        employee.put("name", "Akhil Nizalapuram");
        employee.put("salary", "25000");
        employee.put("age", "25");

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(employee.toString())
                .post("/create");
        Assert.assertEquals("Did not get response", 200, response.getStatusCode());


        int statusCode = response.getStatusCode();
        Headers allHeaders = response.headers();
        String statusLine = response.getStatusLine();
        ResponseBody body = response.getBody();
        String bodyAsString = body.asString();

        for (Header header : allHeaders) {
            System.out.println("Key: " + header.getName() + " Value: " + header.getValue());
        }

        String contentType = response.header("Content-Type");
        assertEquals(contentType /* actual value */, "application/json;charset=utf-8" /* expected value */);
        log.info("Verified Content-Type in Header");

        String serverType = response.header("Server");
        assertEquals(serverType /* actual value */, "nginx/1.16.0" /* expected value */);
        log.info("Verified serverType in Header");

        JSONObject JSONResponseBody = new JSONObject(response.asString());

        assertTrue(JSONResponseBody.getString("status").equals("success") );
        log.info("status verified from Response ");

        assertEquals(JSONResponseBody.getJSONObject("data").getString("name"), "Akhil Nizalapuram");
        log.info("name verified from Response ");

        assertEquals(JSONResponseBody.getJSONObject("data").getString("salary"), "25000");
        log.info("salary verified from Response ");

        assertEquals(JSONResponseBody.getJSONObject("data").getString("age"), "25");
        log.info("age verified from Response ");


        log.info("Created Employees record of Id: " + empid);

    }

}
