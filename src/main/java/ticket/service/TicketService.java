package ticket.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.util.CollectionUtils;
import ticket.model.JourneyOffer;
import ticket.model.Ticket;
import ticket.repository.TicketRepository;
import ticket.rest.model.RegionalOfferDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketService {


    private RegionalOfferPerformer regionalOfferPerformer;
    private IntercityOfferPerformer intercityOfferPerformer;
    private TicketRepository repository;

    @Autowired
    public TicketService(RegionalOfferPerformer regionalOfferPerformer,
                         IntercityOfferPerformer intercityOfferPerformer,
                         TicketRepository repository) {
        this.regionalOfferPerformer = regionalOfferPerformer;
        this.intercityOfferPerformer = intercityOfferPerformer;
        this.repository = repository;
    }

    public List<Ticket> createTickets(List<JourneyOffer> journeyOffers) {
        List<String> offerIds = new ArrayList<>();

        journeyOffers.forEach(journeyOffer -> {
            if (journeyOffer.getIntercityOffer() != null) {
                offerIds.add(journeyOffer.getIntercityOffer().getId());
            }
            if (!CollectionUtils.isEmpty(journeyOffer.getRegionalOffer())) {
                journeyOffer.getRegionalOffer().values().forEach(regional -> offerIds.add(regional.getId()));
            }
        });

        repository.validateOfferIds(offerIds);

        List<Ticket> createdTickets = new ArrayList<>();
        journeyOffers.forEach(journeyOffer -> {
            Map<String, RegionalOfferDTO> regionalOffer = journeyOffer.getRegionalOffer();
            if (!CollectionUtils.isEmpty(regionalOffer)) {
                createdTickets.addAll(regionalOfferPerformer.buildTickets(regionalOffer.values()));
            }
            if (journeyOffer.getIntercityOffer() != null) {
                createdTickets.add(intercityOfferPerformer.buildTicket(journeyOffer.getIntercityOffer()));
            }
        });

        return createdTickets;
    }
}
