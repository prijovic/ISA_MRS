package rs.ac.uns.ftn.siit.isa_mrs.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.siit.isa_mrs.model.VacationRental;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RentalObjectType;

import java.util.Optional;

public interface VacationRentalRepo extends JpaRepository<VacationRental, Long> {
    Optional<VacationRental> findById(Long id);
    Page<VacationRental> findByRentalObjectType(RentalObjectType rentalObjectType, Pageable pageble);
}
