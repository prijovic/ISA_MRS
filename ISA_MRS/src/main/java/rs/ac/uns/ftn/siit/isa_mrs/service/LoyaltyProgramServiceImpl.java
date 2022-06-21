package rs.ac.uns.ftn.siit.isa_mrs.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.AdminDtos.LoyaltyCategoryDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.AdminDtos.LoyaltyProgramDto;
import rs.ac.uns.ftn.siit.isa_mrs.model.LoyaltyCategory;
import rs.ac.uns.ftn.siit.isa_mrs.model.LoyaltyProgram;
import rs.ac.uns.ftn.siit.isa_mrs.model.RentalObjectOwner;
import rs.ac.uns.ftn.siit.isa_mrs.repository.LoyaltyProgramCategoryRepo;
import rs.ac.uns.ftn.siit.isa_mrs.repository.LoyaltyProgramRepo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoyaltyProgramServiceImpl implements LoyaltyProgramService{
    private final LoyaltyProgramRepo loyaltyProgramRepo;
    private final LoyaltyProgramCategoryRepo loyaltyProgramCategoryRepo;
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

    @Override
    public ResponseEntity<LoyaltyProgramDto> deleteCategory(Long id) {
        try {
            loyaltyProgramCategoryRepo.delete(loyaltyProgramCategoryRepo.getById(id));
            return getLoyaltyProgram();
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<LoyaltyProgramDto> updateProgram(long clientPointsPerReservation, long ownerPointsPerReservation) {
        try {
            Collection<LoyaltyProgram> loyaltyPrograms = loyaltyProgramRepo.findAll();
            if (loyaltyPrograms.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            LoyaltyProgram program = loyaltyPrograms.iterator().next();
            program.setClientPointsPerReservation(clientPointsPerReservation);
            program.setOwnerPointsPerReservation(ownerPointsPerReservation);
            loyaltyProgramRepo.save(program);
            return getLoyaltyProgram();
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<LoyaltyCategoryDto> addCategory(rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto.LoyaltyCategoryDto category) {
        try {
            Collection<LoyaltyProgram> loyaltyPrograms = loyaltyProgramRepo.findAll();
            if (loyaltyPrograms.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            LoyaltyProgram program = loyaltyPrograms.iterator().next();
            LoyaltyCategory category1 = modelMapper.map(category, LoyaltyCategory.class);
            program.getLoyaltyCategories().add(category1);
            loyaltyProgramRepo.save(program);
            category1.setLoyaltyProgram(program);
            loyaltyProgramCategoryRepo.save(category1);
            return new ResponseEntity<>(modelMapper.map(category1, LoyaltyCategoryDto.class), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Void> addCategoryPhoto(Long id, String photo) {
        try {
            Optional<LoyaltyCategory> category = loyaltyProgramCategoryRepo.findById(id);
            if (category.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            LoyaltyCategory category1 = category.get();
            category1.setIcon(photo);
            loyaltyProgramCategoryRepo.save(category1);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public double getOwnersBenefit(RentalObjectOwner owner) {
        try {
            Collection<LoyaltyProgram> loyaltyPrograms = loyaltyProgramRepo.findAll();
            if (loyaltyPrograms.isEmpty()) {
                return 0;
            }
            return loyaltyPrograms.iterator().next().getUserCategory(owner.getPoints()).getOwnerBenefit();
        } catch (Exception e) {
            log.error(e.getMessage());
            return 0;
        }
    }
}
