package rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.InstructorDtos.ClientPerspectiveInstructorDtos.InstructorProfile;

import lombok.Data;
import rs.ac.uns.ftn.siit.isa_mrs.dto.AddressDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.RentalProfileDtos.AdventureDtos.AdventuresForMenuDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.RentalProfileDtos.ReviewDtos.ReviewDto;

import java.io.Serializable;
import java.util.Collection;

@Data
public class ClientInstructorProfileDto implements Serializable {
    private Long id;
    private String photo;
    private String email;
    private String name;
    private String surname;
    private String phone;
    private AddressDto address;
    private Collection<AdventuresForMenuDto> rentalObjects;
    private Collection<ReviewDto> clientReviews;
    private String grade;
}
