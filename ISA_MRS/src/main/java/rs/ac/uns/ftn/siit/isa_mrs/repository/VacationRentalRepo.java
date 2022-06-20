package rs.ac.uns.ftn.siit.isa_mrs.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.siit.isa_mrs.model.VacationRental;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RentalObjectType;

import java.util.Collection;
import java.util.Optional;

public interface VacationRentalRepo extends JpaRepository<VacationRental, Long> {
    Page<VacationRental> findAllByRentalObjectType(RentalObjectType rentalObjectType, Pageable pageable);
    Page<VacationRental> findAllByRentalObjectTypeAndRentalObjectOwnerEmail(RentalObjectType rentalObjectType, String rentalObjectOwnerEmail, Pageable pageable);
    Collection<VacationRental> findAllByIsActive(boolean isActive);
}
