package ticket.mapper;

import ticket.model.Address;
import ticket.model.JourneyOffer;
import ticket.model.Ticket;
import ticket.rest.model.AddressDTO;
import ticket.rest.model.JourneyOfferDTO;
import org.springframework.stereotype.Component;
import ticket.rest.model.TicketDTO;

@Component
public class OfferMapper {

    public JourneyOffer toDomainOffer(JourneyOfferDTO offer) {
        JourneyOffer dto = new JourneyOffer();

        dto.setAddressFrom(toDomainAddress(offer.getAddressFrom()));
        dto.setAddressTo(toDomainAddress(offer.getAddressTo()));
        dto.setDepartureTime(offer.getDepartureTime());
        dto.setIntercityOffer(offer.getIntercityOffer());
        dto.setRegionalOffer(offer.getRegionalOffers());

        return dto;
    }

    public TicketDTO toDTOTicket(Ticket ticket) {
        TicketDTO dto = new TicketDTO();

        dto.setTicketId(ticket.getTicketId());
        dto.setType(ticket.getType() != null ? ticket.getType().name() : null);
        dto.setCompanyName(ticket.getCompanyName());
        dto.setPassenger(ticket.getPassenger().name());
        dto.setTransportTypes(ticket.getTransportTypes());
        dto.setPriceWithoutDiscount(ticket.getPriceWithoutDiscount().toPlainString());
        dto.setPriceWithDiscount(ticket.getPriceWithDiscount().toPlainString());
        dto.setTaxRate(ticket.getTaxRate());
        dto.setRouteNumbers(ticket.getRouteNumbers());
        dto.setAddressFrom(toDTOAddress(ticket.getAddressFrom()));
        dto.setAddressTo(toDTOAddress(ticket.getAddressTo()));
        dto.setDepartureTime(ticket.getDepartureTime() != null ? ticket.getDepartureTime().toString() : null);
        dto.setPlaceType(ticket.getPlaceType());
        dto.setDiscount(ticket.getDiscount());
        dto.setStatus(ticket.getStatus().name());
        dto.setValidUntil(ticket.getValidUntil().toString());
        dto.setUserId(ticket.getUserId());
        dto.setOfferId(ticket.getOfferId());
        dto.setNumberOfLuggage(ticket.getNumberOfLuggage());

        return dto;
    }

    public Address toDomainAddress(AddressDTO addressDTO) {
        Address address = new Address();

        address.setCity(addressDTO.getCity());
        address.setHomeNumber(addressDTO.getHomeNumber());
        address.setStreetName(addressDTO.getStreetName());

        return address;
    }

    public AddressDTO toDTOAddress(Address address) {
        AddressDTO addressDTO = new AddressDTO();

        addressDTO.setCity(address.getCity());
        addressDTO.setHomeNumber(address.getHomeNumber());
        addressDTO.setStreetName(address.getStreetName());

        return addressDTO;
    }
}
