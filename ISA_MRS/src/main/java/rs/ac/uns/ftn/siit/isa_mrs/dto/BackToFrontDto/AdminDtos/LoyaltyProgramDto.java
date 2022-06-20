package rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.AdminDtos;

import lombok.Data;

import java.io.Serializable;
import java.util.Collection;

@Data
public class LoyaltyProgramDto implements Serializable {
    private Long clientPointsPerReservation;
    private Long ownerPointsPerReservation;
    private Collection<LoyaltyCategoryDto> loyaltyCategories;
}
