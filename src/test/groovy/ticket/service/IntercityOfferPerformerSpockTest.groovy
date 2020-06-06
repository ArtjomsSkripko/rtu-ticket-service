package ticket.service

import org.springframework.test.util.ReflectionTestUtils
import spock.lang.Specification
import ticket.builders.IntercityOfferDTOBuilder
import ticket.enumeration.StatusEnum
import ticket.model.Ticket
import ticket.repository.TicketRepository
import ticket.rest.model.IntercityOfferDTO

class IntercityOfferPerformerSpockTest extends Specification {

    TicketRepository repository
    IntercityOfferPerformer intercityOfferPerformer

    def setup() {
        repository = Mock(TicketRepository.class)
        intercityOfferPerformer = new IntercityOfferPerformer()
        ReflectionTestUtils.setField(intercityOfferPerformer, "repository", repository, TicketRepository.class)
    }

    void "build ticket from intercity offer"() {
        given:
        IntercityOfferDTO offer = new IntercityOfferDTOBuilder().withDefaults().build()

        when:
        def result = intercityOfferPerformer.buildTicket(offer)

        then:
        result != null
        result.getTicketId() != null
        result.getValidUntil() != null
        result.getOfferId() == offer.getId()
        result.getAddressFrom().getCity() == offer.getDepCity()
        result.getAddressTo().getCity() == offer.getDestCity()
        result.getCompanyName() == offer.getCompanyName()
        result.getDiscount() == offer.getDiscount()
        result.getPassenger().name() == offer.getPassenger()
        result.getPlaceType() == offer.getPlaceType()
        result.getPriceWithoutDiscount().toString() == offer.getOriginalPrice()
        result.getPriceWithDiscount().toPlainString() == offer.getPriceWithDiscount()
        result.getDiscount() == offer.getDiscount()
        result.getNumberOfLuggage() == offer.getNumberOfLuggage()
        result.getStatus() == StatusEnum.NOT_ACTIVE

        1 * repository.saveTickets(*_)
    }


}
