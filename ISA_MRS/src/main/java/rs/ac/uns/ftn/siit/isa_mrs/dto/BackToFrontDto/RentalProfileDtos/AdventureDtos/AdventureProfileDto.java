package rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.RentalProfileDtos.AdventureDtos;

import lombok.Data;
import rs.ac.uns.ftn.siit.isa_mrs.dto.*;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.RentalProfileDtos.ReviewDtos.ReviewDto;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RentalObjectType;

import java.io.Serializable;
import java.util.Collection;

@Data
public class AdventureProfileDto implements Serializable {
    private Long id;
    private String name;
    private RentalObjectType rentalObjectType;
    private String description;
    private Collection<PhotoDto> photos;
    private Integer capacity;
    private Double price;
    private Collection<AdditionalServiceDto> additionalServices;
    private Collection<ConductRuleDto> conductRules;
    private Collection<SpecialOfferDto> specialOffers;
    private RentalObjectOwnerDto rentalObjectOwner;
    private AddressDto address;
    private Double duration;
    private Collection<AdventureEquipmentDto> adventureEquipment;
    private Boolean isUserSubscribed = false;
    private PageDto<ReviewDto> reviews;
    private String grade = null;
    private String ownerGrade = null;
    private Boolean isDeletable;
    private TimePeriodDto availabilityPeriod;
}
