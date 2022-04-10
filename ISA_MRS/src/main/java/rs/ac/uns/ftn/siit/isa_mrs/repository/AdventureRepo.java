package rs.ac.uns.ftn.siit.isa_mrs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.siit.isa_mrs.model.Adventure;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RentalObjectType;

import java.util.Collection;
import java.util.Optional;

public interface AdventureRepo extends JpaRepository<Adventure, Long> {
    Optional<Adventure> findById(Long id);
    Collection<Adventure> findByRentalObjectType(RentalObjectType rentalObjectType);
}
