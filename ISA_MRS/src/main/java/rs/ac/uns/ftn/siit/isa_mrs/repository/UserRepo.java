package rs.ac.uns.ftn.siit.isa_mrs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.siit.isa_mrs.model.User;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
    void deleteById(Long id);
}
