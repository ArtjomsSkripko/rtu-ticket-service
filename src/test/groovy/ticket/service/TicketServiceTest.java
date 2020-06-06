package ticket.service;

import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import ticket.builders.JourneyOfferBuilder;
import ticket.builders.TicketBuilder;
import ticket.exception.OfferAlreadyUsedException;
import ticket.model.JourneyOffer;
import ticket.model.Ticket;
import ticket.repository.TicketRepository;
import ticket.rest.model.RegionalOfferDTO;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;

//Not possible to create parametrized test + one not parametrized test
public class TicketServiceTest {

    private static TicketService service;
    private static TicketRepository repository;
    private static RegionalOfferPerformer regionalOfferPerformer;
    private static IntercityOfferPerformer intercityOfferPerformer;

    @BeforeClass
    public static void initiate() {
        repository = mock(TicketRepository.class);
        regionalOfferPerformer = mock(RegionalOfferPerformer.class);
        intercityOfferPerformer = mock(IntercityOfferPerformer.class);
        service = new TicketService(regionalOfferPerformer, intercityOfferPerformer, repository);
    }

    @Before
    public void setup() {
        reset(repository, regionalOfferPerformer, intercityOfferPerformer);
    }

    @Test
    public void testCreateTicket() {
        JourneyOffer offer = new JourneyOfferBuilder().withDefaults().build();

        Mockito.when(regionalOfferPerformer.buildTickets(anyListOf(RegionalOfferDTO.class)))
                .thenReturn(Collections.singletonList(new TicketBuilder().withDefaults().ticketId("123").build()));
        Mockito.when(intercityOfferPerformer.buildTicket(any()))
                .thenReturn(new TicketBuilder().withDefaults().ticketId("567").build());
        Mockito.doNothing().when(repository).validateOfferIds(anyListOf(String.class));

        List<Ticket> results = service.createTickets(Collections.singletonList(offer));

        Assert.assertNotNull(results);
        Assert.assertFalse(results.isEmpty());
        Assert.assertEquals(results.size(), 2);
        Assert.assertEquals(results.get(0).getTicketId(), "123");
        Assert.assertEquals(results.get(1).getTicketId(), "567");

        Mockito.verify(regionalOfferPerformer, Mockito.times(1)).buildTickets(anyListOf(RegionalOfferDTO.class));
        Mockito.verify(intercityOfferPerformer, Mockito.times(1)).buildTicket(any());
        Mockito.verify(repository, Mockito.times(1)).validateOfferIds(anyListOf(String.class));
    }

    @Test
    public void testCreateTicketOnlyRegionalOffers() {
        JourneyOffer offer = new JourneyOfferBuilder().withDefaults().intercityOffer(null).build();

        Mockito.when(regionalOfferPerformer.buildTickets(anyListOf(RegionalOfferDTO.class)))
                .thenReturn(Collections.singletonList(new TicketBuilder().withDefaults().ticketId("123").build()));
        Mockito.doNothing().when(repository).validateOfferIds(anyListOf(String.class));

        List<Ticket> results = service.createTickets(Collections.singletonList(offer));

        Assert.assertNotNull(results);
        Assert.assertFalse(results.isEmpty());
        Assert.assertEquals(results.size(), 1);
        Assert.assertEquals(results.get(0).getTicketId(), "123");

        Mockito.verify(regionalOfferPerformer, Mockito.times(1)).buildTickets(anyListOf(RegionalOfferDTO.class));
        Mockito.verify(intercityOfferPerformer, Mockito.times(0)).buildTicket(any());
        Mockito.verify(repository, Mockito.times(1)).validateOfferIds(anyListOf(String.class));
    }

    @Test
    public void testCreateTicketOnlyIntercityOffers() {
        JourneyOffer offer = new JourneyOfferBuilder().withDefaults().regionalOffer(null).build();

        Mockito.when(intercityOfferPerformer.buildTicket(any()))
                .thenReturn(new TicketBuilder().withDefaults().ticketId("567").build());
        Mockito.doNothing().when(repository).validateOfferIds(anyListOf(String.class));

        List<Ticket> results = service.createTickets(Collections.singletonList(offer));

        Assert.assertNotNull(results);
        Assert.assertFalse(results.isEmpty());
        Assert.assertEquals(results.size(), 1);
        Assert.assertEquals(results.get(0).getTicketId(), "567");

        Mockito.verify(regionalOfferPerformer, Mockito.times(0)).buildTickets(anyListOf(RegionalOfferDTO.class));
        Mockito.verify(intercityOfferPerformer, Mockito.times(1)).buildTicket(any());
        Mockito.verify(repository, Mockito.times(1)).validateOfferIds(anyListOf(String.class));
    }

    @Test(expected = OfferAlreadyUsedException.class)
    public void testCreateTicketWhenOffersAlreadyUsed() {
        JourneyOffer offer = new JourneyOfferBuilder().withDefaults().build();
        Mockito.doThrow(new OfferAlreadyUsedException("OffersAreAlreadyUsed")).when(repository).validateOfferIds(anyListOf(String.class));
        service.createTickets(Collections.singletonList(offer));
    }
}
