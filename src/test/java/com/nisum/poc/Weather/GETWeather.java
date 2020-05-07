package com.nisum.poc.Weather;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ResponseBody;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class GETWeather {

    private static Logger log = Logger.getLogger(String.valueOf(GETWeather.class));
    protected static Properties prop = null;
    static {
        try {
            prop = PropertyClass.loader_properties();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @Test
    public void  GetWeather() {
        RestAssured.baseURI = prop.getProperty("GETURI");

        Response response = null;
        try {
            response = RestAssured.given()
                    .when().queryParam("appid", prop.getProperty("APP_ID"))
                    .get("/stations/816a");
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

        assertEquals(statusCode /*actual value*/, 200 /*expected value*/, "Correct status code returned");
        System.out.println("Status Code :" + response.getStatusCode());

        for (Header header : allHeaders) {
            System.out.println("Key: " + header.getName() + " Value: " + header.getValue());
        }

        String contentType = response.header("Content-Type");
        assertEquals(contentType /* actual value */, "application/json; charset=utf-8" /* expected value */);
        log.info("Verified Content-Type in Header");

        String serverType =  response.header("Server");
        assertEquals(serverType /* actual value */, "openresty/1.9.7.1" /* expected value */);
        log.info("Verified serverType in Header");

        JsonPath jsonPathEvaluator = response.jsonPath();

        assertTrue(bodyAsString.toLowerCase().contains("external_id"), "Response body contains external_id");
        assertTrue(bodyAsString.contains("SF_TEST001"), "Response body contains SF_TEST001");
        log.info("external_id received from Response " + jsonPathEvaluator.get("external_id"));

        assertTrue(bodyAsString.toLowerCase().contains("name"), "Response body contains name");
        assertTrue(bodyAsString.contains("San Francisco Test Station"), "Response body contains San Francisco Test Station");
        log.info("name received from Response " + jsonPathEvaluator.get("name"));

        assertTrue(bodyAsString.toLowerCase().contains("longitude"), "Response body contains longitude");
        assertTrue(bodyAsString.contains("-122.43"), "Response body contains -122.43");
        log.info("longitude received from Response " + jsonPathEvaluator.get("longitude"));

        assertTrue(bodyAsString.toLowerCase().contains("latitude"), "Response body contains latitude");
        assertTrue(bodyAsString.contains("37.76"), "Response body contains 37.76");
        log.info("latitude received from Response " + jsonPathEvaluator.get("latitude"));

        assertTrue(bodyAsString.toLowerCase().contains("altitude"), "Response body contains altitude");
        assertTrue(bodyAsString.contains("150"), "Response body contains 150");
        log.info("altitude received from Response " + jsonPathEvaluator.get("altitude"));

    }
}

