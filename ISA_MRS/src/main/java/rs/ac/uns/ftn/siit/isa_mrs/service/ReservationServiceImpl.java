package rs.ac.uns.ftn.siit.isa_mrs.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto.ReportDtos.AddInstructorReportDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto.ReportDtos.AddReportDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto.ReviewDtos.AddInstructorReviewDto;
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
    private final SpecialOfferRepo specialOfferRepo;
    private final ProfitFeeRepo profitFeeRepo;
    private final EmailSenderService emailSenderService;
    private final ModelMapper modelMapper;

    @Override
    public ResponseEntity<Void> bookSpecialOffer(String token, Long offerId, double total) {
        try {
            JwtDecoder.DecodedToken decodedToken;
            try {
                decodedToken = jwtDecoder.decodeToken(token);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            Optional<Client> cli = clientRepo.findByEmail(decodedToken.getEmail());
            Optional<SpecialOffer> specialOffer = specialOfferRepo.findById(offerId);
            if(specialOffer.isEmpty() || cli.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            Client client = cli.get();
            SpecialOffer offer = specialOffer.get();
            Reservation reservation = new Reservation();
            reservation.setPrice(offer.getRentalObject().getPrice());
            reservation.setPeople(offer.getCapacity());
            reservation.setCancelled(false);
            reservation.setEquipmentRequired(false);
            LocalDateTime time = LocalDateTime.now();
            reservation.setTimeStamp(time);
            reservation.setInitDate(offer.getInitDate());
            reservation.setTermDate(offer.getTermDate());
            reservation.setRentalObject(offer.getRentalObject());
            reservation.setClient(client);
            reservation.setSpecialOffer(offer);

            reservationRepo.save(reservation);
            offer.getReservations().add(reservation);
            specialOfferRepo.save(offer);

            Optional<ProfitFee> proFee = profitFeeRepo.findProfitFeeByRentalObjectType(offer.getRentalObject().getRentalObjectType());
            if(proFee.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            ProfitFee profitFee = proFee.get();
            Income income = new Income();
            income.setTimeStamp(time);
            income.setFee(profitFee.getValue());
            income.setValue(total/100 * profitFee.getValue());
            income.setReservation(reservation);

            incomeRepo.save(income);

            emailSenderService.sendSuccessfulReservationEmail(reservation);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

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
                else i.setValue(feeAmount/100 * i.getFee());
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
                    ReviewType.OwnerReview, reservation, client, false);
            reservation.getReviews().add(ownerReview);
            Review rentalReview = setUpReviewData(ard.getRentalReview().getGrade(), ard.getRentalReview().getComment(),
                    ReviewType.RentalReview, reservation, client, false);
            reservation.getReviews().add(rentalReview);
            reviewRepo.save(ownerReview);
            reviewRepo.save(rentalReview);
            reservationRepo.save(reservation);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Void> addInstructorReview(AddInstructorReviewDto aird, String token) {
        try {
            JwtDecoder.DecodedToken decodedToken;
            try {
                decodedToken = jwtDecoder.decodeToken(token);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            Optional<Reservation> res = reservationRepo.findById(aird.getReservationId());
            Optional<RentalObjectOwner> roo = rentalObjectOwnerRepo.findByEmail(decodedToken.getEmail());
            if (res.isEmpty() || roo.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            Reservation reservation = res.get();
            RentalObjectOwner owner = roo.get();
            Review clientReview = setUpReviewData(aird.getClientReview().getGrade(), aird.getClientReview().getComment(),
                    ReviewType.ClientReview, reservation, owner, true);
            reservation.getReviews().add(clientReview);
            reviewRepo.save(clientReview);
            reservationRepo.save(reservation);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private @NotNull Review setUpReviewData(int grade, String comment, ReviewType type, Reservation reservation,
                                            User user, Boolean isAccepted) {
        Review review = new Review();
        review.setGrade(grade);
        review.setComment(comment);
        review.setReviewType(type);
        if(isAccepted) review.setStatus(RequestStatus.Accepted);
        else review.setStatus(RequestStatus.Pending);
        review.setReservation(reservation);
        review.setTimeStamp(LocalDateTime.now());
        review.setAuthor(user);
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

    @Override
    public long countReservationsLastYear() {
        return reservationRepo.findAllByTimeStampBetween(LocalDateTime.now().minusYears(1), LocalDateTime.now()).size();
    }

    @Override
    public long countReservationsLastMonth() {
        return reservationRepo.findAllByTimeStampBetween(LocalDateTime.now().minusMonths(1), LocalDateTime.now()).size();
    }

    @Override
    public long countReservationsLastWeek() {
        return reservationRepo.findAllByTimeStampBetween(LocalDateTime.now().minusWeeks(1), LocalDateTime.now()).size();
    }

    @Override
    public long countReservations() {
        return reservationRepo.findAll().size();
    }

    public ResponseEntity<Void> addInstructorReport(AddInstructorReportDto aird, String token) {
        try {
            JwtDecoder.DecodedToken decodedToken;
            try {
                decodedToken = jwtDecoder.decodeToken(token);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            Optional<Reservation> res = reservationRepo.findById(aird.getReservationId());
            Optional<RentalObjectOwner> roo = rentalObjectOwnerRepo.findByEmail(decodedToken.getEmail());
            if (res.isEmpty() || roo.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            Reservation reservation = res.get();
            RentalObjectOwner owner = roo.get();
            Report report = new Report();
            report.setComment(aird.getComment());
            report.setTimeStamp(LocalDateTime.now());
            report.setReservation(reservation);
            report.setAuthor(owner);
            report.setShowedUp(aird.getShowedUp());
            if(aird.getShowedUp()) {
                report.setStatus(RequestStatus.Pending);
            }
            else {
                report.setStatus(RequestStatus.Accepted);
            }
            reportRepo.save(report);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
