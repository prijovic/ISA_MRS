package rs.ac.uns.ftn.siit.isa_mrs.service;

import org.springframework.http.ResponseEntity;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.AdminDtos.ReviewDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.PageDto;

public interface ReviewService {
    ResponseEntity<PageDto<ReviewDto>> getReviews(int page, int pageSize);
    ResponseEntity<PageDto<ReviewDto>> getReviewsFilter(Integer page, Integer pageSize, String author, String subject);
    ResponseEntity<ReviewDto> changeReviewStatus(Long id, boolean accepted);
}
