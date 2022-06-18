package rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.ReservationDtos;

import lombok.Data;

import java.io.Serializable;

@Data
public class ReservationAdditionalServiceDto implements Serializable {
    private Long id;
    private String name;
    private Double price;
}
