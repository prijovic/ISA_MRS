package rs.ac.uns.ftn.siit.isa_mrs.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.siit.isa_mrs.model.Request;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RequestStatus;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RequestType;

import java.util.Optional;

public interface RequestRepo extends JpaRepository<Request, Long> {
    Page<Request> findAllByTypeAndStatus(RequestType type, RequestStatus status, Pageable pageable);
    Page<Request> findALLByStatus(RequestStatus status, Pageable pageable);
    Page<Request> findAll(Pageable pageable);
    Optional<Request> findById(Long id);
}
