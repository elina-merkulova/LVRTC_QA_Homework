package requesters;

import io.cucumber.core.internal.com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import mappers.Breed;
import mappers.BreedResponse;
import mappers.FactResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.CustomException;

import java.util.List;

public class Requesters {

    private static final Logger logger = LoggerFactory.getLogger(Requesters.class);

    private static final String BASE_URL = "https://catfact.ninja/";

    private Response getRequest(String endpoint) {
        return RestAssured.given()
                .log().all()
                .contentType("application/json")
                .when()
                .get(endpoint);
    }


    public List<Breed> getBreeds(Integer maximumNumber) throws JsonProcessingException {
        String endpoint = BASE_URL + "breeds?limit=" + maximumNumber.toString();
        Response response = getRequest(endpoint);
        response.then().statusCode(200);
        ObjectMapper objectMapper = new ObjectMapper();
        BreedResponse breedResponse = objectMapper.readValue(response.asString(), BreedResponse.class);
        return breedResponse.getData();

    }

    public FactResponse getFact(Integer maximumNumber) throws JsonProcessingException, CustomException {
        String endpoint = BASE_URL + "fact?max_length=" + maximumNumber.toString();
        Response response = getRequest(endpoint);
        response.then().statusCode(200);

        JsonPath jsonPath = response.jsonPath();

        if (jsonPath.get("length") != null && jsonPath.get("fact") != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(response.asString(), FactResponse.class);
        } else {
            String errorMessage = "Error: The response does not contain both 'length' and 'fact' fields.";
            logger.error(errorMessage);
            throw new CustomException(errorMessage);

        }
    }
}

