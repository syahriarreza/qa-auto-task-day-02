package scenario.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

public class ObjectRequest {

    @JsonProperty("name")
    private String name;

    @JsonProperty("data")
    private Map<String, Object> data;

    public ObjectRequest() {
    }

    public ObjectRequest(String name, Map<String, Object> data) {
        this.name = name;
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}