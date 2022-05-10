package rs.ac.uns.ftn.siit.isa_mrs.dto;

import lombok.Data;
import rs.ac.uns.ftn.siit.isa_mrs.dto.ClientDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.ReviewDto;

import java.io.Serializable;
import java.util.Collection;

@Data
public class ReservationDto implements Serializable {
    private Long id;
    private Collection<ReviewDto> reviews;
    private ClientDto client;
}
