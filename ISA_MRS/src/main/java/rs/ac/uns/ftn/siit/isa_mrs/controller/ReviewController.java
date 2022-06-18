package rs.ac.uns.ftn.siit.isa_mrs.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.siit.isa_mrs.dto.PageDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.AdminDtos.ReviewDto;
import rs.ac.uns.ftn.siit.isa_mrs.service.ReviewService;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

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
}
