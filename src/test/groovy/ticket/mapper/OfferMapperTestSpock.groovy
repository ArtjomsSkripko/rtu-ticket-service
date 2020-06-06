package ticket.mapper

import spock.lang.Specification
import ticket.builders.JourneyOfferDTOBuilder
import ticket.builders.TicketBuilder
import ticket.model.JourneyOffer
import ticket.model.Ticket
import ticket.rest.model.JourneyOfferDTO
import ticket.rest.model.TicketDTO

class OfferMapperTestSpock extends Specification {

    OfferMapper mapper = new OfferMapper()

    void "testing toOfferDTO method"() {
        given:
        JourneyOfferDTO input = new JourneyOfferDTOBuilder().withDefaults().build()

        when:
        JourneyOffer output = mapper.toDomainOffer(input)

        then:
        output != null
        output.getAddressFrom().getCity() == input.getAddressFrom().getCity()
        output.getAddressFrom().getStreetName() == input.getAddressFrom().getStreetName()
        output.getAddressFrom().getHomeNumber() == input.getAddressFrom().getHomeNumber()
        output.getAddressTo().getCity() == input.getAddressTo().getCity()
        output.getAddressTo().getStreetName() == input.getAddressTo().getStreetName()
        output.getAddressTo().getHomeNumber() == input.getAddressTo().getHomeNumber()
        output.getDepartureTime() == input.getDepartureTime()
        output.getIntercityOffer() == input.getIntercityOffer()
        output.getIntercityOffer() == input.getIntercityOffer()
        output.getRegionalOffer() == input.getRegionalOffers()
    }

    void "testing ToTicketDTO method"() {
        given:
        Ticket input = new TicketBuilder().withDefaults().build()

        when:
        TicketDTO output = mapper.toDTOTicket(input)

        then:
        output != null
        output.getAddressFrom().getCity() == input.getAddressFrom().getCity()
        output.getAddressFrom().getStreetName() == input.getAddressFrom().getStreetName()
        output.getAddressFrom().getHomeNumber() == input.getAddressFrom().getHomeNumber()
        output.getAddressTo().getCity() == input.getAddressTo().getCity()
        output.getAddressTo().getStreetName() == input.getAddressTo().getStreetName()
        output.getAddressTo().getHomeNumber() == input.getAddressTo().getHomeNumber()
        output.getDepartureTime() == input.getDepartureTime().toString()
        output.getCompanyName() == input.getCompanyName()
        output.getPriceWithDiscount() == input.getPriceWithDiscount().toPlainString()
        output.getPriceWithoutDiscount() == input.getPriceWithoutDiscount().toPlainString()
        output.getDiscount() == input.getDiscount()
        output.getPassenger() == input.getPassenger().name()
    }
}
