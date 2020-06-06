package ticket.exception;

import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;

public class OfferAlreadyUsedExceptionNGTest {

    @Test
    public void testConstructorWithMessage() {
        String msg = "Offer already in use";
        OfferAlreadyUsedException offerAlreadyUsedException = new OfferAlreadyUsedException(msg);
        assertEquals(offerAlreadyUsedException.getMessage(), msg);
    }
}
