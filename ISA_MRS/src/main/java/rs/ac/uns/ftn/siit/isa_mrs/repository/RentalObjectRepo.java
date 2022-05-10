package rs.ac.uns.ftn.siit.isa_mrs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.siit.isa_mrs.model.RentalObject;


public interface RentalObjectRepo extends JpaRepository<RentalObject, Long> {
}
