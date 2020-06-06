package ticket.service;

import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.collections.CollectionUtils;
import ticket.builders.AddressBuilder;
import ticket.builders.IntercityOfferDTOBuilder;
import ticket.builders.RegionalOfferDTOBuilder;
import ticket.enumeration.StatusEnum;
import ticket.mapper.OfferMapper;
import ticket.model.Address;
import ticket.model.Ticket;
import ticket.repository.TicketRepository;
import ticket.rest.model.AddressDTO;
import ticket.rest.model.IntercityOfferDTO;
import ticket.rest.model.RegionalOfferDTO;

import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RegionalOfferPerformerTest {

    private static RegionalOfferPerformer regionalOfferPerformer;
    private static TicketRepository repository;
    private static OfferMapper mapper;

    @BeforeClass
    public static void initiate() throws Exception {
        repository = mock(TicketRepository.class);
        mapper = mock(OfferMapper.class);
        regionalOfferPerformer = new RegionalOfferPerformer();
        ReflectionTestUtils.setField(regionalOfferPerformer, "repository", repository, TicketRepository.class);
        ReflectionTestUtils.setField(regionalOfferPerformer, "mapper", mapper, OfferMapper.class);
    }

    @Test
    public void testBuildTicket() {
        RegionalOfferDTO offer = new RegionalOfferDTOBuilder().withDefaults().build();
        Address address = new AddressBuilder().withDefaults().build();
        when(mapper.toDomainAddress(Mockito.any(AddressDTO.class))).thenReturn(address);

        List<Ticket> resultList = regionalOfferPerformer.buildTickets(Collections.singletonList(offer));

        Ticket result = resultList.get(0);
        Assert.assertNotNull(result);
        Assert.assertNotNull(result.getTicketId());
        Assert.assertNotNull(result.getValidUntil());
        Assert.assertEquals(result.getOfferId(), offer.getId());
        Assert.assertEquals(result.getAddressFrom(), address);
        Assert.assertEquals(result.getAddressTo(), address);
        Assert.assertEquals(result.getCompanyName(), offer.getCompanyName());
        Assert.assertEquals(result.getDiscount(), offer.getDiscount());
        Assert.assertEquals(result.getPassenger().name(), offer.getPassenger());
        Assert.assertEquals(result.getPriceWithoutDiscount().toString(), offer.getOriginalPrice());
        Assert.assertEquals(result.getPriceWithDiscount().toPlainString(), offer.getPriceWithDiscount());
        Assert.assertEquals(result.getDiscount(), offer.getDiscount());
        Assert.assertEquals(result.getTransportTypes(), offer.getTransportTypes());
        Assert.assertEquals(result.getStatus(), StatusEnum.NOT_ACTIVE);

        Mockito.verify(repository, Mockito.times(1)).saveTickets(anyListOf(Ticket.class));
    }
}
