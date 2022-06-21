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
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.AdminDtos.GraphDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.AdminDtos.GraphNodeDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.InstructorDtos.RentalGradeDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.RentalProfileDtos.SpecialOfferDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.PageDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.RentalObjectDto;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RentalObjectType;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.RentalProfileDtos.ReviewDtos.ReviewDto;
import rs.ac.uns.ftn.siit.isa_mrs.model.*;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RequestStatus;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.ReviewType;
import rs.ac.uns.ftn.siit.isa_mrs.repository.*;
import rs.ac.uns.ftn.siit.isa_mrs.security.JwtDecoder;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAdjusters;
import java.time.chrono.ChronoLocalDateTime;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class RentalObjectServiceImpl implements RentalObjectService {
    private final RentalObjectRepo rentalObjectRepo;
    private final ClientRepo clientRepo;
    private final ModelMapper modelMapper;
    private final ReviewRepo reviewRepo;
    private final JwtDecoder jwtDecoder;
    private final RentalObjectOwnerRepo rentalObjectOwnerRepo;
    private final ReservationRepo reservationRepo;

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

    public Collection<SpecialOfferDto> getFutureSpecialOffers(Collection<SpecialOffer> specialOffers) {
        Collection<SpecialOfferDto> result = new ArrayList<>();
        for(var offer : specialOffers) {
            log.info(offer.getId()+"");
            log.info(offer.getInitDate()+"");
            if(offer.getInitDate().isBefore(ChronoLocalDateTime.from(LocalDateTime.now()))) continue;
            if(offer.getReservations().size() == 0) {result.add(modelMapper.map(offer, SpecialOfferDto.class)); continue;}
            int i=0;
            for(var reservation : offer.getReservations()) {
                if(reservation.getCancelled()) i++;
            }
            if(i == offer.getReservations().size()) result.add(modelMapper.map(offer, SpecialOfferDto.class));
        }
        return result;
    }

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
    public Collection<RentalGradeDto> getRentalsGrades(String token) {
        Collection<RentalGradeDto> result = new ArrayList<RentalGradeDto>();
        try {
            JwtDecoder.DecodedToken decodedToken;
            try {
                decodedToken = jwtDecoder.decodeToken(token);
            } catch (Exception e) {
                return result;
            }
            Optional<RentalObjectOwner> user = rentalObjectOwnerRepo.findByEmail(decodedToken.getEmail());
            if (user.isPresent()) {
                RentalObjectOwner owner = user.get();
                Collection<RentalObject> rentalObjects = rentalObjectRepo.findAllByRentalObjectOwner(owner);
                rentalObjects.forEach(rentalObject -> {
                    RentalGradeDto dto = new RentalGradeDto();
                    dto.setRentalObject(modelMapper.map(rentalObject, RentalObjectDto.class));
                    dto.setGrade(calculateRentalRating(rentalObject));
                    result.add(dto);
                });
            }
            return result;
        } catch (Exception e) {
            return new ArrayList<RentalGradeDto>();
        }
    }

    @Override
    public GraphDto getLastYearRentalReservationsGraph(String token) {
        JwtDecoder.DecodedToken decodedToken;
        GraphDto graph = new GraphDto();
        try {
            decodedToken = jwtDecoder.decodeToken(token);
        } catch (Exception e) {
            return null;
        }
        Optional<RentalObjectOwner> user = rentalObjectOwnerRepo.findByEmail(decodedToken.getEmail());
        if (user.isPresent()) {
            RentalObjectOwner owner = user.get();
            LocalDate date = LocalDate.now();
            int currentMonth = date.getMonthValue();
            for (int i = 0; i < 12; i++) {
                int calculatedMonth = ((currentMonth < 1) ? 12 + currentMonth : currentMonth);
                date = date.withMonth(calculatedMonth);
                LocalDate firstDay = date.withDayOfMonth(1);
                LocalDate lastDay = date.with(TemporalAdjusters.lastDayOfMonth());
                GraphNodeDto node = new GraphNodeDto();
                LocalDateTime start = LocalDateTime.of(firstDay, LocalTime.parse("00:00:00"));
                LocalDateTime end = LocalDateTime.of(lastDay, LocalTime.parse("00:00:00"));
                node.setValue(reservationRepo.findAllByTimeStampBetweenAndRentalObjectRentalObjectOwner(start, end, owner).size());
                node.setMonth(date.getMonth().getDisplayName(TextStyle.FULL, Locale.US));
                graph.getNodes().add(node);
                currentMonth--;
            }
        }
        return graph;
    }

    @Override
    public GraphDto getLastMonthRentalReservationsGraph(String token) {
        JwtDecoder.DecodedToken decodedToken;
        GraphDto graph = new GraphDto();
        try {
            decodedToken = jwtDecoder.decodeToken(token);
        } catch (Exception e) {
            return null;
        }
        Optional<RentalObjectOwner> user = rentalObjectOwnerRepo.findByEmail(decodedToken.getEmail());
        if (user.isPresent()) {
            RentalObjectOwner owner = user.get();
            LocalDate date = LocalDate.now();
            for (int i = 0; i < 31; i++) {
                GraphNodeDto node = new GraphNodeDto();
                LocalDateTime start = LocalDateTime.of(date, LocalTime.parse("00:00:00"));
                LocalDateTime end = LocalDateTime.of(date, LocalTime.parse("23:59:59"));
                node.setValue(reservationRepo.findAllByTimeStampBetweenAndRentalObjectRentalObjectOwner(start, end, owner).size());
                node.setMonth(date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy.")));
                graph.getNodes().add(node);
                date = date.minusDays(1);
            }
        }
        return graph;
    }

    @Override
    public GraphDto getLastWeekRentalReservationsGraph(String token) {
        JwtDecoder.DecodedToken decodedToken;
        GraphDto graph = new GraphDto();
        try {
            decodedToken = jwtDecoder.decodeToken(token);
        } catch (Exception e) {
            return null;
        }
        Optional<RentalObjectOwner> user = rentalObjectOwnerRepo.findByEmail(decodedToken.getEmail());
        if (user.isPresent()) {
            RentalObjectOwner owner = user.get();
            LocalDate date = LocalDate.now();
            for (int i = 0; i < 7; i++) {
                GraphNodeDto node = new GraphNodeDto();
                LocalDateTime start = LocalDateTime.of(date, LocalTime.parse("00:00:00"));
                LocalDateTime end = LocalDateTime.of(date, LocalTime.parse("23:59:59"));
                node.setValue(reservationRepo.findAllByTimeStampBetweenAndRentalObjectRentalObjectOwner(start, end, owner).size());
                node.setMonth(date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.US));
                graph.getNodes().add(node);
                date = date.minusDays(1);
            }
        }
        return graph;
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
