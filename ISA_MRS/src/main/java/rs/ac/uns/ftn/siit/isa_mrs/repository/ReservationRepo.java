package rs.ac.uns.ftn.siit.isa_mrs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.siit.isa_mrs.model.Reservation;

import java.util.Collection;

public interface ReservationRepo extends JpaRepository<Reservation, Long> {
    Collection<Reservation> findAllByClientId(Long id);
}
