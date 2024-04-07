package stepDefinitions;

import io.cucumber.core.internal.com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import mappers.Breed;
import mappers.Fact;
import mappers.FactResponse;
import org.junit.jupiter.api.Assertions;
import requesters.Requesters;
import utils.CustomException;

import java.util.List;


public class CatFactStepDefinitions {
    Requesters requesters = new Requesters();
    String fact;
    int lengthOfFact;
    int breedCount;
    int factCount;
    List<Fact> responseList;

    @When("request with maximum {int} length of the fact is made")
    public void get_random_fact(Integer length) throws CustomException, JsonProcessingException {
        FactResponse response = requesters.getFact(length);
        fact = response.getFact();
        lengthOfFact = response.getLength();
    }

    @Then("length provided by response matches actual length")
    public void check_length() {
        Assertions.assertEquals(fact.length(), lengthOfFact, "Actual length of the fact does not match one provided by response");
    }

    @Then("length of the fact provided by response does not exceed maximum length of {int}")
    public void check_if_fact_exceeds_limit(Integer length) {
        if (length < lengthOfFact) {
            System.out.println("Fact length exceeds maximum length set");
        }
    }

    @When("request with maximum of {int} breeds displayed is made")
    public void get_cat_breeds(Integer length) throws JsonProcessingException {
        List<Breed> response = requesters.getBreeds(length);
        breedCount = response.size();
    }

    @Then("number of breeds in the response match the expected {int}")
    public void check_if_received_cat_breed_count_matches_expected(Integer length) {
        Assertions.assertEquals(length, breedCount, "Breed count received does not match the expected");
    }

    @When("request for list of random facts with limit {int} and maximum length {int} is made")
    public void get_list_of_random_facts(Integer maxLength, Integer limit) throws CustomException, JsonProcessingException {
        responseList = requesters.getListOfRandomFacts(maxLength, limit);
        factCount = responseList.size();
    }

    @Then("length provided for each received fact matches actual length of the fact")
    public void check_each_fact_length() {
        responseList.forEach(responseList -> {
            Assertions.assertEquals(responseList.getFact().length(), responseList.getLength(), "Not all facts actual lengths match the ones provided from response");
        });
    }

    @Then("number of facts received matches the requested amount of {int} and their length does not exceed limit {int}")
    public void check_if_length_and_count_match_the_expected(Integer limit, Integer maxLength) {
        responseList.forEach(responseList -> {
            if (maxLength < responseList.getLength()) {
                System.out.println("Fact length exceeds maximum length set");
            }
            else {Assertions.assertEquals(limit, factCount,"Fact count received does not match the expected");}
        });
    }
}
