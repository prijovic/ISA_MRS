package rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.VacationRentalOwnerDtos;

import lombok.Data;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.InstructorDtos.InstructorDtos.SubscriberDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.RentalProfileDtos.ReviewDtos.ReviewDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto.AddressDto;

import java.io.Serializable;
import java.util.Collection;

@Data
public class VacationRentalOwnerProfileDto implements Serializable {
    private Long id;
    private String photo;
    private String email;
    private String name;
    private String surname;
    private String phone;
    private AddressDto address;
    private Integer points;
    private String grade;
    private Collection<ReviewDto> clientReviews;
    private Collection<SubscriberDto> subscribers;
}
