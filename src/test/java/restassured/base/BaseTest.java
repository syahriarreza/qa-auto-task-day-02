package restassured.base;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.path.json.JsonPath;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import static io.restassured.RestAssured.given;

public class BaseTest {

    protected String token;

    @BeforeClass
    public void authenticate() {
        RestAssured.baseURI = "https://whitesmokehouse.com";

        String requestBody = "{\n" +
                "  \"email\": \"albertsimanjuntak12@gmail.com\",\n" +
                "  \"password\": \"@dmin123\"\n" +
                "}";

        Response response = given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post("/webhook/api/login")
                .then()
                .statusCode(200)
                .extract().response();

        System.out.println("@@@@@@@@@@@@@@ response: " + response.asString());
        JsonPath jsonPath = response.jsonPath();
        token = jsonPath.getString("token");
        System.out.println("@@@@@@@@@@@@@@ token: " + token);
    }
}