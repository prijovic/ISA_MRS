package rs.ac.uns.ftn.siit.isa_mrs.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.siit.isa_mrs.model.Adventure;
import rs.ac.uns.ftn.siit.isa_mrs.model.Boat;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RentalObjectType;

import java.util.Collection;


public interface AdventureRepo extends JpaRepository<Adventure, Long> {
    Page<Adventure> findByRentalObjectType(RentalObjectType rentalObjectType, Pageable pageable);
    Page<Adventure> findAllByRentalObjectTypeAndRentalObjectOwnerEmail(RentalObjectType rentalObjectType, String rentalObjectOwnerEmail, Pageable pageable);
    Collection<Adventure> findAllByIsActive(boolean isActive);
}
