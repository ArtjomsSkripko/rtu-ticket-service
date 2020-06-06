package jbehave;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import configs.steps.AddressDTOBuilder;
import configs.steps.IntercityOfferDTOBuilder;
import configs.steps.RegionalOfferDTOBuilder;
import configs.steps.SpringIntegrationTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.springframework.http.HttpStatus;
import ticket.rest.model.JourneyOfferDTO;
import ticket.rest.model.RegionalOfferDTO;

public class JBehaveSteps extends SpringIntegrationTest {

    private List<JourneyOfferDTO> journeyOfferDTOList = new ArrayList();
    private Response response = null;

    @Given("$numberOfOffers journey offer(s) from $depCity to $destCity")
    public void createOfferRequestWithDepAndDestCities(int numberOfOffers, String depCity, String destCity) {
        for (int i = 1; i <= numberOfOffers; i++) {
            JourneyOfferDTO journeyOfferDTO = new JourneyOfferDTO();
            journeyOfferDTO.setAddressFrom(new AddressDTOBuilder().withDefaults().city(depCity).build());
            journeyOfferDTO.setAddressTo(new AddressDTOBuilder().withDefaults().city(destCity).build());
            journeyOfferDTO.setDepartureTime(ZonedDateTime.now().plusHours(10));
            journeyOfferDTOList.add(journeyOfferDTO);
        }
    }

    @Given("add $city regional offer to $journeyWrapperOrdinalNumber journey wrapper")
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

    @Given("add intercity offer to $journeyWrapperOrdinalNumber journey wrapper")
    public void addIntercityOffer(int journeyWrapperOrdinalNumber) {
        JourneyOfferDTO journeyOfferDTO = journeyOfferDTOList.get(journeyWrapperOrdinalNumber - 1);
        journeyOfferDTO.setIntercityOffer(new IntercityOfferDTOBuilder().withDefaults()
                .depCity(journeyOfferDTO.getAddressFrom().getCity())
                .destCity(journeyOfferDTO.getAddressTo().getCity())
                .id(UUID.randomUUID().toString())
                .build()
        );
    }

    @When("call create tickets")
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

    @Then("response status is $responseStatus")
    public void responseStatusIs(String responseStatus) {
        response.then().statusCode(HttpStatus.valueOf(responseStatus).value());
    }

    @Then("response has $ticketCount ticket(s)")
    public void responseHasTicket(int ticketCount) {
        response.then().body("ticketId", Matchers.hasSize(ticketCount));
    }

    @Then("$ticketNumber ticket has route from $depCity to $destCity")
    public void ticketHasRoute(int ticketNumber, String depCity, String arrCity) {
        String pathForRequestedTicket = "[" + (ticketNumber - 1) + "]";
        response.then().body(pathForRequestedTicket + ".addressFrom.city", Matchers.equalTo(depCity));
        response.then().body(pathForRequestedTicket + ".addressTo.city", Matchers.equalTo(arrCity));
    }
}
