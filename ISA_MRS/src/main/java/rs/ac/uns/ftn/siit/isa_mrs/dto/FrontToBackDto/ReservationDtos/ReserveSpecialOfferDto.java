package rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto.ReservationDtos;

import lombok.Data;

import java.io.Serializable;

@Data
public class ReserveSpecialOfferDto implements Serializable {
    private Long offerId;
    private double total;
}
