package ticket.mapper;


import org.testng.Assert;
import org.testng.annotations.Test;
import ticket.builders.JourneyOfferDTOBuilder;
import ticket.builders.TicketBuilder;
import ticket.model.JourneyOffer;
import ticket.model.Ticket;
import ticket.rest.model.JourneyOfferDTO;
import ticket.rest.model.TicketDTO;

public class OfferMapperNGTest {

    private OfferMapper mapper = new OfferMapper();

    @Test
    public void testToDomainOffer() {
        JourneyOfferDTO input = new JourneyOfferDTOBuilder().withDefaults().build();

        JourneyOffer output = mapper.toDomainOffer(input);

        Assert.assertNotNull(output);
        Assert.assertEquals(output.getAddressFrom().getCity(), input.getAddressFrom().getCity());
        Assert.assertEquals(output.getAddressFrom().getStreetName(), input.getAddressFrom().getStreetName());
        Assert.assertEquals(output.getAddressFrom().getHomeNumber(), input.getAddressFrom().getHomeNumber());
        Assert.assertEquals(output.getAddressTo().getCity(), input.getAddressTo().getCity());
        Assert.assertEquals(output.getAddressTo().getStreetName(), input.getAddressTo().getStreetName());
        Assert.assertEquals(output.getAddressTo().getHomeNumber(), input.getAddressTo().getHomeNumber());
        Assert.assertEquals(output.getDepartureTime(), input.getDepartureTime());
        Assert.assertEquals(output.getIntercityOffer(), input.getIntercityOffer());
        Assert.assertEquals(output.getRegionalOffer(), input.getRegionalOffers());
    }

    @Test
    public void testToTicketDTO() {
        Ticket input = new TicketBuilder().withDefaults().build();

        TicketDTO output = mapper.toDTOTicket(input);

        Assert.assertNotNull(output);
        Assert.assertEquals(output.getAddressFrom().getCity(), input.getAddressFrom().getCity());
        Assert.assertEquals(output.getAddressFrom().getStreetName(), input.getAddressFrom().getStreetName());
        Assert.assertEquals(output.getAddressFrom().getHomeNumber(), input.getAddressFrom().getHomeNumber());
        Assert.assertEquals(output.getAddressTo().getCity(), input.getAddressTo().getCity());
        Assert.assertEquals(output.getAddressTo().getStreetName(), input.getAddressTo().getStreetName());
        Assert.assertEquals(output.getAddressTo().getHomeNumber(), input.getAddressTo().getHomeNumber());
        Assert.assertEquals(output.getDepartureTime(), input.getDepartureTime().toString());
        Assert.assertEquals(output.getCompanyName(), input.getCompanyName());
        Assert.assertEquals(output.getPriceWithDiscount(), input.getPriceWithDiscount().toPlainString());
        Assert.assertEquals(output.getPriceWithoutDiscount(), input.getPriceWithoutDiscount().toPlainString());
        Assert.assertEquals(output.getDiscount(), input.getDiscount());
        Assert.assertEquals(output.getPassenger(), input.getPassenger().name());
    }
}
