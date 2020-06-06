package ticket.rest;

import java.util.Collections;

import org.junit.BeforeClass;
import org.junit.Test;
import ticket.exception.UnauthorizedException;
import ticket.mapper.OfferMapper;
import ticket.rest.model.JourneyOfferDTO;
import ticket.service.TicketService;

import static org.mockito.Mockito.mock;

public class TicketControllerTest {

    private static TicketService service;
    private static OfferMapper mapper;
    private static TicketController controller;

    @BeforeClass
    public static void initiate() {
        mapper = mock(OfferMapper.class);
        service = mock(TicketService.class);
        controller = new TicketController(service, mapper);
    }

    @Test(expected = UnauthorizedException.class)
    public void testGetOffersUnauthorized() {
        JourneyOfferDTO offerDTO = new JourneyOfferDTO();
        controller.reserveTicket(Collections.singletonList(offerDTO));
    }

}
