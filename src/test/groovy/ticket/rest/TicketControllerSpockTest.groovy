package ticket.rest

import spock.lang.Specification
import ticket.exception.UnauthorizedException
import ticket.mapper.OfferMapper
import ticket.rest.model.JourneyOfferDTO
import ticket.service.TicketService

class TicketControllerSpockTest extends Specification {

    TicketService service
    OfferMapper mapper
    TicketController controller

    def setup() {
        service = Mock(TicketService.class)
        mapper = Mock(OfferMapper.class)
        controller = new TicketController(service, mapper)
    }

    void "call create tickets unauthorized"() {
        given:
        JourneyOfferDTO journeyOfferDTO = new JourneyOfferDTO()
        when:
        controller.reserveTicket([journeyOfferDTO])
        then:
        thrown(UnauthorizedException.class)
    }
}
