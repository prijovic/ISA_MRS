package rs.ac.uns.ftn.siit.isa_mrs.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.AdminDtos.ReviewDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.PageDto;
import rs.ac.uns.ftn.siit.isa_mrs.model.Client;
import rs.ac.uns.ftn.siit.isa_mrs.model.RentalObjectOwner;
import rs.ac.uns.ftn.siit.isa_mrs.model.Reservation;
import rs.ac.uns.ftn.siit.isa_mrs.model.Review;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RequestStatus;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.ReviewType;
import rs.ac.uns.ftn.siit.isa_mrs.repository.ReservationRepo;
import rs.ac.uns.ftn.siit.isa_mrs.repository.ReviewRepo;

import java.security.KeyStore;
import java.util.ArrayList;
import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{
    private final ReviewRepo reviewRepo;
    private final ModelMapper modelMapper;
    private final ReservationRepo reservationRepo;
    private final EmailSenderService emailSenderService;

    @Override
    public ResponseEntity<PageDto<ReviewDto>> getReviews(int page, int pageSize) {
        try {
            Pageable pageable = PageRequest.of(page, pageSize).withSort(Sort.by(Sort.Order.asc("timeStamp")));
            Page<Review> reviewPage = reviewRepo.findAllByStatus(RequestStatus.Pending, pageable);
            return new ResponseEntity<>(reviewPageToDto(reviewPage), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<PageDto<ReviewDto>> getReviewsFilter(Integer page, Integer pageSize, String author, String subject) {
        Page<Review> reviewPage;
        try {
            Pageable pageable = PageRequest.of(page, pageSize).withSort(Sort.by(Sort.Order.asc("timeStamp")));
            ReviewType reviewType = getReviewType(author, subject);
            reviewPage = reviewRepo.findAllByStatusAndReviewType(RequestStatus.Pending, reviewType,pageable);
            return new ResponseEntity<>(reviewPageToDto(reviewPage), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ReviewDto> changeReviewStatus(Long id, boolean accepted) {
        try {
            Review review = reviewRepo.getById(id);
            if (accepted) {
                review.setStatus(RequestStatus.Accepted);
                String commentedObject;
                Reservation reservation = reservationRepo.findByReviewsIsContaining(review);
                if (review.getReviewType().equals(ReviewType.RentalReview)) {
                    commentedObject = reservation.getRentalObject().getName();
                } else if (review.getReviewType().equals(ReviewType.OwnerReview)) {
                    RentalObjectOwner owner = reservation.getRentalObject().getRentalObjectOwner();
                    commentedObject = owner.getName() + " " + owner.getSurname();
                } else {
                    Client client = reservation.getClient();
                    commentedObject = client.getName() + " " + client.getSurname();
                }
                emailSenderService.sendReviewStatusNotificationEmail(review, commentedObject);
            } else {
                review.setStatus(RequestStatus.Rejected);
            }
            reviewRepo.save(review);
            return new ResponseEntity<>(modelMapper.map(review, ReviewDto.class), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private PageDto<ReviewDto> reviewPageToDto(Page<Review> reviewPage) {
        PageDto<ReviewDto> result = new PageDto<>();
        Collection<ReviewDto> reviewDtos = new ArrayList<>();
        reviewPage.getContent().forEach(review -> {
            ReviewDto reviewDto = modelMapper.map(review, ReviewDto.class);
            Reservation reservation = reservationRepo.findByReviewsIsContaining(review);
            if (review.getReviewType().equals(ReviewType.RentalReview)) {
                reviewDto.setSubjectName(reservation.getRentalObject().getName());
            } else if (review.getReviewType().equals(ReviewType.OwnerReview)) {
                RentalObjectOwner owner = reservation.getRentalObject().getRentalObjectOwner();
                reviewDto.setSubjectName(owner.getName() + " " + owner.getSurname());
            } else {
                Client client = reservation.getClient();
                reviewDto.setSubjectName(client.getName() + " " + client.getSurname());
            }
            reviewDtos.add(reviewDto);
        });
        result.setContent(reviewDtos);
        result.setPages(reviewPage.getTotalPages());
        result.setCurrentPage(reviewPage.getNumber());
        result.setPageSize(reviewPage.getSize());
        return result;
    }

    private ReviewType getReviewType(String author, String subject) {
        if (author.equals("RentalObjectOwner")) {
            return ReviewType.ClientReview;
        } else if (author.equals("Client") && subject.equals("RentalObjectOwner")) {
            return ReviewType.OwnerReview;
        } else {
            return ReviewType.RentalReview;
        }
    }
}
