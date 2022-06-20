package rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.InstructorDtos.InstructorDtos;

import lombok.Data;
import rs.ac.uns.ftn.siit.isa_mrs.dto.AddressDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.RentalProfileDtos.ReviewDtos.ReviewDto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;

@Data
public class InstructorProfileDto implements Serializable {
    private Long id;
    private String photo;
    private String email;
    private String name;
    private String surname;
    private String phone;
    private Integer points;
    private AddressDto address;
    private String grade;
    private Collection<ReviewDto> clientReviews;
    private Collection<SubscriberDto> subscribers;
    private LocalDateTime initDate;
    private LocalDateTime termDate;
}
