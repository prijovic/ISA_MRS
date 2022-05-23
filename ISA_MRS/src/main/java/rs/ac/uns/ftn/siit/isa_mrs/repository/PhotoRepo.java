package rs.ac.uns.ftn.siit.isa_mrs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.siit.isa_mrs.model.Photo;

import java.util.Optional;

public interface PhotoRepo extends JpaRepository<Photo, Long> {
    Optional<Photo> findById(Long id);
    Optional<Photo> findByPhoto(String photo);
}
