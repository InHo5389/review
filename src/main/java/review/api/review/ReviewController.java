package review.api.review;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import review.api.review.dto.ReviewRequest;
import review.domain.review.dto.ReviewResponse;
import review.application.review.ReviewFacade;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewFacade reviewFacade;

    @PostMapping("/products/{productId}/reviews")
    public void createReview(
            @PathVariable Long productId,
            @RequestPart("review") ReviewRequest.Create request,
            @RequestPart(value = "image", required = false) MultipartFile image) {

        reviewFacade.createReview(productId, request.toCommand(), image);
    }

    @GetMapping("/products/{productId}/reviews")
    public ResponseEntity<ReviewResponse.GetReviews> getReviews(
            @PathVariable Long productId,
            @RequestParam(required = false) Long cursor,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(reviewFacade.getReviewsByCursor(productId,cursor,size));
    }
}
