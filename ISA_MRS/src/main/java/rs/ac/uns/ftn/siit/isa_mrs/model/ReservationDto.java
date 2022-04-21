package rs.ac.uns.ftn.siit.isa_mrs.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Collection;

@Data
public class ReservationDto implements Serializable {
    private final Long id;
    private final Collection<ReviewDto> reviews;
    private final ClientDto client;
}
