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
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.RentalProfileDtos.BoatDtos.BoatsForMenuDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.RentalProfileDtos.VacationRentalDtos.VacationRentalsForMenuDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BoatDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.PageDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.PhotoDto;
import rs.ac.uns.ftn.siit.isa_mrs.model.Boat;
import rs.ac.uns.ftn.siit.isa_mrs.model.Client;
import rs.ac.uns.ftn.siit.isa_mrs.model.Photo;
import rs.ac.uns.ftn.siit.isa_mrs.model.VacationRental;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RentalObjectType;
import rs.ac.uns.ftn.siit.isa_mrs.repository.BoatRepo;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.RentalProfileDtos.BoatDtos.BoatProfileDto;
import rs.ac.uns.ftn.siit.isa_mrs.repository.ClientRepo;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class BoatServiceImpl implements BoatService{

    private final BoatRepo boatRepo;
    private final ModelMapper modelMapper;
    private final RentalObjectServiceImpl rentalService;
    private final ClientRepo clientRepo;

    @Override
    public ResponseEntity<BoatProfileDto> getBoat(Long id, String email) {
        try{
            Optional<Boat> rental = boatRepo.findById(id);
            if (rental.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            BoatProfileDto boatDto = modelMapper.map(rental, BoatProfileDto.class);
            Boat boat = rental.get();
            boatDto.setReviews(rentalService.getRentalReviews(boat));
            Optional<Client> optionalClient = clientRepo.findByEmail(email);
            if(optionalClient.isPresent()){
                Client client = optionalClient.get();
                if(boat.getSubscribers().contains(client)) boatDto.setIsUserSubscribed(true);
            }
            return new ResponseEntity<>(boatDto, HttpStatus.OK);
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<PageDto<BoatsForMenuDto>> findBoatsWithPaginationSortedByField(int offset, int pageSize,
                                                                                 String field) {
        PageDto<BoatsForMenuDto> result = new PageDto<>();
        try{
            Pageable pageable = PageRequest.of(offset, pageSize).withSort(Sort.by(field));
            Page<Boat> boatsPage = boatRepo.findByRentalObjectType(RentalObjectType.Boat, pageable);
            Collection<BoatsForMenuDto> boatDtos = new ArrayList<>();
            boatsPage.getContent().forEach(boat -> boatDtos.add(setUpMenuDto(boat)));
            result.setContent(boatDtos);
            result.setPages(boatsPage.getTotalPages());
            result.setCurrentPage(boatsPage.getNumber() + 1);
            result.setPageSize(boatsPage.getSize());
            if (boatsPage.getContent().isEmpty()) {
                return new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
            }
            else {
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }

    private @NotNull BoatsForMenuDto setUpMenuDto(Boat boat) {
        BoatsForMenuDto boatDto = modelMapper.map(boat, BoatsForMenuDto.class);
        boatDto.setGrade(rentalService.calculateRentalRating(boat));
        if(boat.getPhotos().size() != 0) {
            Optional<Photo> photo = boat.getPhotos().stream().findFirst();
            photo.ifPresent(value -> boatDto.setDisplayPhoto(modelMapper.map(value, PhotoDto.class)));
        }
        return boatDto;
    }
}
