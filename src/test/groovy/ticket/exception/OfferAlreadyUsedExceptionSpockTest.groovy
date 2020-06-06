package ticket.exception

import spock.lang.Specification

class OfferAlreadyUsedExceptionSpockTest extends Specification {

    void "test Offer already in use exception"() {
        given: "error message to pass in exception"
        String msg = "Offer is already in use"

        when: "create exception using constructor"
        OfferAlreadyUsedException result = new OfferAlreadyUsedException(msg)

        then: "expect that created exception message is equal to passed message"
        result.message == msg
    }
}


