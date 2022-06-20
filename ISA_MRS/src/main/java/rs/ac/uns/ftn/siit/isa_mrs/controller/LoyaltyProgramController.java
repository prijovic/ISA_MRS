package rs.ac.uns.ftn.siit.isa_mrs.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.AdminDtos.LoyaltyCategoryDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.AdminDtos.LoyaltyProgramDto;
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
}
