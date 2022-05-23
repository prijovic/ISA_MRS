package rs.ac.uns.ftn.siit.isa_mrs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.siit.isa_mrs.model.Address;

import java.util.Optional;

public interface AddressRepo extends JpaRepository<Address, Long> {
}
