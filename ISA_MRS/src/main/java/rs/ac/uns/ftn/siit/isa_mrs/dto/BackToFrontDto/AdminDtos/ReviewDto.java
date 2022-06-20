package rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.AdminDtos;

import lombok.Data;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.RentalProfileDtos.ReviewDtos.ReviewUserDto;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ReviewDto implements Serializable {
    private Long id;
    private String comment;
    private Integer grade;
    private LocalDateTime timeStamp;
    private ReviewUserDto author;
    private String subjectName;
}
