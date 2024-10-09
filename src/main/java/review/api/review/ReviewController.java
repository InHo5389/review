package review.api.review;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import review.api.review.dto.ReviewRequest;
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

        reviewFacade.createReview(productId,request.toCommand(),image);
    }
}
