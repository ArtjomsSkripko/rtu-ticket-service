package ticket.service;

import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import ticket.builders.IntercityOfferDTOBuilder;
import ticket.enumeration.StatusEnum;
import ticket.model.Ticket;
import ticket.repository.TicketRepository;
import ticket.rest.model.IntercityOfferDTO;

import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.mock;

public class IntercityOfferPerformerNGTest {

    private static IntercityOfferPerformer intercityOfferPerformer;
    private static TicketRepository repository;

    @BeforeSuite
    public static void initiate() throws Exception {
        repository = mock(TicketRepository.class);
        intercityOfferPerformer = new IntercityOfferPerformer();
        ReflectionTestUtils.setField(intercityOfferPerformer, "repository", repository, TicketRepository.class);
    }

    @Test
    public void testBuildTicket() {
        IntercityOfferDTO offer = new IntercityOfferDTOBuilder().withDefaults().build();

        Ticket result = intercityOfferPerformer.buildTicket(offer);

        Assert.assertNotNull(result);
        Assert.assertNotNull(result.getTicketId());
        Assert.assertNotNull(result.getValidUntil());
        Assert.assertEquals(result.getOfferId(), offer.getId());
        Assert.assertEquals(result.getAddressFrom().getCity(), offer.getDepCity());
        Assert.assertEquals(result.getAddressTo().getCity(), offer.getDestCity());
        Assert.assertEquals(result.getCompanyName(), offer.getCompanyName());
        Assert.assertEquals(result.getDiscount(), offer.getDiscount());
        Assert.assertEquals(result.getPassenger().name(), offer.getPassenger());
        Assert.assertEquals(result.getPlaceType(), offer.getPlaceType());
        Assert.assertEquals(result.getPriceWithoutDiscount().toString(), offer.getOriginalPrice());
        Assert.assertEquals(result.getPriceWithDiscount().toPlainString(), offer.getPriceWithDiscount());
        Assert.assertEquals(result.getDiscount(), offer.getDiscount());
        Assert.assertEquals(result.getNumberOfLuggage(), offer.getNumberOfLuggage());
        Assert.assertEquals(result.getStatus(), StatusEnum.NOT_ACTIVE);

        Mockito.verify(repository, Mockito.times(1)).saveTickets(anyListOf(Ticket.class));
    }
}
