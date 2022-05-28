package rs.ac.uns.ftn.siit.isa_mrs.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.RentalProfileDtos.ReviewDtos.ReviewDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.RentalObjectPeriodsDto;
import rs.ac.uns.ftn.siit.isa_mrs.model.*;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.ReviewType;
import rs.ac.uns.ftn.siit.isa_mrs.repository.ClientRepo;
import rs.ac.uns.ftn.siit.isa_mrs.repository.RentalObjectRepo;
import rs.ac.uns.ftn.siit.isa_mrs.repository.TimePeriodRepo;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class RentalObjectServiceImpl implements RentalObjectService {
    private final RentalObjectRepo rentalObjectRepo;
    private final TimePeriodRepo timePeriodRepo;
    private final ClientRepo clientRepo;
    private final ModelMapper modelMapper;

    @Override
    public ResponseEntity<RentalObjectPeriodsDto> setAvailabilityPeriods(Long id, List<LocalDate> dates) {
        try {
            Optional<RentalObject> rentalObjectOptional = rentalObjectRepo.findById(id);
            if (rentalObjectOptional.isEmpty()) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            List<TimePeriod> periods = makePeriods(dates);
            RentalObject rentalObject = rentalObjectOptional.get();
            for (TimePeriod period:periods) {
                period.setRentalObject(rentalObject);
                timePeriodRepo.save(period);
            }
            rentalObject.setAvailabilityPeriods(periods);
            rentalObjectRepo.save(rentalObject);
            RentalObjectPeriodsDto rentalObjectPeriodsDto = modelMapper.map(rentalObject, RentalObjectPeriodsDto.class);
            return new ResponseEntity<>(rentalObjectPeriodsDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Void> addSubscriber(Long rentalId, String clientEmail) {
        try {
            log.info("Dodajemo subskrajbera");
            Optional<RentalObject> optionalRental = rentalObjectRepo.findById(rentalId);
            Optional<Client> optionalClient = clientRepo.findByEmail(clientEmail);
            if(optionalRental.isEmpty() || optionalClient.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            RentalObject rental = optionalRental.get();
            Client client = optionalClient.get();
            rental.getSubscribers().add(client);
            log.info("" + rental.getSubscribers());
            client.getSubscriptions().add(rental);
            log.info("" + client.getSubscriptions());
            rentalObjectRepo.save(rental);
            clientRepo.save(client);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Collection<ReviewDto> getRentalReviews(@NotNull RentalObject rental) {
        Collection<ReviewDto> reviews = new ArrayList<>();
        for(var reservation : rental.getReservations()) {
            for(var review : reservation.getReviews()) {
                if (review.getReviewType() == ReviewType.RentalReview && review.getAuthor().isActive())
                    reviews.add(modelMapper.map(review, ReviewDto.class));
            }
        }
        return reviews;
    }

    @Override
    public String calculateRentalRating(@NotNull RentalObject rental) {
        Collection<Reservation> reservations = rental.getReservations();
        if(reservations.isEmpty()) return null;
        double grade = getRentalGrade(reservations);
        return gradeFormatting(grade);
    }

    private List<TimePeriod> makePeriods(List<LocalDate> dates){
        List<TimePeriod> timePeriods = new ArrayList<>();
        dates.sort(Comparator.naturalOrder());
        for (int i = 0; i < dates.size() - 1; i++) {
            LocalDate start = dates.get(i);
            if (timePeriods.size()!=0 &&
                    (start.isBefore(timePeriods.get(timePeriods.size()-1).getTermDate()) ||
                    start.isEqual(timePeriods.get(timePeriods.size()-1).getTermDate()))){
                continue;
            }
            LocalDate end = dates.get(i);
            for (int j = i+1; j < dates.size(); j++) {
                if (ChronoUnit.DAYS.between(dates.get(i), dates.get(j)) == 1){
                    end = dates.get(j);
                }
                else {
                    break;
                }
            }
            TimePeriod timePeriod = new TimePeriod();
            timePeriod.setInitDate(start);
            timePeriod.setTermDate(end);
            timePeriods.add(timePeriod);
        }
        return timePeriods;
    }

    private String gradeFormatting(double grade) {
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);
        return df.format(grade);
    }

    private double getRentalGrade(Collection<Reservation> reservations) {
        double sumOfGrades = 0, amountOfGrades = 0;
        for(var reservation : reservations) {
            for(var review : reservation.getReviews())
                if(review.getReviewType() == ReviewType.RentalReview && review.getAuthor().isActive())
                { sumOfGrades += review.getGrade(); amountOfGrades++; }
        }
        if(amountOfGrades == 0) return 0;
        return sumOfGrades/amountOfGrades;
    }

}
