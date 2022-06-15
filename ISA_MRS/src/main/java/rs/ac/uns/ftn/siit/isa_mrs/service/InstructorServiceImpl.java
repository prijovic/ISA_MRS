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
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.RentalProfileDtos.VacationRentalDtos.VacationRentalProfileDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.PageDto;
import rs.ac.uns.ftn.siit.isa_mrs.model.Client;
import rs.ac.uns.ftn.siit.isa_mrs.model.RentalObjectOwner;
import rs.ac.uns.ftn.siit.isa_mrs.model.VacationRental;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.UserType;
import rs.ac.uns.ftn.siit.isa_mrs.repository.RentalObjectOwnerRepo;
import rs.ac.uns.ftn.siit.isa_mrs.security.JwtDecoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class InstructorServiceImpl implements InstructorService{

    private final RentalObjectOwnerRepo ownerRepo;
    private final RentalObjectServiceImpl rosi;
    private final ModelMapper modelMapper;
    private final JwtDecoder jwtDecoder;

    public ResponseEntity<InstructorProfileDto> getInstructor(Long id, int page, int pageSize, String token) {
        try{
            JwtDecoder.DecodedToken decodedToken;
            try {
                decodedToken = jwtDecoder.decodeToken(token);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            Optional<RentalObjectOwner> owner = ownerRepo.;
            if(owner.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            InstructorProfileDto instructorDto = modelMapper.map(owner, InstructorProfileDto.class);

            VacationRental vacationRental = rental.get();
            rentalDto.setReviews(rentalService.getRentalReviews(vacationRental, page, pageSize));
            rentalDto.setGrade(rentalService.calculateRentalRating(vacationRental));
            rentalDto.setOwnerGrade(rentalService.calculateOwnerRating(vacationRental.getRentalObjectOwner()));
            Optional<Client> optionalClient = clientRepo.findByEmail(decodedToken.getEmail());
            if(optionalClient.isPresent()){
                Client client = optionalClient.get();
                if(vacationRental.getSubscribers().contains(client)) rentalDto.setIsUserSubscribed(true);
            }
            return new ResponseEntity<>(rentalDto, HttpStatus.OK);
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
