package rs.ac.uns.ftn.siit.isa_mrs.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.AdminDtos.ReviewDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.PageDto;
import rs.ac.uns.ftn.siit.isa_mrs.model.Client;
import rs.ac.uns.ftn.siit.isa_mrs.model.RentalObject;
import rs.ac.uns.ftn.siit.isa_mrs.model.Reservation;
import rs.ac.uns.ftn.siit.isa_mrs.model.Review;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RequestStatus;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.ReviewType;
import rs.ac.uns.ftn.siit.isa_mrs.repository.ReservationRepo;
import rs.ac.uns.ftn.siit.isa_mrs.repository.ReviewRepo;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
class ReviewServiceImplTest {
    @Mock
    private ReviewRepo reviewRepo;
    @Mock
    private ModelMapper modelMapper = new ModelMapper();
    @Mock
    private ReservationRepo reservationRepo;
    @InjectMocks
    private ReviewServiceImpl reviewService;

    @Test
    void getReviews() {
        Pageable pageable = PageRequest.of(0, 3).withSort(Sort.by(Sort.Order.asc("timeStamp")));
        List<Review> reviews = new ArrayList<>();
        List<ReviewDto> dtos = new ArrayList<>();
        Reservation reservation = new Reservation();
        Client client = new Client();
        client.setName("Marko");
        client.setSurname("Markovic");
        reservation.setClient(client);
        for (int i = 0; i < 5; i++) {
            Review review = new Review();
            ReviewDto dto1 = new ReviewDto();
            review.setReviewType(ReviewType.ClientReview);

            review.setId(i+1L);
            dto1.setId(i+1L);

            review.setStatus(RequestStatus.Pending);

            review.setComment("KOMENTAR"+i);
            dto1.setComment("KOMENTAR"+i);

            reviews.add(review);
            dtos.add(dto1);
        }
        Page<Review> page = new PageImpl<>(reviews.subList(0, 3), pageable, 3);
        when(reviewRepo.findAllByStatus(RequestStatus.Pending, pageable)).thenReturn(page);

        when(modelMapper.map(reviews.get(0), ReviewDto.class)).thenReturn(dtos.get(0));
        when(modelMapper.map(reviews.get(1), ReviewDto.class)).thenReturn(dtos.get(1));
        when(modelMapper.map(reviews.get(2), ReviewDto.class)).thenReturn(dtos.get(2));

        when(reservationRepo.findByReviewsIsContaining(reviews.get(0))).thenReturn(reservation);
        when(reservationRepo.findByReviewsIsContaining(reviews.get(1))).thenReturn(reservation);
        when(reservationRepo.findByReviewsIsContaining(reviews.get(2))).thenReturn(reservation);

        ResponseEntity<PageDto<ReviewDto>> result = reviewService.getReviews(0, 3);

        assertEquals(result.getStatusCode(), HttpStatus.OK);
        Assertions.assertNotNull(result.getBody());
        assertEquals(result.getBody().getContent().size(), 3);
        assertEquals(result.getBody().getContent().iterator().next().getSubjectName(), "Marko Markovic");
    }

    @Test
    void getReviewsFilter() {
        Pageable pageable = PageRequest.of(0, 2).withSort(Sort.by(Sort.Order.asc("timeStamp")));
        List<Review> reviews1 = new ArrayList<>();
        List<ReviewDto> dtos1 = new ArrayList<>();
        Reservation reservation = new Reservation();
        Client client = new Client();
        client.setName("Marko");
        client.setSurname("Markovic");
        RentalObject rentalObject = new RentalObject();
        rentalObject.setName("Rental");
        reservation.setRentalObject(rentalObject);
        reservation.setClient(client);
        for (int i = 0; i < 5; i++) {
            Review review = new Review();
            ReviewDto dto1 = new ReviewDto();

            review.setReviewType(i%2==0?ReviewType.ClientReview:ReviewType.RentalReview);

            review.setId(i+1L);
            dto1.setId(i+1L);

            review.setStatus(RequestStatus.Pending);

            review.setComment("KOMENTAR"+i);
            dto1.setComment("KOMENTAR"+i);

            if (i%2==0) {
                review.setReviewType(ReviewType.ClientReview);
                reviews1.add(review);
                dtos1.add(dto1);
            }
        }

        Page<Review> reviewPage1 = new PageImpl<>(reviews1.subList(0, 2), pageable, 2);
        when(reviewRepo.findAllByStatusAndReviewType(RequestStatus.Pending, ReviewType.ClientReview, pageable)).thenReturn(reviewPage1);

        when(modelMapper.map(reviews1.get(0), ReviewDto.class)).thenReturn(dtos1.get(0));
        when(modelMapper.map(reviews1.get(1), ReviewDto.class)).thenReturn(dtos1.get(1));
        when(modelMapper.map(reviews1.get(2), ReviewDto.class)).thenReturn(dtos1.get(2));

        when(reservationRepo.findByReviewsIsContaining(reviews1.get(0))).thenReturn(reservation);
        when(reservationRepo.findByReviewsIsContaining(reviews1.get(1))).thenReturn(reservation);
        when(reservationRepo.findByReviewsIsContaining(reviews1.get(2))).thenReturn(reservation);

        ResponseEntity<PageDto<ReviewDto>> result = reviewService.getReviewsFilter(0, 2, "RentalObjectOwner", "Client");

        assertEquals(result.getStatusCode(), HttpStatus.OK);
        Assertions.assertNotNull(result.getBody());
        assertEquals(result.getBody().getContent().size(), 2);
        assertEquals(result.getBody().getContent().iterator().next().getSubjectName(), "Marko Markovic");
    }
}