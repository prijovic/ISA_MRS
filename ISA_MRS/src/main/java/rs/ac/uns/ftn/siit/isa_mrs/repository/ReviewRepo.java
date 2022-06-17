package rs.ac.uns.ftn.siit.isa_mrs.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.siit.isa_mrs.model.RentalObject;
import rs.ac.uns.ftn.siit.isa_mrs.model.Review;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RequestStatus;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.ReviewType;

public interface ReviewRepo extends JpaRepository<Review, Long> {
    Page<Review> findAllByReservationRentalObjectAndAuthorIsActiveAndReviewTypeAndStatus(RentalObject rentalObject,
                     Boolean isActive, ReviewType reviewType, Pageable pageable, RequestStatus status);
}
