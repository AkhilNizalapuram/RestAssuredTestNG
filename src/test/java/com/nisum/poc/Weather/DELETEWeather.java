package com.nisum.poc.Weather;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ResponseBody;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class DELETEWeather {
    private static Logger log = Logger.getLogger(String.valueOf(DELETEWeather.class));
    protected static Properties prop = null;
    static {
        try {
            prop = PropertyClass.loader_properties();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void  updateWeather() throws IOException {
        RestAssured.baseURI = prop.getProperty("URI");

        Response response = null;

        try {
            response = RestAssured.given()
                    .when().queryParam("appid", prop.getProperty("APP_ID"))
                    .contentType(ContentType.JSON)
                    .delete("/stations");
        } catch (Exception e) {
            e.printStackTrace();
        }
        int statusCode = response.getStatusCode();
        Headers allHeaders = response.headers();
        String statusLine = response.getStatusLine();
        ResponseBody body = response.getBody();
        String bodyAsString = body.asString();

        assertEquals(statusLine /*actual value*/, "HTTP/1.1 404 Not Found" /*expected value*/, "Correct status code returned");
        System.out.println("Status Line :" + response.getStatusLine());

        assertEquals(statusCode /*actual value*/, 404 /*expected value*/, "Correct status code returned");
        System.out.println("Status Code :" + response.getStatusCode());

        for (Header header : allHeaders) {
            System.out.println("Key: " + header.getName() + " Value: " + header.getValue());
        }

        String contentType = response.header("Content-Type");
        assertEquals(contentType /* actual value */, "application/json; charset=utf-8" /* expected value */);
        log.info("Verified Content-Type in Header");

        String serverType = response.header("Server");
        assertEquals(serverType /* actual value */, "openresty" /* expected value */);
        log.info("Verified serverType in Header");

        JsonPath jsonPathEvaluator = response.jsonPath();

        assertTrue(bodyAsString.toLowerCase().contains("code"), "Response body contains code");
        assertTrue(bodyAsString.contains("404000"), "Response body contains 404000");
        log.info("code received from Response " + jsonPathEvaluator.get("code"));

        assertTrue(bodyAsString.toLowerCase().contains("message"), "Response body contains message");
        assertTrue(bodyAsString.contains("Internal error"), "Response body contains Internal error");
        log.info("message received from Response " + jsonPathEvaluator.get("message"));
    }
}