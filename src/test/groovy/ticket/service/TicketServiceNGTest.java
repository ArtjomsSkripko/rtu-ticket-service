package ticket.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.mockito.Mockito;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
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
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertNotNull;

public class TicketServiceNGTest {

    private static TicketService service;
    private static TicketRepository repository;
    private static RegionalOfferPerformer regionalOfferPerformer;
    private static IntercityOfferPerformer intercityOfferPerformer;

    @BeforeSuite
    public static void initiate() {
        repository = mock(TicketRepository.class);
        regionalOfferPerformer = mock(RegionalOfferPerformer.class);
        intercityOfferPerformer = mock(IntercityOfferPerformer.class);
        service = new TicketService(regionalOfferPerformer, intercityOfferPerformer, repository);
    }

    @Test(dataProvider = "parameterizedTest")
    public void testCreateTicket(JourneyOffer journeyOffer, Triple<Integer, List<String>, Pair<Integer, Integer>> expectedResult) {
        Mockito.when(regionalOfferPerformer.buildTickets(anyListOf(RegionalOfferDTO.class)))
                .thenReturn(Collections.singletonList(new TicketBuilder().withDefaults().ticketId("123").build()));
        Mockito.when(intercityOfferPerformer.buildTicket(any()))
                .thenReturn(new TicketBuilder().withDefaults().ticketId("567").build());
        Mockito.doNothing().when(repository).validateOfferIds(anyListOf(String.class));

        List<Ticket> results = service.createTickets(Collections.singletonList(journeyOffer));

        assertNotNull(results);
        assertFalse(results.isEmpty());
        assertEquals(results.size(), (int) expectedResult.getLeft());
        for (int i = 0; i < results.size(); i++) {
            assertEquals(results.get(i).getTicketId(), expectedResult.getMiddle().get(i));
        }

        Mockito.verify(regionalOfferPerformer, Mockito.times(expectedResult.getRight().getLeft())).buildTickets(anyListOf(RegionalOfferDTO.class));
        Mockito.verify(intercityOfferPerformer, Mockito.times(expectedResult.getRight().getRight())).buildTicket(any());
        Mockito.verify(repository, Mockito.times(1)).validateOfferIds(anyListOf(String.class));
        Mockito.reset(repository, regionalOfferPerformer, intercityOfferPerformer);
    }

    @DataProvider(name = "parameterizedTest")
    public static Object[][] journeyOffers() {
        return new Object[][]{
                {new JourneyOfferBuilder().withDefaults().build(), Triple.of(2, Arrays.asList("123", "567"), Pair.of(1, 1))},
                {new JourneyOfferBuilder().withDefaults().regionalOffer(null).build(), Triple.of(1, Collections.singletonList("567"), Pair.of(0, 1))},
                {new JourneyOfferBuilder().withDefaults().intercityOffer(null).build(), Triple.of(1, Collections.singletonList("123"), Pair.of(1, 0))},
        };
    }

    @Test(expectedExceptions = OfferAlreadyUsedException.class)
    public void testCreateTicketWhenOffersAlreadyUsed() {
        JourneyOffer offer = new JourneyOfferBuilder().withDefaults().build();
        Mockito.doThrow(new OfferAlreadyUsedException("OffersAreAlreadyUsed")).when(repository).validateOfferIds(anyListOf(String.class));
        service.createTickets(Collections.singletonList(offer));
    }
}
