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
import rs.ac.uns.ftn.siit.isa_mrs.dto.PageDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.RentalObjectDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.SpecialOfferDto;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RentalObjectType;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.RentalProfileDtos.ReviewDtos.ReviewDto;
import rs.ac.uns.ftn.siit.isa_mrs.model.*;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RequestStatus;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.ReviewType;
import rs.ac.uns.ftn.siit.isa_mrs.repository.*;
import rs.ac.uns.ftn.siit.isa_mrs.security.JwtDecoder;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class RentalObjectServiceImpl implements RentalObjectService {
    private final RentalObjectRepo rentalObjectRepo;
    private final ClientRepo clientRepo;
    private final ModelMapper modelMapper;
    private final ReviewRepo reviewRepo;
    private final ServiceRepo serviceRepo;
    private final SpecialOfferRepo specialOfferRepo;
    private final JwtDecoder jwtDecoder;

//    @Override
//    public ResponseEntity<RentalObjectPeriodsDto> setAvailabilityPeriods(Long id, List<LocalDate> dates) {
//        try {
//            Optional<RentalObject> rentalObjectOptional = rentalObjectRepo.findById(id);
//            if (rentalObjectOptional.isEmpty()) {
//                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//            }
//            List<TimePeriod> periods = makePeriods(dates);
//            RentalObject rentalObject = rentalObjectOptional.get();
//            for (TimePeriod period:periods) {
//                period.setRentalObject(rentalObject);
//                timePeriodRepo.save(period);
//            }
//            rentalObject.setAvailabilityPeriods(periods);
//            rentalObjectRepo.save(rentalObject);
//            RentalObjectPeriodsDto rentalObjectPeriodsDto = modelMapper.map(rentalObject, RentalObjectPeriodsDto.class);
//            return new ResponseEntity<>(rentalObjectPeriodsDto, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @Override
    public ResponseEntity<PageDto<RentalObjectDto>> getRentalObjects(int page, int pageSize) {
        try{
            Pageable pageable = PageRequest.of(page, pageSize).withSort(Sort.by(Sort.Order.asc("name")));
            Page<RentalObject> rentalsPage = rentalObjectRepo.findAll(pageable);
            return new ResponseEntity<>(packRentals(rentalsPage), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Collection<RentalObjectDto>> changeRentalObjectsStatus(Collection<Long> ids) {
        Collection<Long> changedStatuses = new ArrayList<>();
        try {
            Collection<RentalObjectDto> result = new ArrayList<>();
            ids.forEach(id -> {
                RentalObject rentalObject = rentalObjectRepo.getById(id);
                rentalObject.setIsActive(!rentalObject.getIsActive());
                rentalObjectRepo.save(rentalObject);
                changedStatuses.add(id);
                result.add(mapRentalObjectToDto(rentalObject));
            });
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            changeRentalObjectsStatus(changedStatuses);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Void> addSubscriber(Long rentalId, String token) {
        try {
            JwtDecoder.DecodedToken decodedToken;
            try {
                decodedToken = jwtDecoder.decodeToken(token);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            Optional<RentalObject> optionalRental = rentalObjectRepo.findById(rentalId);
            Optional<Client> optionalClient = clientRepo.findByEmail(decodedToken.getEmail());
            if(optionalRental.isEmpty() || optionalClient.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            RentalObject rental = optionalRental.get();
            Client client = optionalClient.get();
            rental.getSubscribers().add(client);
            client.getSubscriptions().add(rental);
            rentalObjectRepo.save(rental);
            clientRepo.save(client);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Void> cancelSubscription(Long rentalId, String token) {
        try {
            JwtDecoder.DecodedToken decodedToken;
            try {
                decodedToken = jwtDecoder.decodeToken(token);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            Optional<RentalObject> optionalRental = rentalObjectRepo.findById(rentalId);
            Optional<Client> optionalClient = clientRepo.findByEmail(decodedToken.getEmail());
            if(optionalRental.isEmpty() || optionalClient.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            RentalObject rental = optionalRental.get();
            Client client = optionalClient.get();
            rental.getSubscribers().remove(client);
            client.getSubscriptions().remove(rental);
            rentalObjectRepo.save(rental);
            clientRepo.save(client);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<SpecialOfferDto> defineSpecialOffer(rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto.SpecialOfferDto specialOfferDto) {
        try {
            Optional<RentalObject> rentalObjectOptional = rentalObjectRepo.findById(specialOfferDto.getRentalObjectId());
            if (rentalObjectOptional.isEmpty()) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            RentalObject rentalObject = rentalObjectOptional.get();
            SpecialOffer specialOffer = new SpecialOffer();
            specialOffer.setCapacity(specialOfferDto.getCapacity());
            specialOffer.setDiscount(specialOfferDto.getDiscount());
            specialOffer.setInitDate(specialOfferDto.getInitDate());
            specialOffer.setTermDate(specialOfferDto.getTermDate());

            List<rs.ac.uns.ftn.siit.isa_mrs.model.Service> objectIncludedServices = new ArrayList<>();
            specialOfferDto.getIncludedServices().forEach(service ->{
                rs.ac.uns.ftn.siit.isa_mrs.model.Service includedServices1 = modelMapper.map(service, rs.ac.uns.ftn.siit.isa_mrs.model.Service.class);
                serviceRepo.save(includedServices1);
                objectIncludedServices.add(includedServices1);
            });
            specialOffer.setIncludedServices(objectIncludedServices);
            specialOffer.setRentalObject(rentalObject);
            specialOfferRepo.save(specialOffer);

            objectIncludedServices.forEach(service -> {
                service.setSpecialOffer(specialOffer);
                serviceRepo.save(service);
            });
            SpecialOfferDto specialOfferDto1 = modelMapper.map(specialOffer, SpecialOfferDto.class);
            return new ResponseEntity<>(specialOfferDto1, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<PageDto<RentalObjectDto>> getRentalObjects(int page, int pageSize, String filter) {
        try{
            Pageable pageable = PageRequest.of(page, pageSize).withSort(Sort.by(Sort.Order.asc("name")));
            Page<RentalObject> rentalsPage = rentalObjectRepo.findAllByRentalObjectType(RentalObjectType.valueOf(filter), pageable);
            return new ResponseEntity<>(packRentals(rentalsPage), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private PageDto<RentalObjectDto> packRentals(Page<RentalObject> rentalObjectPage) {
        PageDto<RentalObjectDto> result = new PageDto<>();
        Collection<RentalObjectDto> rentalsDtos = new ArrayList<>();
        rentalObjectPage.getContent().forEach(rentalObject -> rentalsDtos.add(mapRentalObjectToDto(rentalObject)));
        result.setContent(rentalsDtos);
        result.setPages(rentalObjectPage.getTotalPages());
        result.setCurrentPage(rentalObjectPage.getNumber());
        result.setPageSize(rentalObjectPage.getSize());
        return result;
    }

    private RentalObjectDto mapRentalObjectToDto(RentalObject rentalObject) {
        RentalObjectDto rentalObjectDto = modelMapper.map(rentalObject, RentalObjectDto.class);
        rentalObjectDto.setIsDeletable(rentalObject.getReservations().size() == 0);
        return rentalObjectDto;
    }

    private String gradeFormatting(double grade) {
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);
        return df.format(grade);
    }

    @Override
    public String calculateRentalRating(@NotNull RentalObject rental) {
        Collection<Reservation> reservations = rental.getReservations();
        if(reservations.isEmpty()) return "0";
        double grade = getRentalGrade(reservations);
        return gradeFormatting(grade);
    }

    @Override
    public String calculateOwnerRating(@NotNull RentalObjectOwner owner) {
        double grade = getOwnerGrade(owner);
        return gradeFormatting(grade);
    }

    private double getRentalGrade(Collection<Reservation> reservations) {
        double sumOfGrades = 0, amountOfGrades = 0;
        for(var reservation : reservations) {
            for(var review : reservation.getReviews())
                if(review.getReviewType() == ReviewType.RentalReview && review.getAuthor().getIsActive() && review.getStatus() == RequestStatus.Accepted)
                { sumOfGrades += review.getGrade(); amountOfGrades++; }
        }
        if(amountOfGrades == 0) return 0;
        return sumOfGrades/amountOfGrades;
    }

    public double getOwnerGrade(RentalObjectOwner owner) {
        double sumOfGrades = 0, amountOfGrades = 0;
        Collection<RentalObject> rentals = rentalObjectRepo.findAllByRentalObjectOwner(owner);
        for(var rental : rentals) {
            for(var reservation : rental.getReservations()) {
                for(var review : reservation.getReviews())
                    if(review.getReviewType() == ReviewType.OwnerReview && review.getAuthor().getIsActive() && review.getStatus() == RequestStatus.Accepted)
                    { sumOfGrades += review.getGrade(); amountOfGrades++; break;} } }
        if(amountOfGrades == 0) return 0;
        return sumOfGrades/amountOfGrades;
    }

    @Override
    public PageDto<ReviewDto> getRentalReviews(RentalObject rental, int page, int pageSize) {
        PageDto<ReviewDto> result = new PageDto<>();
        Collection<ReviewDto> reviewDtos = new ArrayList<>();
        Pageable pageable = PageRequest.of(page, pageSize).withSort(Sort.by(Sort.Order.desc("timeStamp")));
        Page<Review> reviewPage = reviewRepo.findAllByReservationRentalObjectAndAuthorIsActiveAndReviewTypeAndStatus(
                rental, true, ReviewType.RentalReview, pageable, RequestStatus.Accepted);
        reviewPage.getContent().forEach(review -> reviewDtos.add(modelMapper.map(review, ReviewDto.class)));
        result.setContent(reviewDtos);
        result.setPages(reviewPage.getTotalPages());
        result.setCurrentPage(reviewPage.getNumber());
        result.setPageSize(reviewPage.getSize());
        return result;
    }

}
