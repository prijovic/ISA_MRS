package rs.ac.uns.ftn.siit.isa_mrs.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.AdminDtos.LoyaltyCategoryDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.AdminDtos.LoyaltyProgramDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto.RentalPhotosDto;
import rs.ac.uns.ftn.siit.isa_mrs.service.LoyaltyProgramService;

import java.util.Collection;

import static rs.ac.uns.ftn.siit.isa_mrs.util.Paths.CROSS_ORIGIN;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(CROSS_ORIGIN)
@RequestMapping("/LoyaltyProgram")
public class LoyaltyProgramController {
    private final LoyaltyProgramService loyaltyProgramService;

    @GetMapping("/getCategories")
    public ResponseEntity<Collection<LoyaltyCategoryDto>> getCategories() {
        return loyaltyProgramService.getLoyaltyCategories();
    }

    @GetMapping("/getProgram")
    public ResponseEntity<LoyaltyProgramDto> getProgram() {
        return loyaltyProgramService.getLoyaltyProgram();
    }

    @DeleteMapping("/deleteCategory")
    public ResponseEntity<LoyaltyProgramDto> deleteCategory(@RequestParam Long id) {
        return loyaltyProgramService.deleteCategory(id);
    }

    @PostMapping("/addCategory")
    public ResponseEntity<LoyaltyCategoryDto> addCategory(@RequestBody rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto.LoyaltyCategoryDto category) {
        return loyaltyProgramService.addCategory(category);
    }

    @PostMapping("/connectPhotoToCategory")
    public ResponseEntity<Void> connectPhotosToRental(@RequestBody RentalPhotosDto dto) {
        return loyaltyProgramService.addCategoryPhoto(dto.getId(), dto.getPhotos().get(0));
    }

    @PutMapping("/updateProgram")
    public ResponseEntity<LoyaltyProgramDto> updateProgram(@RequestParam long clientPointsPerReservation, @RequestParam long ownerPointsPerReservation) {
        return loyaltyProgramService.updateProgram(clientPointsPerReservation, ownerPointsPerReservation);
    }
}
