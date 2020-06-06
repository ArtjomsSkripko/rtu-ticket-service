package ticket.model;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

import ticket.enumeration.OfferTypeEnum;
import ticket.enumeration.PassengerTypeEnum;
import ticket.enumeration.StatusEnum;

public class Ticket {

    private String ticketId;
    private String offerId;
    private OfferTypeEnum type;
    private String companyName;
    private PassengerTypeEnum passenger;
    private List<String> transportTypes;
    private BigDecimal priceWithDiscount;
    private BigDecimal priceWithoutDiscount;
    private String taxRate;
    private List<String> routeNumbers;
    private Address addressFrom;
    private Address addressTo;
    private ZonedDateTime departureTime;
    private String placeType;
    private String discount;
    private StatusEnum status;
    private ZonedDateTime validUntil;
    private String userId;
    private Integer numberOfLuggage;

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public OfferTypeEnum getType() {
        return type;
    }

    public void setType(OfferTypeEnum type) {
        this.type = type;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public PassengerTypeEnum getPassenger() {
        return passenger;
    }

    public void setPassenger(PassengerTypeEnum passenger) {
        this.passenger = passenger;
    }

    public List<String> getTransportTypes() {
        return transportTypes;
    }

    public void setTransportTypes(List<String> transportTypes) {
        this.transportTypes = transportTypes;
    }

    public BigDecimal getPriceWithDiscount() {
        return priceWithDiscount;
    }

    public void setPriceWithDiscount(BigDecimal priceWithDiscount) {
        this.priceWithDiscount = priceWithDiscount;
    }

    public BigDecimal getPriceWithoutDiscount() {
        return priceWithoutDiscount;
    }

    public void setPriceWithoutDiscount(BigDecimal priceWithoutDiscount) {
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

    public Address getAddressFrom() {
        return addressFrom;
    }

    public void setAddressFrom(Address addressFrom) {
        this.addressFrom = addressFrom;
    }

    public Address getAddressTo() {
        return addressTo;
    }

    public void setAddressTo(Address addressTo) {
        this.addressTo = addressTo;
    }

    public ZonedDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(ZonedDateTime departureTime) {
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

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public ZonedDateTime getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(ZonedDateTime validUntil) {
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

    public Integer getNumberOfLuggage() {
        return numberOfLuggage;
    }

    public void setNumberOfLuggage(Integer numberOfLuggage) {
        this.numberOfLuggage = numberOfLuggage;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketId='" + ticketId + '\'' +
                ", offerId='" + offerId + '\'' +
                ", type=" + type +
                ", companyName='" + companyName + '\'' +
                ", passenger=" + passenger +
                ", transportTypes=" + transportTypes +
                ", priceWithDiscount=" + priceWithDiscount +
                ", priceWithoutDiscount=" + priceWithoutDiscount +
                ", taxRate='" + taxRate + '\'' +
                ", routeNumbers=" + routeNumbers +
                ", addressFrom=" + addressFrom +
                ", addressTo=" + addressTo +
                ", departureTime=" + departureTime +
                ", placeType='" + placeType + '\'' +
                ", discount='" + discount + '\'' +
                ", status=" + status +
                ", validUntil=" + validUntil +
                ", userId='" + userId + '\'' +
                ", numberOfLuggage=" + numberOfLuggage +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(ticketId, ticket.ticketId) &&
                Objects.equals(offerId, ticket.offerId) &&
                type == ticket.type &&
                Objects.equals(companyName, ticket.companyName) &&
                passenger == ticket.passenger &&
                Objects.equals(transportTypes, ticket.transportTypes) &&
                Objects.equals(priceWithDiscount, ticket.priceWithDiscount) &&
                Objects.equals(priceWithoutDiscount, ticket.priceWithoutDiscount) &&
                Objects.equals(taxRate, ticket.taxRate) &&
                Objects.equals(routeNumbers, ticket.routeNumbers) &&
                Objects.equals(addressFrom, ticket.addressFrom) &&
                Objects.equals(addressTo, ticket.addressTo) &&
                Objects.equals(departureTime, ticket.departureTime) &&
                Objects.equals(placeType, ticket.placeType) &&
                Objects.equals(discount, ticket.discount) &&
                status == ticket.status &&
                Objects.equals(validUntil, ticket.validUntil) &&
                Objects.equals(userId, ticket.userId) &&
                Objects.equals(numberOfLuggage, ticket.numberOfLuggage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticketId, offerId, type, companyName, passenger, transportTypes, priceWithDiscount, priceWithoutDiscount, taxRate, routeNumbers, addressFrom, addressTo, departureTime, placeType, discount, status, validUntil, userId, numberOfLuggage);
    }

}
