package rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto.ReviewDtos;

import lombok.Data;

import java.io.Serializable;

@Data
public class AddInstructorReviewDto implements Serializable {
    private Long reservationId;
    private ReviewInfoDto clientReview;
}

