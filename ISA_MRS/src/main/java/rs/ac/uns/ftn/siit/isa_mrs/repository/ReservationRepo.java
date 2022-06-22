package rs.ac.uns.ftn.siit.isa_mrs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.siit.isa_mrs.model.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

public interface ReservationRepo extends JpaRepository<Reservation, Long> {
    Collection<Reservation> findAllByClientId(Long id);
    Reservation findByReviewsIsContaining(Review review);
    Reservation findByReportsIsContaining(Report report);
    Collection<Reservation> findAllByRentalObjectRentalObjectOwner(RentalObjectOwner rentalObjectOwner);
    Collection<Reservation> findAllByTimeStampBetween(LocalDateTime start, LocalDateTime end);
    Collection<Reservation> findAllByTimeStampBetweenAndRentalObjectRentalObjectOwner(LocalDateTime start, LocalDateTime end, RentalObjectOwner owner);
}
