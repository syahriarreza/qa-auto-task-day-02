package scenario.tests;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import scenario.base.BaseTest;
import scenario.dto.ObjectRequest;
import scenario.dto.ObjectResponse;
import scenario.dto.LoginRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.*;

public class ObjectFlowE2ETest extends BaseTest {

    ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testAddUpdateDeleteObjectFlow() throws Exception {
        String objectName = "Laptop_" + UUID.randomUUID().toString().substring(0, 5);
        login("albertsimanjuntak12@gmail.com", "@dmin123");
        System.out.println("Token: " + token);

        Map<String, Object> data = new HashMap<>();
        data.put("year", 2025);
        data.put("price", 1999.99);
        data.put("cpu_model", "Intel Core i7");
        data.put("hard_disk_size", "1 TB");
        data.put("capacity", "2 cpu");
        data.put("screen_size", "15 Inch");
        data.put("color", "black");

        ObjectRequest objectRequest = new ObjectRequest(objectName, data);
        String json = mapper.writeValueAsString(objectRequest);

        Response createResp = given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .body(json)
                .when()
                .post("/webhook/api/objects");

        System.out.println("Create Response: " + createResp.asString());
        assertEquals(createResp.statusCode(), 200, "Failed to create object");

        // Parse the response as a list of ObjectResponse
        List<ObjectResponse> createdObjects = mapper.readValue(createResp.asString(),
                mapper.getTypeFactory().constructCollectionType(List.class, ObjectResponse.class));

        // Assuming you want to work with the first object in the list
        ObjectResponse createdObj = createdObjects.get(0);
        assertEquals(createdObj.getName(), objectName);
        assertNotNull(createdObj.getId());

        String objectID = createdObj.getId();

        // Step 2: Update Object
        createdObj.getData().put("price", 1799.99); // change price
        createdObj.getData().put("color", "silver"); // change color
        String updateJson = mapper.writeValueAsString(createdObj);

        Response updateResp = given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .body(updateJson)
                .when()
                .put("/webhook/37777abe-a5ef-4570-a383-c99b5f5f7906/api/objects/" + objectID);

        System.out.println("Update Response: " + updateResp.asString());
        assertEquals(updateResp.statusCode(), 200, "Failed to update object");

        // Step 3: Delete Object
        Response deleteResp = given()
                .header("Authorization", "Bearer " + token)
                .when()
                .delete("/webhook/d79a30ed-1066-48b6-83f5-556120afc46f/api/objects/" + objectID);

        System.out.println("Delete Response: " + deleteResp.asString());
        assertEquals(deleteResp.statusCode(), 200, "Failed to delete object");

        System.out.println("Object E2E flow succeeded: " + objectID);
    }
}