package rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.AdminDtos;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoyaltyCategoryDto implements Serializable {
    private Long id;
    private String name;
    private Integer requiredPoints;
    private Double clientDiscount;
    private Double ownerBenefit;
    private String icon;
}
