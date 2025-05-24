package scenario.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import scenario.base.BaseTest;
import scenario.dto.LoginResponse;
import scenario.dto.RegisterResponse;
import scenario.dto.UserRequest;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.*;

public class RegisterAndLoginE2ETest extends BaseTest {

    ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testRegisterGetLoginFlow() throws Exception {
        // Step 1: Register
        String email = "baba_" + UUID.randomUUID().toString().substring(0, 6) + "@mail.com";
        String password = "@dmin123";

        UserRequest request = new UserRequest(email, "Baba QA", password, "Technology", "08123456789");
        String jsonBody = mapper.writeValueAsString(request);

        Response registerResponse = given()
                .header("Content-Type", "application/json")
                .body(jsonBody)
                .when()
                .post("/webhook/api/register");

        System.out.println("Register Response: " + registerResponse.asString());
        assertEquals(registerResponse.statusCode(), 200, "Register failed");

        RegisterResponse registerObj = mapper.readValue(registerResponse.asString(), RegisterResponse.class);

        assertEquals(registerObj.getEmail(), email, "Email mismatch");
        assertEquals(registerObj.getFullName(), "Baba QA", "Full name mismatch");
        assertEquals(registerObj.getDepartment(), "Technology", "Department mismatch");
        assertEquals(registerObj.getPhoneNumber(), "08123456789");
        assertNotNull(registerObj.getId());
        assertNotNull(registerObj.getCreateAt());

        // Step 2: Login
        String loginJson = String.format("{\"email\": \"%s\", \"password\": \"%s\"}", email, password);

        Response loginResponse = given()
                .header("Content-Type", "application/json")
                .body(loginJson)
                .when()
                .post("/webhook/api/login");

        assertEquals(loginResponse.statusCode(), 200, "Login failed");

        LoginResponse loginObj = mapper.readValue(loginResponse.asString(), LoginResponse.class);
        assertNotNull(loginObj.getToken(), "Token should not be null");

        System.out.println("E2E Passed. Token: " + loginObj.getToken());
    }
}