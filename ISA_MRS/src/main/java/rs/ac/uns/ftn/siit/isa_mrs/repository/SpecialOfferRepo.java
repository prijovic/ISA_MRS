package rs.ac.uns.ftn.siit.isa_mrs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.siit.isa_mrs.model.SpecialOffer;

import java.util.Optional;

public interface SpecialOfferRepo extends JpaRepository<SpecialOffer, Long> {
    Optional<SpecialOffer> findById(Long id);
}
