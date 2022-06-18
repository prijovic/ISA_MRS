package rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.RentalProfileDtos.ReviewDtos;

import lombok.Data;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.UserType;

import java.io.Serializable;

@Data
public class ReviewUserDto implements Serializable {
    private Long id;
    private String photo;
    private String name;
    private String surname;
    private boolean isActive;
    private UserType userType;
}
