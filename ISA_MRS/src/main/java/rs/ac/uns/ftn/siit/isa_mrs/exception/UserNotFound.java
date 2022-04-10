package rs.ac.uns.ftn.siit.isa_mrs.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserNotFound extends ResponseStatusException {

    public UserNotFound(HttpStatus status, String data) {
        super(status, "User with " + data + " + was not found");
    }
}
