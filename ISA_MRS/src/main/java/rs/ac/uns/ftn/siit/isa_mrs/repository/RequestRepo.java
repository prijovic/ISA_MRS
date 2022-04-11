package rs.ac.uns.ftn.siit.isa_mrs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.siit.isa_mrs.model.Request;

import java.util.Optional;

public interface RequestRepo extends JpaRepository<Request, Long> {
    Optional<Request> findById(Long id);
}
