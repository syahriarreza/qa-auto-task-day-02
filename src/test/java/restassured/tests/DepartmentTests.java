package restassured.tests;

import org.testng.annotations.Test;

import io.restassured.response.Response;
import restassured.base.BaseTest;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class DepartmentTests extends BaseTest {

    @Test
    public void testGetAllDepartment() {
        Response response = given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/webhook/api/department");

        assertEquals(response.statusCode(), 200);
        Object responseBody = response.jsonPath().get();
        assertTrue(responseBody instanceof java.util.List, "Response should be an array");
    }

}
