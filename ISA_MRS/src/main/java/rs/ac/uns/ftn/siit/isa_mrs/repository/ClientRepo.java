package rs.ac.uns.ftn.siit.isa_mrs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.siit.isa_mrs.model.Client;

import java.util.Optional;

public interface ClientRepo extends JpaRepository<Client, Long> {
    Optional<Client> findByEmail(String email);
}
