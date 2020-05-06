package TestNG;

import Employee.GETEmployee;
import Employee.PropertiesClass;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class IncludeExcludeEmployee {
    private static Logger log = Logger.getLogger(String.valueOf(GETEmployee.class));
    protected static Properties prop = null;
    static {
        try {
            prop = PropertiesClass.loader_properties();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @Test
    public void retriveEmployee() {

        RestAssured.baseURI = prop.getProperty("URI");

        Response response = null;

        try {
            response = RestAssured.given()
                    .when()
                    .get("/employees");
        } catch (Exception e) {
            e.printStackTrace();
        }
        int statusCode = response.getStatusCode();
        Headers allHeaders = response.headers();
        String statusLine = response.getStatusLine();
        ResponseBody body = response.getBody();
        String bodyAsString = body.asString();

        assertEquals(statusLine /*actual value*/, "HTTP/1.1 200 OK" /*expected value*/, "Correct status code returned");
        System.out.println("Status Line :" + response.getStatusLine());
        log.info("Verified Status line");

        assertEquals(statusCode /*actual value*/, 200 /*expected value*/, "Correct status code returned");
        System.out.println("Status Code :" + response.getStatusCode());
        log.info("Verified Status code");

        for (Header header : allHeaders) {
            System.out.println("Key: " + header.getName() + " Value: " + header.getValue());
        }

        String contentType = response.header("Content-Type");
        assertEquals(contentType /* actual value */, "application/json;charset=utf-8" /* expected value */);
        log.info("Verified Content-Type in Header");

        String serverType = response.header("Server");
        assertEquals(serverType /* actual value */, "nginx/1.16.0" /* expected value */);
        log.info("Verified serverType in Header");

        JsonPath jsonPathEvaluator = response.jsonPath();

        assertTrue(bodyAsString.toLowerCase().contains("status"), "Response body contains status");
        assertTrue(bodyAsString.contains("success"), "Response body contains success");
        log.info("status received from Response " + jsonPathEvaluator.get("status"));

    }
    @Test
    public void  createEmployee() {

        RestAssured.baseURI = prop.getProperty("URI");

        Response response = null;
        JSONObject employee = new JSONObject();
        employee.put("name", "Akhil Nizalapuram");
        employee.put("salary", "25000");
        employee.put("age", "25");

        try {
            response = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(employee.toString())
                    .post("/create");
        } catch (Exception e) {
            e.printStackTrace();
        }
        int statusCode = response.getStatusCode();
        Headers allHeaders = response.headers();
        String statusLine = response.getStatusLine();

        assertEquals(statusLine /*actual value*/, "HTTP/1.1 200 OK" /*expected value*/, "Correct status code returned");
        System.out.println("Status Line :" + response.getStatusLine());
        log.info("Verified Status line");

        assertEquals(statusCode /*actual value*/, 200 /*expected value*/, "Correct status code returned");
        System.out.println("Status Code :" + response.getStatusCode());
        log.info("Verified Status code");

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

    }
    @Test
    public void updateEmployee() {

        RestAssured.baseURI = prop.getProperty("URI");
        Response response = null;
        JSONObject employee = new JSONObject();
        employee.put("name", "Sanjay");
        employee.put("salary", "26000");
        employee.put("age", "24");

        try {
            response = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(employee.toString())
                    .put("/update/21");
        } catch (Exception e) {
            e.printStackTrace();
        }
        int statusCode = response.getStatusCode();
        Headers allHeaders = response.headers();
        String statusLine = response.getStatusLine();
        ResponseBody body = response.getBody();
        String bodyAsString = body.asString();

        assertEquals(statusLine /*actual value*/, "HTTP/1.1 200 OK" /*expected value*/, "Correct status code returned");
        System.out.println("Status Line :" + response.getStatusLine());
        log.info("Verified status line");

        assertEquals(statusCode /*actual value*/, 200 /*expected value*/, "Correct status code returned");
        System.out.println("Status Code :" + response.getStatusCode());
        log.info("Verified status code");

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

    }
    @Test
    public void deleteEmployee() {

        RestAssured.baseURI = prop.getProperty("URI");

        Response response = null;

        try {
            response = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .delete("/delete/2");
        } catch (Exception e) {
            e.printStackTrace();
        }
        int statusCode = response.getStatusCode();
        Headers allHeaders = response.headers();
        String statusLine = response.getStatusLine();
        ResponseBody body = response.getBody();
        String bodyAsString = body.asString();

        assertEquals(statusLine /*actual value*/, "HTTP/1.1 200 OK" /*expected value*/, "Correct status code returned");
        System.out.println("Status Line :" + response.getStatusLine());
        log.info("Verified status line");

        assertEquals(statusCode /*actual value*/, 200 /*expected value*/, "Correct status code returned");
        System.out.println("Status Code :" + response.getStatusCode());
        log.info("Verified status code");

        for (Header header : allHeaders) {
            System.out.println("Key: " + header.getName() + " Value: " + header.getValue());
        }

        String contentType = response.header("Content-Type");
        assertEquals(contentType /* actual value */, "application/json;charset=utf-8" /* expected value */);
        log.info("Verified Content-Type in Header");

        String serverType = response.header("Server");
        assertEquals(serverType /* actual value */, "nginx/1.16.0" /* expected value */);
        log.info("Verified serverType in Header");

    }
}


