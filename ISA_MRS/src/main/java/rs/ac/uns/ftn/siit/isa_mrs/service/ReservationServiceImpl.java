package rs.ac.uns.ftn.siit.isa_mrs.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto.ReportDtos.AddReportDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto.ReviewDtos.AddReviewDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.ReservationDto;
import rs.ac.uns.ftn.siit.isa_mrs.model.*;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RequestStatus;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.ReviewType;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.UserType;
import rs.ac.uns.ftn.siit.isa_mrs.repository.*;
import rs.ac.uns.ftn.siit.isa_mrs.security.JwtDecoder;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private final JwtDecoder jwtDecoder;
    private final UserRepo userRepo;
    private final ClientRepo clientRepo;
    private final RentalObjectOwnerRepo rentalObjectOwnerRepo;
    private final RentalObjectRepo rentalObjectRepo;
    private final ReservationRepo reservationRepo;
    private final ReviewRepo reviewRepo;
    private final ReportRepo reportRepo;
    private final IncomeRepo incomeRepo;
    private final ModelMapper modelMapper;

    // INSERT INTO income (id, fee, time_stamp, value, reservation_id) VALUES (1, 5, '2022-07-01 12:23:34', 731.5, 1);
    @Override
    public ResponseEntity<Void> cancelReservation(Long id, double feeAmount) {
        try {
            Optional<Reservation> reservation = reservationRepo.findById(id);
            if(reservation.isPresent()) {
                Reservation r = reservation.get();
                r.setCancelled(true);
                Optional<Income> income = incomeRepo.findByReservationId(id);
                if(income.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                Income i = income.get();
                i.setTimeStamp(LocalDateTime.now());
                if(feeAmount == 0) i.setValue(feeAmount);
                else i.setValue(feeAmount - (feeAmount/100 * i.getFee()));
                incomeRepo.save(i);
                reservationRepo.save(r);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Collection<ReservationDto>> getFutureReservations(String token) {
        JwtDecoder.DecodedToken decodedToken;
        try {
            decodedToken = jwtDecoder.decodeToken(token);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        try {
            Collection<ReservationDto> result = new ArrayList<>();
            Optional<User> user = userRepo.findByEmail(decodedToken.getEmail());
            if (user.isEmpty()) {
                throw new Exception();
            }
            Collection<Reservation> reservations = getUsersFutureReservations(user.get());
            if (reservations == null) {
                throw new Exception();
            }
            reservations.forEach(reservation -> result.add(modelMapper.map(reservation, ReservationDto.class)));
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private Collection<Reservation> getUsersFutureReservations(User user) {
        if (user.getUserType().equals(UserType.Client)) {
            Client client = clientRepo.getById(user.getId());
            return client.getReservations();
        } else if (user.getUserType().equals(UserType.VacationRentalOwner) ||
                user.getUserType().equals(UserType.BoatOwner) ||
                user.getUserType().equals(UserType.Instructor)) {
            Collection<Reservation> reservations = new HashSet<>();
            RentalObjectOwner owner = rentalObjectOwnerRepo.getById(user.getId());
            Collection<RentalObject> rentalObjects = rentalObjectRepo.findAllByRentalObjectOwner(owner);
            rentalObjects.forEach(rentalObject -> {
                rentalObject.getReservations().forEach(reservation ->  {
                    if (reservation.getInitDate().isAfter(ChronoLocalDateTime.from(LocalDateTime.now())) ||
                            reservation.getTermDate().isAfter(ChronoLocalDateTime.from(LocalDateTime.now()))){
                        reservations.add(reservation);
                    }
                });
            });
            return reservations;
        } else {
            return null;
        }
    }

    @Override
    public ResponseEntity<Void> addReview(AddReviewDto ard, String token) {
        try {
            JwtDecoder.DecodedToken decodedToken;
            try {
                decodedToken = jwtDecoder.decodeToken(token);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            Optional<Reservation> res = reservationRepo.findById(ard.getReservationId());
            Optional<Client> cli = clientRepo.findByEmail(decodedToken.getEmail());
            if (res.isEmpty() || cli.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            Reservation reservation = res.get();
            Client client = cli.get();
            Review ownerReview = setUpReviewData(ard.getOwnerReview().getGrade(), ard.getOwnerReview().getComment(),
                    ReviewType.OwnerReview, reservation, client);
            reservation.getReviews().add(ownerReview);
            Review rentalReview = setUpReviewData(ard.getRentalReview().getGrade(), ard.getRentalReview().getComment(),
                    ReviewType.RentalReview, reservation, client);
            reservation.getReviews().add(rentalReview);
            reviewRepo.save(ownerReview);
            reviewRepo.save(rentalReview);
            reservationRepo.save(reservation);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private @NotNull Review setUpReviewData(int grade, String comment, ReviewType type, Reservation reservation,
                                            Client client) {
        Review review = new Review();
        review.setGrade(grade);
        review.setComment(comment);
        review.setReviewType(type);
        review.setStatus(RequestStatus.Pending);
        review.setReservation(reservation);
        review.setTimeStamp(LocalDateTime.now());
        review.setAuthor(client);
        return review;
    }

    @Override
    public ResponseEntity<Void> addReport(AddReportDto ard, String token) {
        try {
            JwtDecoder.DecodedToken decodedToken;
            try {
                decodedToken = jwtDecoder.decodeToken(token);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            Optional<Reservation> res = reservationRepo.findById(ard.getReservationId());
            Optional<Client> cli = clientRepo.findByEmail(decodedToken.getEmail());
            if (res.isEmpty() || cli.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            Reservation reservation = res.get();
            Client client = cli.get();
            Report report = new Report();
            report.setComment(ard.getComment());
            report.setTimeStamp(LocalDateTime.now());
            report.setReservation(reservation);
            report.setAuthor(client);
            report.setStatus(RequestStatus.Pending);
            reportRepo.save(report);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
