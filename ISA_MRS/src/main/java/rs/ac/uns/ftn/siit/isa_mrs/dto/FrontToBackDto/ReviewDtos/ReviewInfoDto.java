package rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto.ReviewDtos;

import lombok.Data;

import java.io.Serializable;

@Data
public class ReviewInfoDto implements Serializable {
    private Integer grade;
    private String comment;
}
