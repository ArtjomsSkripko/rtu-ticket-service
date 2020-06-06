package ticket.repository;

import java.util.Collections;
import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.test.util.ReflectionTestUtils;
import ticket.builders.TicketBuilder;
import ticket.exception.OfferAlreadyUsedException;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

public class TicketRepositoryTest {

    private TicketRepository repository;
    private NamedParameterJdbcOperations namedJdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    @Before
    public void setUp() throws Exception {
        namedJdbcTemplate = mock(NamedParameterJdbcOperations.class);
        DataSource dataSource = mock(DataSource.class);
        simpleJdbcInsert = mock(SimpleJdbcInsert.class);
        repository = new TicketRepository(namedJdbcTemplate, dataSource);
        ReflectionTestUtils.setField(repository, "insertTicketTmpl", simpleJdbcInsert, SimpleJdbcInsert.class);
        ReflectionTestUtils.setField(repository, "namedJdbcTemplate", namedJdbcTemplate, NamedParameterJdbcOperations.class);
    }

    @Test
    public void testValidateOfferIds() {
        when(namedJdbcTemplate.queryForObject(anyString(), any(MapSqlParameterSource.class), eq(Integer.class)))
                .thenReturn(0);
        repository.validateOfferIds(Collections.singletonList("test"));
    }

    @Test(expected = OfferAlreadyUsedException.class)
    public void testValidateOfferIdsAlreadyUsed() {
        when(namedJdbcTemplate.queryForObject(anyString(), any(MapSqlParameterSource.class), eq(Integer.class)))
                .thenReturn(1);
        repository.validateOfferIds(Collections.singletonList("test"));
    }

    @Test
    public void testSaveTickets() {
        int[] res = {1};
        when(simpleJdbcInsert.executeBatch(any(MapSqlParameterSource[].class)))
                .thenReturn(res);
        repository.saveTickets(Collections.singletonList(new TicketBuilder().withDefaults().build()));
        Mockito.verify(simpleJdbcInsert, times(1)).executeBatch(any(MapSqlParameterSource[].class));
    }

}
