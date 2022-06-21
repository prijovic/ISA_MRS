package rs.ac.uns.ftn.siit.isa_mrs.service;

import org.springframework.http.ResponseEntity;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.AdminDtos.LoyaltyCategoryDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.AdminDtos.LoyaltyProgramDto;
import rs.ac.uns.ftn.siit.isa_mrs.model.RentalObjectOwner;
import rs.ac.uns.ftn.siit.isa_mrs.repository.RentalObjectOwnerRepo;

import java.util.Collection;

public interface LoyaltyProgramService {
    ResponseEntity<Collection<LoyaltyCategoryDto>> getLoyaltyCategories();
    ResponseEntity<LoyaltyProgramDto> getLoyaltyProgram();
    ResponseEntity<LoyaltyProgramDto> deleteCategory(Long id);
    ResponseEntity<LoyaltyProgramDto> updateProgram(long clientPointsPerReservation, long ownerPointsPerReservation);
    ResponseEntity<LoyaltyCategoryDto> addCategory(rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto.LoyaltyCategoryDto category);
    ResponseEntity<Void> addCategoryPhoto(Long id, String photo);
    double getOwnersBenefit(RentalObjectOwner owner);
}
