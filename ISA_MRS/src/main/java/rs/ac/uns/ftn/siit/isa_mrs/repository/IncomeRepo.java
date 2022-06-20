package rs.ac.uns.ftn.siit.isa_mrs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.siit.isa_mrs.model.Income;
import java.util.Optional;

import java.time.LocalDateTime;
import java.util.Collection;

public interface IncomeRepo  extends JpaRepository<Income, Long> {
    Collection<Income> findAllByTimeStampBetween(LocalDateTime start, LocalDateTime end);
    Collection<Income> findAllByTimeStampBetweenOrderByTimeStamp(LocalDateTime start, LocalDateTime end);
    Optional<Income> findByReservationId(Long id);
}
