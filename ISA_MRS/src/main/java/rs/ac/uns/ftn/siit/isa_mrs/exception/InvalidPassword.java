package rs.ac.uns.ftn.siit.isa_mrs.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidPassword extends ResponseStatusException {

    public InvalidPassword(HttpStatus status, String reason) {
        super(status, reason);
    }
}
