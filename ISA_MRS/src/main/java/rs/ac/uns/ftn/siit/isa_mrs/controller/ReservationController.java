package rs.ac.uns.ftn.siit.isa_mrs.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.siit.isa_mrs.dto.ReservationDto;
import rs.ac.uns.ftn.siit.isa_mrs.model.Reservation;
import rs.ac.uns.ftn.siit.isa_mrs.repository.UserRepo;
import rs.ac.uns.ftn.siit.isa_mrs.service.ReservationService;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static rs.ac.uns.ftn.siit.isa_mrs.util.Paths.CANCEL_RESERVATION;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/Reservations")
public class ReservationController {
    private final ReservationService reservationService;

    @GetMapping("/getReservations")
    public ResponseEntity<Collection<ReservationDto>> getFutureReservations(HttpServletRequest request) {
        return reservationService.getFutureReservations(request.getHeader(AUTHORIZATION));
    }

    @PutMapping(CANCEL_RESERVATION)
    public ResponseEntity<Void> cancelReservation(@RequestParam Long id) {
        return reservationService.cancelReservation(id);
    }
}
