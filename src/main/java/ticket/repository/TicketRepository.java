package ticket.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import ticket.exception.OfferAlreadyUsedException;
import ticket.model.Ticket;

@Repository
public class TicketRepository {

    private SimpleJdbcInsert insertTicketTmpl;

    private NamedParameterJdbcOperations namedJdbcTemplate;
    public static String COMPANY_NAME_COLUMN = "company_name";
    public static String PLACE_TYPE_COLUMN = "place_type";
    public static String TRANSPORT_TYPE_COLUMN = "transport_type";
    public static String DEP_CITY_COLUMN = "dep_city";
    public static String DEP_STREET_COLUMN = "dep_street";
    public static String DEP_HOME_COLUMN = "dep_home";
    public static String DEST_HOME_COLUMN = "dest_home";
    public static String DEST_STREET_COLUMN = "dest_street";
    public static String DEST_CITY_COLUMN = "dest_city";
    public static String DEP_TIME_COLUMN = "dep_time";
    public static String TICKET_ID = "ticket_id";
    public static String TYPE = "type";
    public static String PASSENGER = "passenger";
    public static String PRICE_WITH_DISCOUNT = "price_with_discount";
    public static String PRICE_WITHOUT_DISCOUNT = "price_without_discount";
    public static String TAX_RATE = "tax_rate";
    public static String ROUTE_NUMBERS = "route_number";
    public static String VALID_UNTIL = "valid_until";
    public static String USER_ID = "user_id";
    public static String DISCOUNT = "discount";
    public static String OFFER_ID = "offer_id";


    @Autowired
    public TicketRepository(NamedParameterJdbcOperations namedParameterJdbcTemplate, DataSource dataSource) {
        this.namedJdbcTemplate = namedParameterJdbcTemplate;

        this.insertTicketTmpl = new SimpleJdbcInsert(dataSource).withTableName("ticket")
                .usingColumns(COMPANY_NAME_COLUMN, PLACE_TYPE_COLUMN, TRANSPORT_TYPE_COLUMN, DEP_CITY_COLUMN, DEP_STREET_COLUMN
                        , DEP_HOME_COLUMN, DEST_HOME_COLUMN, DEST_STREET_COLUMN, DEST_CITY_COLUMN
                        , DEP_TIME_COLUMN, TICKET_ID, TYPE, PASSENGER, PRICE_WITH_DISCOUNT
                        , PRICE_WITHOUT_DISCOUNT, TAX_RATE, ROUTE_NUMBERS, VALID_UNTIL, USER_ID, DISCOUNT, OFFER_ID);
    }

    public void saveTickets(List<Ticket> tickets) {
        Collection<MapSqlParameterSource> paramsList = new ArrayList<>(tickets.size());

        tickets.forEach(ticket -> {
            MapSqlParameterSource params = new MapSqlParameterSource();
            addToParams(params, COMPANY_NAME_COLUMN, ticket.getCompanyName());
            addToParams(params, PLACE_TYPE_COLUMN, ticket.getPlaceType());
            List<String> transportTypes = ticket.getTransportTypes();
            if (!CollectionUtils.isEmpty(transportTypes)) {
                StringBuilder transportType = new StringBuilder();
                for (int i = 0; i < transportTypes.size(); i++) {
                    transportType.append(transportTypes.get(i));
                    if (i != transportTypes.size() - 1) {
                        transportType.append(",");
                    }
                }
                addToParams(params, TRANSPORT_TYPE_COLUMN, transportType);
            }
            addToParams(params, DEP_CITY_COLUMN, ticket.getAddressFrom().getCity());
            addToParams(params, DEP_STREET_COLUMN, ticket.getAddressFrom().getStreetName());
            addToParams(params, DEP_HOME_COLUMN, ticket.getAddressFrom().getHomeNumber());
            addToParams(params, DEST_HOME_COLUMN, ticket.getAddressTo().getHomeNumber());
            addToParams(params, DEST_STREET_COLUMN, ticket.getAddressTo().getStreetName());
            addToParams(params, DEST_CITY_COLUMN, ticket.getAddressTo().getCity());
            if(ticket.getDepartureTime() != null)
            addToParams(params, DEP_TIME_COLUMN, ticket.getDepartureTime().toString());
            if(ticket.getType() != null)
            addToParams(params, TYPE, ticket.getType().name());
            addToParams(params, TICKET_ID, ticket.getTicketId());
            addToParams(params, OFFER_ID, ticket.getOfferId());
            addToParams(params, PASSENGER, ticket.getPassenger().name());
            addToParams(params, PRICE_WITH_DISCOUNT, ticket.getPriceWithDiscount().toString());
            addToParams(params, PRICE_WITHOUT_DISCOUNT, ticket.getPriceWithoutDiscount().toString());
            addToParams(params, TAX_RATE, ticket.getTaxRate());
            List<String> routeNumbers = ticket.getRouteNumbers();
            if (!CollectionUtils.isEmpty(routeNumbers)) {
                StringBuilder routeNumber = new StringBuilder();
                for (int i = 0; i < routeNumbers.size(); i++) {
                    routeNumber.append(routeNumbers.get(i));
                    if (i != routeNumbers.size() - 1) {
                        routeNumber.append(",");
                    }
                }
                addToParams(params, ROUTE_NUMBERS, routeNumber.toString());
            }
            addToParams(params, VALID_UNTIL, ticket.getValidUntil().toString());
            addToParams(params, USER_ID, ticket.getUserId());
            addToParams(params, DISCOUNT, ticket.getDiscount());
            paramsList.add(params);
        });

        insertTicketTmpl.executeBatch(paramsList.toArray(new MapSqlParameterSource[0]));
    }

    public void validateOfferIds(List<String> offerIds) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(OFFER_ID, offerIds);

        String sql = "Select count(*) from ticket where offer_id IN (:" + OFFER_ID + ")";
        Integer foundRows = namedJdbcTemplate.queryForObject(sql, params, Integer.class);

        if (foundRows != null && foundRows > 0) {
            throw new OfferAlreadyUsedException("Some of offers are already used");
        }
    }

    private void addToParams(MapSqlParameterSource params, String paramName, Object value) {
        if (value != null) {
            params.addValue(paramName, value);
        }
    }

}
