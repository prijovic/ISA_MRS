package rs.ac.uns.ftn.siit.isa_mrs.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.BoatOwnerDtos.BoatOwnerProfileDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.InstructorDtos.InstructorDtos.SubscriberDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.RentalProfileDtos.ReviewDtos.ReviewDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.VacationRentalOwnerDtos.VacationRentalOwnerProfileDto;
import rs.ac.uns.ftn.siit.isa_mrs.model.Client;
import rs.ac.uns.ftn.siit.isa_mrs.model.RentalObject;
import rs.ac.uns.ftn.siit.isa_mrs.model.RentalObjectOwner;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RequestStatus;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.ReviewType;
import rs.ac.uns.ftn.siit.isa_mrs.repository.RentalObjectOwnerRepo;
import rs.ac.uns.ftn.siit.isa_mrs.repository.RentalObjectRepo;
import rs.ac.uns.ftn.siit.isa_mrs.security.JwtDecoder;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class RentalObjectOwnerServiceImpl implements RentalObjectOwnerService{
    private final RentalObjectOwnerRepo rentalObjectOwnerRepo;

    private final RentalObjectOwnerRepo ownerRepo;
    private final RentalObjectRepo rentalRepo;
    private final RentalObjectServiceImpl rosi;
    private final ModelMapper modelMapper;
    private final JwtDecoder jwtDecoder;

    @Override
    public long countRentalObjectOwners() {
        return rentalObjectOwnerRepo.findAllByIsActive(true).size();
    }

    @Override
    public ResponseEntity<BoatOwnerProfileDto> getBoatOwner(String token) {
        try{
            JwtDecoder.DecodedToken decodedToken;
            try {
                decodedToken = jwtDecoder.decodeToken(token);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            Optional<RentalObjectOwner> owner = ownerRepo.findByEmail(decodedToken.getEmail());
            if(owner.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            BoatOwnerProfileDto ownerDto = modelMapper.map(owner, BoatOwnerProfileDto.class);
            RentalObjectOwner boatOwner = owner.get();
            ownerDto.setGrade(rosi.calculateOwnerRating(boatOwner));
            ownerDto.setClientReviews(getOwnerReviews(boatOwner));
            ownerDto.setSubscribers(setUpSubscribers(boatOwner.getRentalObjects()));
            return new ResponseEntity<>(ownerDto, HttpStatus.OK);
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<VacationRentalOwnerProfileDto> getVacationRentalOwner(String token) {
        try{
            JwtDecoder.DecodedToken decodedToken;
            try {
                decodedToken = jwtDecoder.decodeToken(token);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            Optional<RentalObjectOwner> owner = ownerRepo.findByEmail(decodedToken.getEmail());
            if(owner.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            VacationRentalOwnerProfileDto ownerDto = modelMapper.map(owner, VacationRentalOwnerProfileDto.class);
            RentalObjectOwner boatOwner = owner.get();
            ownerDto.setGrade(rosi.calculateOwnerRating(boatOwner));
            ownerDto.setClientReviews(getOwnerReviews(boatOwner));
            ownerDto.setSubscribers(setUpSubscribers(boatOwner.getRentalObjects()));
            return new ResponseEntity<>(ownerDto, HttpStatus.OK);
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
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

    private Collection<SubscriberDto> setUpSubscribers(Collection<RentalObject> objects) {
        Collection<SubscriberDto> subscribers = new ArrayList<>();
        Set<Client> clients = new LinkedHashSet<>();
        for(var a : objects) {
            clients.addAll(a.getSubscribers());
        }
        clients.forEach(client -> subscribers.add(modelMapper.map(client, SubscriberDto.class)));
        return subscribers;
    }
}
