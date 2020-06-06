package ticket.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Offer is already in use")
public class OfferAlreadyUsedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public OfferAlreadyUsedException(String message) {
        super(message);
    }
}
