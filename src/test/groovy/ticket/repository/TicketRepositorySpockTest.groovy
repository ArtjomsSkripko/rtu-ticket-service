package ticket.repository

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.jdbc.core.simple.SimpleJdbcInsert
import org.springframework.test.util.ReflectionTestUtils
import spock.lang.Specification
import ticket.exception.OfferAlreadyUsedException

import javax.sql.DataSource

class TicketRepositorySpockTest extends Specification {

    TicketRepository repository
    NamedParameterJdbcOperations namedJdbcTemplate
    SimpleJdbcInsert simpleJdbcInsert

    def setup() {
        namedJdbcTemplate = Mock(NamedParameterJdbcOperations.class)
        DataSource dataSource = Mock(DataSource.class)
        simpleJdbcInsert = Mock(SimpleJdbcInsert.class)
        repository = new TicketRepository(namedJdbcTemplate, dataSource)
        ReflectionTestUtils.setField(repository, "insertTicketTmpl", simpleJdbcInsert, SimpleJdbcInsert.class)
        ReflectionTestUtils.setField(repository, "namedJdbcTemplate", namedJdbcTemplate, NamedParameterJdbcOperations.class)
    }

    void "call validate offer ids when exception is thrown"() {
        given:
        namedJdbcTemplate.queryForObject(_ as String, _ as MapSqlParameterSource, Integer.class) >> 1
        when:
        repository.validateOfferIds(["test"])
        then:
        thrown(OfferAlreadyUsedException.class)
    }

    void "call validate offer ids "() {
        given:
        namedJdbcTemplate.queryForObject(_ as String, _ as MapSqlParameterSource, Integer.class) >> 0
        when:
        repository.validateOfferIds(["test"])
        then:
        notThrown(OfferAlreadyUsedException.class)
    }

    void "call save tickets "() {
        given:
        simpleJdbcInsert.executeBatch(_ as MapSqlParameterSource[]) >> [1]
        when:
        repository.validateOfferIds(["test"])
        then:
        noExceptionThrown()
    }
}
