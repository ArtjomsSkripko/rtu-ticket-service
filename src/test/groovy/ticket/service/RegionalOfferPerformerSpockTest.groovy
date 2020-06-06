package ticket.service

import org.springframework.test.util.ReflectionTestUtils
import spock.lang.Specification
import ticket.builders.AddressBuilder
import ticket.builders.IntercityOfferDTOBuilder
import ticket.builders.RegionalOfferDTOBuilder
import ticket.enumeration.StatusEnum
import ticket.mapper.OfferMapper
import ticket.model.Address
import ticket.repository.TicketRepository
import ticket.rest.model.AddressDTO
import ticket.rest.model.IntercityOfferDTO
import ticket.rest.model.RegionalOfferDTO

class RegionalOfferPerformerSpockTest extends Specification {

    RegionalOfferPerformer regionalOfferPerformer
    TicketRepository repository
    OfferMapper mapper

    def setup() {
        repository = Mock(TicketRepository.class)
        mapper = Mock(OfferMapper.class)
        regionalOfferPerformer = new RegionalOfferPerformer()
        ReflectionTestUtils.setField(regionalOfferPerformer, "repository", repository, TicketRepository.class)
        ReflectionTestUtils.setField(regionalOfferPerformer, "mapper", mapper, OfferMapper.class)
    }

    void "build ticket from regional offer"() {
        given:
        RegionalOfferDTO offer = new RegionalOfferDTOBuilder().withDefaults().build()
        Address address = new AddressBuilder().withDefaults().build()

        and:
        mapper.toDomainAddress(offer.getAddressFrom()) >> address
        mapper.toDomainAddress(offer.getAddressTo()) >> address
        when:
        def resultList = regionalOfferPerformer.buildTickets([offer])

        then:
        def result = resultList.get(0)
        result != null
        result.getTicketId() != null
        result.getValidUntil() != null
        result.getOfferId() == offer.getId()
        result.getAddressFrom() == address
        result.getAddressTo() == address
        result.getCompanyName() == offer.getCompanyName()
        result.getDiscount() == offer.getDiscount()
        result.getPassenger().name() == offer.getPassenger()
        result.getPriceWithoutDiscount().toString() == offer.getOriginalPrice()
        result.getPriceWithDiscount().toPlainString() == offer.getPriceWithDiscount()
        result.getDiscount() == offer.getDiscount()
        result.getTransportTypes() == offer.getTransportTypes()
        result.getStatus() == StatusEnum.NOT_ACTIVE

        1 * repository.saveTickets(*_)
    }


}
