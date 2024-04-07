package requesters;

import io.cucumber.core.internal.com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import mappers.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.CustomException;
import utils.Deserialization;

import java.util.List;
import java.util.Map;

public class Requesters {

    private static final Logger logger = LoggerFactory.getLogger(Requesters.class);

    private static final String BASE_URL = "https://catfact.ninja/";
    Deserialization deserialization = new Deserialization();

    public List<Breed> getBreeds(Integer maximumNumber) throws JsonProcessingException {
        String endpoint = BASE_URL + "breeds?limit=" + maximumNumber.toString();
        Response response = getRequest(endpoint);
        response.then().statusCode(200);
        BreedResponse breedResponse = deserialization.deserializeJson(response.asString(), BreedResponse.class);
        return breedResponse.getData();

    }

    public FactResponse getFact(Integer maximumNumber) throws JsonProcessingException, CustomException {
        String endpoint = BASE_URL + "fact?max_length=" + maximumNumber.toString();
        Response response = getRequest(endpoint);
        response.then().statusCode(200);
        checkIfParamsPresent(response, "length", "fact");
        return deserialization.deserializeJson(response.asString(), FactResponse.class);
    }

    public List<Fact> getListOfRandomFacts(Integer maximumLength, Integer limit) throws JsonProcessingException, CustomException {
        String endpoint = BASE_URL + "facts?max_length=" + maximumLength.toString() + "&limit=" + limit.toString();
        Response response = getRequest(endpoint);
        response.then().statusCode(200);
        checkIfParamsPresentInList(response, "fact", "length");
        FactListResponse factListResponse = deserialization.deserializeJson(response.asString(), FactListResponse.class);
        return factListResponse.getData();
    }

    private Response getRequest(String endpoint) {
        return RestAssured.given()
                .log().all()
                .contentType("application/json")
                .when()
                .get(endpoint);
    }

    public void checkIfParamsPresent(Response response, String param1, String param2) throws CustomException {
        JsonPath jsonPath = response.jsonPath();
        if (jsonPath.get(param1) == null || jsonPath.get(param2) == null) {
            String errorMessage = "Error: The response does not contain both 'length' and 'fact' fields.";
            logger.error(errorMessage);
            throw new CustomException(errorMessage);
        }
    }

    public void checkIfParamsPresentInList(Response response, String param1, String param2) throws CustomException {
        JsonPath jsonPath = response.jsonPath();
        List<Map<String, Object>> dataList = jsonPath.getList("data");

        if (dataList.isEmpty()) {
            throw new CustomException("Error: The response data is empty.");
        }

        for (Map<String, Object> dataItem : dataList) {
            if (!dataItem.containsKey(param1) || !dataItem.containsKey(param2)) {
                String errorMessage = "Error: One of the data items does not contain both '" + param1 + "' and '" + param2 + "' fields.";
                logger.error(errorMessage);
                throw new CustomException(errorMessage);
            }
        }
    }
}

