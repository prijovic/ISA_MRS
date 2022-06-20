package rs.ac.uns.ftn.siit.isa_mrs.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.AdminDtos.LoyaltyCategoryDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.AdminDtos.LoyaltyProgramDto;
import rs.ac.uns.ftn.siit.isa_mrs.model.LoyaltyProgram;
import rs.ac.uns.ftn.siit.isa_mrs.repository.LoyaltyProgramRepo;

import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoyaltyProgramServiceImpl implements LoyaltyProgramService{
    private final LoyaltyProgramRepo loyaltyProgramRepo;
    private final ModelMapper modelMapper;

    @Override
    public ResponseEntity<Collection<LoyaltyCategoryDto>> getLoyaltyCategories() {
        try {
            Collection<LoyaltyProgram> loyaltyPrograms = loyaltyProgramRepo.findAll();
            Collection<LoyaltyCategoryDto> loyaltyCategoryDtos = new ArrayList<>();
            if (loyaltyPrograms.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            loyaltyPrograms.iterator().next().getLoyaltyCategories().forEach(loyaltyCategory -> {
                loyaltyCategoryDtos.add(modelMapper.map(loyaltyCategory, LoyaltyCategoryDto.class));
            });
            return new ResponseEntity<>(loyaltyCategoryDtos, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<LoyaltyProgramDto> getLoyaltyProgram() {
        try {
            Collection<LoyaltyProgram> loyaltyPrograms = loyaltyProgramRepo.findAll();
            if (loyaltyPrograms.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(modelMapper.map(loyaltyPrograms.iterator().next(), LoyaltyProgramDto.class), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
