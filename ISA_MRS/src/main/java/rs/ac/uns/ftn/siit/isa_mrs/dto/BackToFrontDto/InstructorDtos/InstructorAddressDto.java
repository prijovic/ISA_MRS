package rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.InstructorDtos;

import lombok.Data;

import java.io.Serializable;

@Data
public class InstructorAddressDto implements Serializable {
    private Long id;
    private String country;
    private String city;
}
