package rs.ac.uns.ftn.siit.isa_mrs.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidParam extends ResponseStatusException {
    public InvalidParam(HttpStatus status, String param) {
        super(status, "Invalid param " + param);
    }

}
