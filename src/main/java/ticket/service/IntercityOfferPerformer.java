package ticket.service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ticket.authorization.Utils;
import ticket.enumeration.PassengerTypeEnum;
import ticket.enumeration.StatusEnum;
import ticket.mapper.OfferMapper;
import ticket.model.Address;
import ticket.model.Ticket;
import ticket.repository.TicketRepository;
import ticket.rest.model.IntercityOfferDTO;
import ticket.rest.model.RegionalOfferDTO;

@Service
public class IntercityOfferPerformer {

    @Autowired
    private TicketRepository repository;

    public Ticket buildTicket(IntercityOfferDTO offer) {
        Ticket ticket = new Ticket();
        ticket.setOfferId(offer.getId());
        ticket.setTicketId(UUID.randomUUID().toString());
        ticket.setAddressFrom(new Address(offer.getDepCity()));
        ticket.setAddressTo(new Address(offer.getDestCity()));
        ticket.setValidUntil(offer.getDepTime().plusHours(5));
        ticket.setCompanyName(offer.getCompanyName());
        ticket.setDiscount(offer.getDiscount());
        ticket.setPassenger(PassengerTypeEnum.valueOf(offer.getPassenger()));
        ticket.setPlaceType(offer.getPlaceType());
        ticket.setPriceWithoutDiscount(BigDecimal.valueOf(Double.parseDouble(offer.getOriginalPrice())));
        ticket.setPriceWithDiscount(BigDecimal.valueOf(Double.parseDouble(offer.getPriceWithDiscount())));
        ticket.setDiscount(offer.getDiscount());
        ticket.setNumberOfLuggage(offer.getNumberOfLuggage());
        ticket.setStatus(StatusEnum.NOT_ACTIVE);
        ticket.setUserId(Utils.getServiceUser() != null ? Utils.getServiceUser().getUserId() : null);

        repository.saveTickets(Collections.singletonList(ticket));

        return ticket;
    }
}
