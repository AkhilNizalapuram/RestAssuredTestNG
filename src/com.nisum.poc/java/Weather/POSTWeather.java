package Weather;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.apache.commons.io.FileUtils;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;
import static org.testng.Assert.assertTrue;

public class POSTWeather {

    private static Logger log = Logger.getLogger(String.valueOf(POSTWeather.class));
    protected static Properties prop = null;
    static {
        try {
            prop = PropertyClass.loader_properties();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @Test
    public void  createWeather() {
        RestAssured.baseURI = prop.getProperty("URI");
        File file = new File("src/com.nisum.poc/resources/jsonfile/create.json");
        Response response = null;
        try {
            response = RestAssured.given()
                    .when().queryParam("appid", prop.getProperty("APP_ID"))
                    .contentType(ContentType.JSON)
                    .body(FileUtils.readFileToString(file,"utf-8"))
                    .post("/stations");
        } catch (Exception e) {
            e.printStackTrace();
        }
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

        JsonPath jsonPathEvaluator = response.jsonPath();

        assertTrue(bodyAsString.contains("external_id"),"external_id received from Response ");
        assertTrue(jsonPathEvaluator.get("external_id").equals("HYD_TEST001"));
        log.info("external_id received from Response ");

        String name = jsonPathEvaluator.get("name");
        System.out.println("name received from Response " + name);
        Assert.assertEquals(name, "Hyderabad Test Station", "Correct name received in the Response");
        log.info("name received from Response ");

        assertTrue(bodyAsString.contains("latitude"),"latitude received from Response " + jsonPathEvaluator.get("latitude").equals("17.38"));
        log.info("latitude received from Response ");

        assertTrue(bodyAsString.contains("longitude"),"longitude received from Response " + jsonPathEvaluator.get("longitude").equals("78.47"));
        log.info("longitude received from Response ");

        assertTrue(bodyAsString.contains("altitude"),"altitude received from Response " + jsonPathEvaluator.get("altitude").equals("542"));
        log.info("altitude received from Response ");

    }
}




