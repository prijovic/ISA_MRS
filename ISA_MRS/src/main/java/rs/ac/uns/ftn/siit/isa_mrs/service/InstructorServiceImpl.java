package rs.ac.uns.ftn.siit.isa_mrs.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.InstructorDtos.InstructorMenu.InstructorsForMenuDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.InstructorDtos.InstructorProfile.InstructorProfileDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.RentalProfileDtos.AdventureDtos.AdventuresForMenuDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.RentalProfileDtos.ReviewDtos.ReviewDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.PageDto;
import rs.ac.uns.ftn.siit.isa_mrs.model.*;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RequestStatus;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.ReviewType;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.UserType;
import rs.ac.uns.ftn.siit.isa_mrs.repository.RentalObjectOwnerRepo;
import rs.ac.uns.ftn.siit.isa_mrs.repository.RentalObjectRepo;
import rs.ac.uns.ftn.siit.isa_mrs.security.JwtDecoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class InstructorServiceImpl implements InstructorService{

    private final RentalObjectOwnerRepo ownerRepo;
    private final RentalObjectRepo rentalRepo;
    private final AdventureServiceImpl adventureService;
    private final RentalObjectServiceImpl rosi;
    private final ModelMapper modelMapper;
    private final JwtDecoder jwtDecoder;

    @Override
    public ResponseEntity<InstructorProfileDto> getInstructor(Long id) {
        try{
            Optional<RentalObjectOwner> owner = ownerRepo.findById(id);
            if(owner.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            InstructorProfileDto instructorDto = modelMapper.map(owner, InstructorProfileDto.class);
            RentalObjectOwner instructor = owner.get();
            instructorDto.setReviews(getOwnerReviews(instructor));
            instructorDto.setGrade(rosi.calculateOwnerRating(instructor));
            instructorDto.setRentalObjects(setUpAdventures(instructor.getRentalObjects()));
            return new ResponseEntity<>(instructorDto, HttpStatus.OK);
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private Collection<AdventuresForMenuDto> setUpAdventures(Collection<RentalObject> adventures) {
        Collection<AdventuresForMenuDto> adventureDtos = new ArrayList<>();
        for(var adventure : adventures)
            adventureDtos.add(adventureService.setUpMenuDto((Adventure)adventure));
        return adventureDtos;
    }

    private Collection<ReviewDto> getOwnerReviews(RentalObjectOwner owner) {
        Collection<RentalObject> rentals = rentalRepo.findAllByRentalObjectOwner(owner);
        Collection<ReviewDto> reviews = new ArrayList<>();
        for(var rental : rentals) {
            for(var reservation : rental.getReservations()) {
                for(var review : reservation.getReviews())
                    if(review.getReviewType() == ReviewType.OwnerReview && review.getAuthor().getIsActive() && review.getStatus() == RequestStatus.Accepted)
                        reviews.add(modelMapper.map(review, ReviewDto.class));
            }
        }
        return reviews;
    }

    @Override
    public ResponseEntity<PageDto<InstructorsForMenuDto>> findInstructorsWithPaginationSortedByField(
            int offset, int pageSize, String field) {
        PageDto<InstructorsForMenuDto> result = new PageDto<>();
        try{
            Pageable pageable = PageRequest.of(offset, pageSize).withSort(Sort.by(field));
            Page<RentalObjectOwner> instructorsPage = ownerRepo.findAllByUserType(UserType.Instructor, pageable);
            Collection<InstructorsForMenuDto> instructorDtos = new ArrayList<>();
            instructorsPage.getContent().forEach(instructor ->
                    instructorDtos.add(setUpMenuDto(instructor)));
            result.setContent(instructorDtos);
            result.setPages(instructorsPage.getTotalPages());
            result.setCurrentPage(instructorsPage.getNumber() + 1);
            result.setPageSize(instructorsPage.getSize());
            if (instructorsPage.getContent().isEmpty()) {
                return new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
            }
            else {
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }

    private @NotNull InstructorsForMenuDto setUpMenuDto(RentalObjectOwner owner) {
        InstructorsForMenuDto instructorDto = modelMapper.map(owner, InstructorsForMenuDto.class);
        instructorDto.setGrade(rosi.calculateOwnerRating(owner));
        return instructorDto;
    }
}
