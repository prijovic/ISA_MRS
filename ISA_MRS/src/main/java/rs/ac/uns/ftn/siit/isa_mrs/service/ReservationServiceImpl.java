package rs.ac.uns.ftn.siit.isa_mrs.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.siit.isa_mrs.dto.ReservationDto;
import rs.ac.uns.ftn.siit.isa_mrs.model.*;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.UserType;
import rs.ac.uns.ftn.siit.isa_mrs.repository.*;
import rs.ac.uns.ftn.siit.isa_mrs.security.JwtDecoder;

import java.time.LocalDate;
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
    private final ModelMapper modelMapper;

    @Override
    public ResponseEntity<Void> cancelReservation(Long id) {
        try {
            Optional<Reservation> reservation = reservationRepo.findById(id);
            if(reservation.isPresent()) {
                Reservation r = reservation.get();
                r.setCancelled(true);
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
                    if (reservation.getReservationTime().getInitDate().isAfter(LocalDate.now()) || reservation.getReservationTime().getTermDate().isAfter(LocalDate.now())){
                        reservations.add(reservation);
                    }
                });
            });
            return reservations;
        } else {
            return null;
        }
    }
}
