package ticket.exception;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OfferAlreadyUsedExceptionTest {

    @Test
    public void testConstructorWithMessage() {
        String msg = "Offer is already in use";
        OfferAlreadyUsedException offerNotFoundException = new OfferAlreadyUsedException(msg);
        assertEquals(offerNotFoundException.getMessage(), msg);
    }
}
