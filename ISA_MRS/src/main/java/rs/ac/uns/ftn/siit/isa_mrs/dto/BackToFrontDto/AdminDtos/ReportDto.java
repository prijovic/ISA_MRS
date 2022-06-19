package rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.AdminDtos;

import lombok.Data;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.RentalProfileDtos.ReviewDtos.ReviewUserDto;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ReportDto implements Serializable {
    private Long id;
    private String comment;
    private LocalDateTime timeStamp;
    private String subjectName;
    private ReviewUserDto author;
}
