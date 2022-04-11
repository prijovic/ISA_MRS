package rs.ac.uns.ftn.siit.isa_mrs.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class RentalNotFound extends ResponseStatusException {

    public RentalNotFound(HttpStatus status, String data) {

        super(status, "Rental with " + data + " was not found");
    }
}
