package com.nisum.poc.Weather;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import static org.testng.Assert.assertTrue;

public class POSTWeather {

    private static Logger log = Logger.getLogger(String.valueOf(POSTWeather.class));

    public static void createWeather() throws IOException {
        File file = new File("src/test/resources/jsonfile/create.json");
        Response response = RestAssured.given()
                .when().queryParam("appid", "b8896959f2c926765dfbaefb02c3c260")
                .contentType(ContentType.JSON)
                .body(FileUtils.readFileToString(file,"utf-8"))
                .post("/stations");
        org.junit.Assert.assertEquals("Did not get response", 201, response.getStatusCode());
        log.info("Verified status code");

        int statusCode = response.getStatusCode();
        Headers allHeaders = response.headers();
        String statusLine = response.getStatusLine();
        ResponseBody body = response.getBody();
        String bodyAsString = body.asString();

        Assert.assertEquals(statusLine /*actual value*/, "HTTP/1.1 201 Created" /*expected value*/, "Correct status code returned");
        System.out.println("Status Line :" + response.getStatusLine());

        Assert.assertEquals(statusCode /*actual value*/, 201 /*expected value*/, "Correct status code returned");
        System.out.println("Status Code :" + response.getStatusCode());

        for (Header header : allHeaders) {
            System.out.println("Key: " + header.getName() + " Value: " + header.getValue());
        }

        String contentType = response.header("Content-Type");
        Assert.assertEquals(contentType /* actual value */, "application/json; charset=utf-8" /* expected value */);
        log.info("Verified Content-Type in Header");

        String serverType =  response.header("Server");
        Assert.assertEquals(serverType /* actual value */, "openresty" /* expected value */);
        log.info("Verified serverType in Header");

        System.out.println("Response Body is: " + body.asString());
        assertTrue(bodyAsString.toLowerCase().contains("hyderabad"), "Response body contains Hyderabad");
        log.info("Verified hyderabad in body");

        JSONObject JSONResponseBody = new JSONObject(response.asString());

        assertTrue(bodyAsString.contains("external_id"),"external_id received from Response ");
        assertTrue(JSONResponseBody.get("external_id").equals("HYD_TEST001"));
        log.info("external_id received from Response ");

        String name = (String) JSONResponseBody.get("name");
        System.out.println("name received from Response " + name);
        Assert.assertEquals(name, "Hyderabad Test Station", "Correct name received in the Response");
        log.info("name received from Response ");

        assertTrue(bodyAsString.contains("latitude"),"latitude received from Response " + JSONResponseBody.get("latitude").equals("17.38"));
        log.info("latitude received from Response ");

        assertTrue(bodyAsString.contains("longitude"),"longitude received from Response " + JSONResponseBody.get("longitude").equals("78.47"));
        log.info("longitude received from Response ");

        assertTrue(bodyAsString.contains("altitude"),"altitude received from Response " + JSONResponseBody.get("altitude").equals("542"));
        log.info("altitude received from Response ");

    }
}




