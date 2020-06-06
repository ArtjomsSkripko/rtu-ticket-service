package ticket.service;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ticket.authorization.Utils;
import ticket.enumeration.PassengerTypeEnum;
import ticket.enumeration.StatusEnum;
import ticket.mapper.OfferMapper;
import ticket.model.Ticket;
import ticket.repository.TicketRepository;
import ticket.rest.model.RegionalOfferDTO;

@Service
public class RegionalOfferPerformer {

    @Autowired
    private OfferMapper mapper;

    @Autowired
    private TicketRepository repository;

    public List<Ticket> buildTickets(Collection<RegionalOfferDTO> offers) {

        List<Ticket> ticketList = new ArrayList<>();
        offers.forEach(offer -> {
            for(int i = 0; i < offer.getNumberOfTickets(); i++) {
                Ticket ticket = new Ticket();
                ticket.setOfferId(offer.getId());
                ticket.setTicketId(UUID.randomUUID().toString());
                ticket.setAddressFrom(mapper.toDomainAddress(offer.getAddressFrom()));
                ticket.setAddressTo(mapper.toDomainAddress(offer.getAddressTo()));
                ticket.setValidUntil(ZonedDateTime.now().plusYears(1));
                ticket.setCompanyName(offer.getCompanyName());
                ticket.setDiscount(offer.getDiscount());
                ticket.setPassenger(PassengerTypeEnum.valueOf(offer.getPassenger()));
                ticket.setRouteNumbers(offer.getRouteNumbers());
                ticket.setTransportTypes(offer.getTransportTypes());
                ticket.setPriceWithoutDiscount(BigDecimal.valueOf(Double.parseDouble(offer.getOriginalPrice())));
                ticket.setPriceWithDiscount(BigDecimal.valueOf(Double.parseDouble(offer.getPriceWithDiscount())));
                ticket.setDiscount(offer.getDiscount());
                ticket.setStatus(StatusEnum.NOT_ACTIVE);
                ticket.setUserId(Utils.getServiceUser() != null ? Utils.getServiceUser().getUserId() : null);
                ticketList.add(ticket);
            }
        });

        repository.saveTickets(ticketList);

        return ticketList;
    }
}
