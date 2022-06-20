package rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class LoyaltyCategoryDto implements Serializable {
    private String name;
    private Integer requiredPoints;
    private Double clientDiscount;
    private Double ownerBenefit;
    private LocalDateTime lastEdited = LocalDateTime.now();
}
