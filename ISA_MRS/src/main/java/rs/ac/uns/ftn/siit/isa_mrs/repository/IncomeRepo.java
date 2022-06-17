package rs.ac.uns.ftn.siit.isa_mrs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.siit.isa_mrs.model.Income;

import java.util.Optional;

public interface IncomeRepo extends JpaRepository<Income, Long> {
    Optional<Income> findByReservationId(Long id);
}
