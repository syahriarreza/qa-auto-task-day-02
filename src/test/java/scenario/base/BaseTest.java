package scenario.base;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.path.json.JsonPath;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import scenario.dto.LoginRequest;

import static io.restassured.RestAssured.given;

public class BaseTest {

    protected String token;

    @BeforeClass
    public void authenticate() {
        RestAssured.baseURI = "https://whitesmokehouse.com";
    }

    public void login(String username, String password) {
        LoginRequest loginRequest = new LoginRequest(username, password);
        Response response = given()
                .header("Content-Type", "application/json")
                .body(loginRequest)
                .when()
                .post("/webhook/api/login");

        System.out.println("Response Login: " + response.asString());
        JsonPath jsonPath = response.jsonPath();
        token = jsonPath.get("token");
    }

}