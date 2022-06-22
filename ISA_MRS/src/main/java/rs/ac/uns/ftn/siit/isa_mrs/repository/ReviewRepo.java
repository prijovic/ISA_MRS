package rs.ac.uns.ftn.siit.isa_mrs.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.QueryHints;
import rs.ac.uns.ftn.siit.isa_mrs.model.RentalObject;
import rs.ac.uns.ftn.siit.isa_mrs.model.Review;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RequestStatus;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.ReviewType;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;

public interface ReviewRepo extends JpaRepository<Review, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value ="0")})
    Page<Review> findAllByReservationRentalObjectAndAuthorIsActiveAndReviewTypeAndStatus(RentalObject rentalObject,
                     Boolean isActive, ReviewType reviewType, Pageable pageable, RequestStatus status);
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value ="0")})
    Page<Review> findAllByStatus(RequestStatus status, Pageable pageable);
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value ="0")})
    Page<Review> findAllByStatusAndReviewType(RequestStatus status, ReviewType type, Pageable pageable);
}
