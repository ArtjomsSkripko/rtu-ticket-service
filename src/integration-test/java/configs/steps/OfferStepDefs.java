package configs.steps;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.ImmutableMap;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.springframework.http.HttpStatus;
import ticket.rest.model.JourneyOfferDTO;
import ticket.rest.model.RegionalOfferDTO;

public class OfferStepDefs extends SpringIntegrationTest {

    private List<JourneyOfferDTO> journeyOfferDTOList = new ArrayList();
    private Response response = null;

    @Given("^(\\d+) journey offers? from (.+) to (.+)$")
    public void createOfferRequestWithDepAndDestCities(int numberOfOffers, String depCity, String destCity) {
        for (int i = 1; i <= numberOfOffers; i++) {
            JourneyOfferDTO journeyOfferDTO = new JourneyOfferDTO();
            journeyOfferDTO.setAddressFrom(new AddressDTOBuilder().withDefaults().city(depCity).build());
            journeyOfferDTO.setAddressTo(new AddressDTOBuilder().withDefaults().city(destCity).build());
            journeyOfferDTO.setDepartureTime(ZonedDateTime.now().plusHours(10));
            journeyOfferDTOList.add(journeyOfferDTO);
        }
    }

    @And("^add (.+) regional offer to (\\d+) journey wrapper$")
    public void addRegionalOfferToJourneyWrapper(String city, int journeyWrapperOrdinalNumber) {
        JourneyOfferDTO journeyOfferDTO = journeyOfferDTOList.get(journeyWrapperOrdinalNumber - 1);
        RegionalOfferDTO offerDTO = new RegionalOfferDTOBuilder().withDefaults()
                .city(city)
                .id(UUID.randomUUID().toString())
                .addressFrom(new AddressDTOBuilder().withDefaults().city(city).build())
                .addressTo(new AddressDTOBuilder().withDefaults().city(city).build())
                .build();
        if (journeyOfferDTO.getRegionalOffers() == null) {

            Map<String, RegionalOfferDTO> regionalOffers = new HashMap<>();
            regionalOffers.put(city, offerDTO);
            journeyOfferDTO.setRegionalOffers(regionalOffers);
        } else {
            journeyOfferDTO.getRegionalOffers().put(city, offerDTO);
        }
    }

    @And("^add intercity offer to (\\d+) journey wrapper$")
    public void addIntercityOffer(int journeyWrapperOrdinalNumber) {
        JourneyOfferDTO journeyOfferDTO = journeyOfferDTOList.get(journeyWrapperOrdinalNumber - 1);
        journeyOfferDTO.setIntercityOffer(new IntercityOfferDTOBuilder().withDefaults()
                .depCity(journeyOfferDTO.getAddressFrom().getCity())
                .destCity(journeyOfferDTO.getAddressTo().getCity())
                .id(UUID.randomUUID().toString())
                .build()
        );
    }

    @When("^call create tickets")
    public void callCreateTickets() {
        response = RestAssured.given()
                .when()
                .contentType("application/json")
                .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6InRlc3RVc2VyMSIsInVzZXJfaWQiOiIxMjM0MTI1IiwiY29udHJhY3RfbW9kZWwiOiJjb250cmFjdF8xIiwicm9sZSI6IlJFR1VMQVJfVVNFUiIsInRvdWNocG9pbnQiOiJXRUIifQ.vwqvyEYcuZdlmt65QfjwhhZeURNEigCWqfJvGS-kWZA")
                .body(journeyOfferDTOList)
                .port(8085)
                .post("v1/ticket/");
        response.then().log().all();
    }

    @Then("^response status is (.+)$")
    public void responseStatusIs(String responseStatus) {
        response.then().statusCode(HttpStatus.valueOf(responseStatus).value());
    }

    @And("^response has (\\d+) tickets?$")
    public void responseHasTicket(int ticketCount) {
        response.then().body("ticketId", Matchers.hasSize(ticketCount));
    }

    @And("^(\\d+) ticket has route from (.+) to (.+)$")
    public void ticketHasRoute(int ticketNumber, String depCity, String arrCity) {
        String pathForRequestedTicket = "[" + (ticketNumber - 1) + "]";
        response.then().body(pathForRequestedTicket + ".addressFrom.city", Matchers.equalTo(depCity));
        response.then().body(pathForRequestedTicket + ".addressTo.city", Matchers.equalTo(arrCity));
    }
}
