package restassured.tests;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.response.Response;
import restassured.base.BaseTest;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

public class ObjectTests extends BaseTest {
    String objectID;

    @BeforeClass
    public void testAddObject() {
        String body = "{\n" +
                "  \"name\": \"Apple MacBook Pro 16\",\n" +
                "  \"data\": {\n" +
                "    \"year\": 2019,\n" +
                "    \"price\": 1849.99,\n" +
                "    \"cpu_model\": \"Intel Core i9\",\n" +
                "    \"hard_disk_size\": \"1 TB\",\n" +
                "    \"capacity\": \"2 cpu\",\n" +
                "    \"screen_size\": \"14 Inch\",\n" +
                "    \"color\": \"red\"\n" +
                "  }\n" +
                "}";

        System.out.println("@@@@@@@@@@@@@@ token: " + token);
        Response response = given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .body(body)
                .when()
                .post("/webhook/api/objects");

        if (response.statusCode() != 200) {
            throw new RuntimeException("Failed to add object: " + response.asString());
        }

        assertEquals(response.statusCode(), 200);

        System.out.println("Response testAddObject: " + response.getBody().asString());

        objectID = response.jsonPath().getString("id");
        System.out.println("@@@@@@@@@@@@@@ objectID: " + objectID);
    }

    @Test
    public void testGetListAllObjects() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/webhook/api/objects");

        assertEquals(response.statusCode(), 200);
        Object responseBody = response.jsonPath().get();
        assertTrue(responseBody instanceof java.util.List, "Response should be an array");
    }

    @Test
    public void testSingleObject() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/webhook/8749129e-f5f7-4ae6-9b03-93be7252443c/api/objects/" + objectID);

        assertEquals(response.statusCode(), 200);

        System.out.println("Response testSingleObject: " + response.getBody().asString());

        String id = response.jsonPath().getString("id");
        assertNotNull(id, "Response should contain an id field");
    }

    @Test
    public void testListOfObjectsByIds() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/webhook/api/objects?id=3");

        assertEquals(response.statusCode(), 200);
        Object responseBody = response.jsonPath().get();
        assertTrue(responseBody instanceof java.util.List, "Response should be an array");
    }

    @Test
    public void testUpdateObject() {
        String body = "{\n" +
                "  \"name\": \"Apple MacBook Pro 16\",\n" +
                "  \"data\": {\n" +
                "    \"year\": 2019,\n" +
                "    \"price\": 1849.99,\n" +
                "    \"cpu_model\": \"Intel Core i9\",\n" +
                "    \"hard_disk_size\": \"1 TB\",\n" +
                "    \"capacity\": \"2 cpu\",\n" +
                "    \"screen_size\": \"14 Inch\",\n" +
                "    \"color\": \"red\"\n" +
                "  }\n" +
                "}";

        Response response = given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .body(body)
                .when()
                .put("/webhook/37777abe-a5ef-4570-a383-c99b5f5f7906/api/objects/" + objectID);

        assertEquals(response.statusCode(), 200);
        String id = response.jsonPath().getString("id");
        assertNotNull(id, "Response should contain an id field");
    }

    @Test
    public void testPartiallyUpdateObject() {
        String body = "{\n" +
                "  \"name\": \"Apple MacBook Pro 1611-albert12\",\n" +
                "  \"year\": \"2030\"\n" +
                "}";

        Response response = given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .body(body)
                .when()
                .patch("/webhook/39a0f904-b0f2-4428-80a3-391cea5d7d04/api/object/" + objectID);

        assertEquals(response.statusCode(), 200);

        System.out.println("Response testPartiallyUpdateObject: " + response.getBody().asString());

        String id = response.jsonPath().getString("id");
        assertNotNull(id, "Response should contain an id field");
    }

    @AfterClass
    public void testDeleteObject() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .when()
                .delete("/webhook/d79a30ed-1066-48b6-83f5-556120afc46f/api/objects/" + objectID);
        assertEquals(response.statusCode(), 200);

        System.out.println("Response testDeleteObject: " + response.getBody().asString());
        String status = response.jsonPath().getString("status");
        assertEquals(status, "deleted", "Status should be 'deleted'");
    }
}
