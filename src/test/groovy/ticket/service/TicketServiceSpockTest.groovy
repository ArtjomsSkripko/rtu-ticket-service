package ticket.service

import org.apache.commons.lang3.tuple.Pair
import org.apache.commons.lang3.tuple.Triple
import spock.lang.Specification
import spock.lang.Unroll
import ticket.builders.JourneyOfferBuilder
import ticket.builders.TicketBuilder
import ticket.exception.OfferAlreadyUsedException
import ticket.model.JourneyOffer
import ticket.repository.TicketRepository

class TicketServiceSpockTest extends Specification {

    TicketService service
    TicketRepository repository
    RegionalOfferPerformer regionalOfferPerformer
    IntercityOfferPerformer intercityOfferPerformer

    def setup() {
        repository = Mock(TicketRepository.class)
        regionalOfferPerformer = Mock(RegionalOfferPerformer.class)
        intercityOfferPerformer = Mock(IntercityOfferPerformer.class)
        service = new TicketService(regionalOfferPerformer, intercityOfferPerformer, repository)
    }

    void "create ticket when offers already used"() {
        given:
        JourneyOffer journeyOffer = new JourneyOfferBuilder().withDefaults().build()

        and:
        repository.validateOfferIds(["offerId1", "offerId2", "offerId3"]) >> { List<String> offerIds -> throw new OfferAlreadyUsedException("offer already in use") }

        when:
        service.createTickets([journeyOffer])

        then:
        thrown(OfferAlreadyUsedException)
    }

    @Unroll
    def "create ticket from offers #offer"() {
        when:
        regionalOfferPerformer.buildTickets(offer.regionalOffer != null ? offer.regionalOffer.values() : null) >> [new TicketBuilder().withDefaults().ticketId("123").build()]
        intercityOfferPerformer.buildTicket(offer.intercityOffer) >> new TicketBuilder().withDefaults().ticketId("567").build()
        def result = service.createTickets([offer])
        then:
        result.size() == expectedResult.getLeft()
        result != null
        for (int i = 0; i < result.size(); i++) {
            result.get(i).getTicketId() == expectedResult.getMiddle().get(i)
        }
        expectedResult.getRight().getLeft() * regionalOfferPerformer.buildTickets(offer.regionalOffer != null ? offer.regionalOffer.values() : null) >> [new TicketBuilder().withDefaults().ticketId("123").build()]
        expectedResult.getRight().getRight() * intercityOfferPerformer.buildTicket(offer.intercityOffer) >> new TicketBuilder().withDefaults().ticketId("567").build()
        where:
        offer                                                                 || expectedResult
        new JourneyOfferBuilder().withDefaults().build()                      || Triple.of(2, ["123", "567"], Pair.of(1, 1))
        new JourneyOfferBuilder().withDefaults().regionalOffer(null).build()  || Triple.of(1, Collections.singletonList("567"), Pair.of(0, 1))
        new JourneyOfferBuilder().withDefaults().intercityOffer(null).build() || Triple.of(1, Collections.singletonList("123"), Pair.of(1, 0))
    }

}
