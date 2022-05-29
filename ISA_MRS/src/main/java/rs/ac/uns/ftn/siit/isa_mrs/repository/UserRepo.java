package rs.ac.uns.ftn.siit.isa_mrs.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.siit.isa_mrs.model.RentalObjectOwner;
import rs.ac.uns.ftn.siit.isa_mrs.model.User;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.UserType;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByEmailAndPassword(String email, String password);
    Page<User> findAllByUserType(UserType type, Pageable pageable);
    void deleteById(Long id);
}
