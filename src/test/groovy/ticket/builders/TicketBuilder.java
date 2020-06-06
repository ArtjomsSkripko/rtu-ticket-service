package ticket.builders;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;

import ticket.enumeration.OfferTypeEnum;
import ticket.enumeration.PassengerTypeEnum;
import ticket.enumeration.StatusEnum;
import ticket.enumeration.TransportTypeEnum;
import ticket.model.Address;
import ticket.model.Ticket;
import ticket.rest.model.AddressDTO;
import ticket.rest.model.IntercityOfferDTO;

public class TicketBuilder {

    private Ticket template;

    public TicketBuilder() {
        this.template = new Ticket();
    }

    public TicketBuilder ticketId(String ticketId) {
        template.setTicketId(ticketId);
        return this;
    }

    public TicketBuilder offerId(String offerId) {
        template.setOfferId(offerId);
        return this;
    }

    public TicketBuilder userId(String userId) {
        template.setUserId(userId);
        return this;
    }

    public TicketBuilder type(OfferTypeEnum type) {
        template.setType(type);
        return this;
    }

    public TicketBuilder status(StatusEnum status) {
        template.setStatus(status);
        return this;
    }

    public TicketBuilder numberOfLuggage(Integer numberOfLuggage) {
        template.setNumberOfLuggage(numberOfLuggage);
        return this;
    }

    public TicketBuilder passenger(PassengerTypeEnum passenger) {
        template.setPassenger(passenger);
        return this;
    }

    public TicketBuilder placeType(String placeType) {
        template.setPlaceType(placeType);
        return this;
    }

    public TicketBuilder priceWithDiscount(BigDecimal priceWithDiscount) {
        template.setPriceWithDiscount(priceWithDiscount);
        return this;
    }

    public TicketBuilder priceWithoutDiscount(BigDecimal priceWithoutDiscount) {
        template.setPriceWithoutDiscount(priceWithoutDiscount);
        return this;
    }

    public TicketBuilder taxRate(String taxRate) {
        template.setTaxRate(taxRate);
        return this;
    }

    public TicketBuilder discount(String discount) {
        template.setDiscount(discount);
        return this;
    }

    public TicketBuilder transportTypes(List<String> transportType) {
        template.setTransportTypes(transportType);
        return this;
    }

    public TicketBuilder companyName(String companyName) {
        template.setCompanyName(companyName);
        return this;
    }

    public TicketBuilder addressFrom(Address address) {
        template.setAddressFrom(address);
        return this;
    }

    public TicketBuilder addressTo(Address address) {
        template.setAddressTo(address);
        return this;
    }

    public TicketBuilder depTime(ZonedDateTime depTime) {
        template.setDepartureTime(depTime);
        return this;
    }

    public TicketBuilder validUntil(ZonedDateTime depTime) {
        template.setValidUntil(depTime);
        return this;
    }



    public TicketBuilder withDefaults() {
        return this
                .ticketId("1234234")
                .offerId("1231252135")
                .userId("userId")
                .type(OfferTypeEnum.SINGLE_TICKET)
                .status(StatusEnum.NOT_ACTIVE)
                .numberOfLuggage(0)
                .passenger(PassengerTypeEnum.ADULT)
                .placeType("ECO")
                .priceWithDiscount(new BigDecimal(10.10))
                .priceWithoutDiscount(new BigDecimal(10.10))
                .taxRate(new BigDecimal(0.10).toString())
                .discount("1.0")
                .transportTypes(Collections.singletonList(TransportTypeEnum.BUS.name()))
                .companyName("NORDEKA")
                .addressFrom(new AddressBuilder().withDefaults().build())
                .addressTo(new AddressBuilder().withDefaults().build())
                .validUntil(ZonedDateTime.of(2021, 5, 5, 12, 0, 0, 0, ZoneId.systemDefault()))
                .depTime(ZonedDateTime.of(2020, 5, 5, 12, 0, 0, 0, ZoneId.systemDefault()))
                ;
    }
    public Ticket build() {
        return template;
    }
}
