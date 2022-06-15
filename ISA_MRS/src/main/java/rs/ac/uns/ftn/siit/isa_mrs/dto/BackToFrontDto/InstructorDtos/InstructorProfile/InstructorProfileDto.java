package rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.InstructorDtos.InstructorProfile;

import lombok.Data;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.InstructorDtos.InstructorAddressDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.RentalProfileDtos.AdventureDtos.AdventuresForMenuDto;
import rs.ac.uns.ftn.siit.isa_mrs.model.Review;

import java.io.Serializable;
import java.util.Collection;

@Data
public class InstructorProfileDto implements Serializable {
    private Long id;
    private String photo;
    private String email;
    private String name;
    private String surname;
    private String phone;
    private InstructorAddressDto address;
    private Collection<AdventuresForMenuDto> rentalObjects;
    private Collection<Review> reviews;
}
