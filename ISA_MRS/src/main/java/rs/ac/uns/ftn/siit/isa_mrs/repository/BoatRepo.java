package rs.ac.uns.ftn.siit.isa_mrs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.siit.isa_mrs.model.Boat;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RentalObjectType;

import java.util.Collection;
import java.util.Optional;

public interface BoatRepo extends JpaRepository<Boat, Long> {
    Optional<Boat> findById(Long id);
    Collection<Boat> findByRentalObjectType(RentalObjectType rentalObjectType);
}
