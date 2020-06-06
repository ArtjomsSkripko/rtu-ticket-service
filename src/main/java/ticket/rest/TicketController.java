package ticket.rest;

import java.util.List;
import java.util.stream.Collectors;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import ticket.authorization.UserToken;
import ticket.authorization.Utils;
import ticket.exception.UnauthorizedException;
import ticket.mapper.OfferMapper;
import ticket.model.Ticket;
import ticket.rest.model.JourneyOfferDTO;
import ticket.rest.model.TicketDTO;
import ticket.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@Api(protocols = "http, https")
@RequestMapping(value = "v1/ticket")
public class TicketController {

    private TicketService ticketService;
    private OfferMapper mapper;

    @Autowired
    public TicketController(TicketService ticketService, OfferMapper mapper) {
        this.ticketService = ticketService;
        this.mapper = mapper;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ApiOperation(
            value = "Reserve tickets",
            notes = "Creates tickets (reservation) from provided journey offers",
            tags = {"Tickets"}
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "InvalidRequestError, code 350: Invalid request"),
            @ApiResponse(code = 500, message = "SomeError")
    })
    public List<TicketDTO> reserveTicket(@RequestBody List<JourneyOfferDTO> journeyOffers) {
        UserToken token = Utils.getServiceUser();
        if (token == null) {
           throw new UnauthorizedException("current user is not authorized to fetch offers");
        }

        List<Ticket> offers = ticketService.createTickets(journeyOffers.stream()
                .map(o -> mapper.toDomainOffer(o)).collect(Collectors.toList()));
        return offers.stream().map(o -> mapper.toDTOTicket(o)).collect(Collectors.toList());
    }
}

