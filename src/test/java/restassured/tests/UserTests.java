package restassured.tests;

import org.testng.annotations.Test;

import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import restassured.base.BaseTest;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class UserTests extends BaseTest {

    @Test
    public void testRegister() {
        String body = "{\n" +
                "  \"email\": \"albertsimanjuntak12@gmail.com\",\n" +
                "  \"full_name\": \"Albert Simanjuntak\",\n" +
                "  \"password\": \"@dmin123\",\n" +
                "  \"department\": \"Technology\",\n" +
                "  \"phone_number\": \"082264189134\"\n" +
                "}";

        Response response = given()
                .header("Content-Type", "application/json")
                .body(body)
                .when()
                .post("/webhook/api/register");

        assertEquals(response.statusCode(), 200);

        // Print response for debugging
        System.out.println("Register response: " + response.asString());

        // Check if the email is already registered
        if (response.jsonPath().getString("result").equals("failed") &&
                response.jsonPath().getString("message").contains("already registered")) {

            assertEquals(response.statusCode(), 200);
            assertEquals(response.jsonPath().getString("result"), "failed");
            assertTrue(response.jsonPath().getString("message").contains("already registered"));
        } else {
            // Original assertions for successful registration
            assertEquals(response.statusCode(), 200);
            assertEquals(response.jsonPath().get("data.email"), "albertsimanjuntak12@gmail.com");
        }
    }

}
