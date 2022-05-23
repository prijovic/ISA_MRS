package rs.ac.uns.ftn.siit.isa_mrs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.siit.isa_mrs.model.ProfitFee;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RentalObjectType;

import java.util.Optional;

public interface ProfitFeeRepo extends JpaRepository<ProfitFee, Long> {
    Optional<ProfitFee> findProfitFeeByRentalObjectType(RentalObjectType rentalObjectType);
}