package rs.ac.uns.ftn.siit.isa_mrs.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.siit.isa_mrs.dto.PageDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.AdminDtos.ReviewDto;
import rs.ac.uns.ftn.siit.isa_mrs.service.ReviewService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/Reviews")
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/getReviewsPage")
    public ResponseEntity<PageDto<ReviewDto>> getReviews(@RequestParam Integer page, @RequestParam Integer pageSize) {
        return reviewService.getReviews(page, pageSize);
    }

    @GetMapping("/getReviewsFilterPage")
    public ResponseEntity<PageDto<ReviewDto>> getFilteredReviews(@RequestParam Integer page, @RequestParam Integer pageSize, @RequestParam String author, @RequestParam String subject) {
        return reviewService.getReviewsFilter(page, pageSize, author, subject);
    }

    @PutMapping("/manageReview")
    public ResponseEntity<ReviewDto> changeReviewStatus(@RequestParam Long id, @RequestParam boolean accepted) {
        return reviewService.changeReviewStatus(id, accepted);
    }
}
