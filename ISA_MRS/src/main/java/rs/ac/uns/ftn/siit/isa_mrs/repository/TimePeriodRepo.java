package rs.ac.uns.ftn.siit.isa_mrs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.siit.isa_mrs.model.TimePeriod;

import java.sql.Time;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

public interface TimePeriodRepo extends JpaRepository<TimePeriod, Long> {
    Optional<TimePeriod> findById(Long id);
    Collection<TimePeriod> findAllByInitDateBetweenAndAndReservationIsNotNull(LocalDate start, LocalDate end);
}
