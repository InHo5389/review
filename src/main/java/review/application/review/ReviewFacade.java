package review.application.review;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import review.application.review.dto.ReviewCommand;
import review.domain.dummy.DummyS3Service;
import review.domain.product.ProductService;
import review.domain.review.ReviewService;

@Component
@RequiredArgsConstructor
public class ReviewFacade {

    private final ProductService productService;
    private final ReviewService reviewService;
    private final DummyS3Service s3Service;

    @Transactional
    public void createReview(Long productId, ReviewCommand.Create command, MultipartFile image){
        productService.getProduct(productId);
        reviewService.validateHasNotReviewedBy(command.getUserId());
        String s3Url = s3Service.uploadImage(image);
        reviewService.saveReview(command.toDto(),s3Url);
    }
}
