package ticket.rest.model;

import java.util.List;
import java.util.Objects;

public class TicketDTO {

    private String ticketId;
    private String offerId;
    private String type;
    private String companyName;
    private String passenger;
    private List<String> transportTypes;
    private String priceWithDiscount;
    private String priceWithoutDiscount;
    private String taxRate;
    private List<String> routeNumbers;
    private AddressDTO addressFrom;
    private AddressDTO addressTo;
    private String departureTime;
    private String placeType;
    private String discount;
    private String status;
    private String validUntil;
    private String userId;
    private Integer numberOfLuggage;

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPassenger() {
        return passenger;
    }

    public void setPassenger(String passenger) {
        this.passenger = passenger;
    }

    public List<String> getTransportTypes() {
        return transportTypes;
    }

    public void setTransportTypes(List<String> transportTypes) {
        this.transportTypes = transportTypes;
    }

    public String getPriceWithDiscount() {
        return priceWithDiscount;
    }

    public void setPriceWithDiscount(String priceWithDiscount) {
        this.priceWithDiscount = priceWithDiscount;
    }

    public String getPriceWithoutDiscount() {
        return priceWithoutDiscount;
    }

    public void setPriceWithoutDiscount(String priceWithoutDiscount) {
        this.priceWithoutDiscount = priceWithoutDiscount;
    }

    public String getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(String taxRate) {
        this.taxRate = taxRate;
    }

    public List<String> getRouteNumbers() {
        return routeNumbers;
    }

    public void setRouteNumbers(List<String> routeNumbers) {
        this.routeNumbers = routeNumbers;
    }

    public AddressDTO getAddressFrom() {
        return addressFrom;
    }

    public void setAddressFrom(AddressDTO addressFrom) {
        this.addressFrom = addressFrom;
    }

    public AddressDTO getAddressTo() {
        return addressTo;
    }

    public void setAddressTo(AddressDTO addressTo) {
        this.addressTo = addressTo;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getPlaceType() {
        return placeType;
    }

    public void setPlaceType(String placeType) {
        this.placeType = placeType;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(String validUntil) {
        this.validUntil = validUntil;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    @Override
    public String toString() {
        return "TicketDTO{" +
                "ticketId='" + ticketId + '\'' +
                ", offerId='" + offerId + '\'' +
                ", type='" + type + '\'' +
                ", companyName='" + companyName + '\'' +
                ", passenger='" + passenger + '\'' +
                ", transportTypes=" + transportTypes +
                ", priceWithDiscount='" + priceWithDiscount + '\'' +
                ", priceWithoutDiscount='" + priceWithoutDiscount + '\'' +
                ", taxRate='" + taxRate + '\'' +
                ", routeNumbers=" + routeNumbers +
                ", addressFrom=" + addressFrom +
                ", addressTo=" + addressTo +
                ", departureTime='" + departureTime + '\'' +
                ", placeType='" + placeType + '\'' +
                ", discount='" + discount + '\'' +
                ", status='" + status + '\'' +
                ", validUntil='" + validUntil + '\'' +
                ", userId='" + userId + '\'' +
                ", numberOfLuggage=" + numberOfLuggage +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TicketDTO ticketDTO = (TicketDTO) o;
        return Objects.equals(ticketId, ticketDTO.ticketId) &&
                Objects.equals(offerId, ticketDTO.offerId) &&
                Objects.equals(type, ticketDTO.type) &&
                Objects.equals(companyName, ticketDTO.companyName) &&
                Objects.equals(passenger, ticketDTO.passenger) &&
                Objects.equals(transportTypes, ticketDTO.transportTypes) &&
                Objects.equals(priceWithDiscount, ticketDTO.priceWithDiscount) &&
                Objects.equals(priceWithoutDiscount, ticketDTO.priceWithoutDiscount) &&
                Objects.equals(taxRate, ticketDTO.taxRate) &&
                Objects.equals(routeNumbers, ticketDTO.routeNumbers) &&
                Objects.equals(addressFrom, ticketDTO.addressFrom) &&
                Objects.equals(addressTo, ticketDTO.addressTo) &&
                Objects.equals(departureTime, ticketDTO.departureTime) &&
                Objects.equals(placeType, ticketDTO.placeType) &&
                Objects.equals(discount, ticketDTO.discount) &&
                Objects.equals(status, ticketDTO.status) &&
                Objects.equals(validUntil, ticketDTO.validUntil) &&
                Objects.equals(userId, ticketDTO.userId) &&
                Objects.equals(numberOfLuggage, ticketDTO.numberOfLuggage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticketId, offerId, type, companyName, passenger, transportTypes, priceWithDiscount, priceWithoutDiscount, taxRate, routeNumbers, addressFrom, addressTo, departureTime, placeType, discount, status, validUntil, userId, numberOfLuggage);
    }

    public Integer getNumberOfLuggage() {
        return numberOfLuggage;
    }

    public void setNumberOfLuggage(Integer numberOfLuggage) {
        this.numberOfLuggage = numberOfLuggage;
    }

}
