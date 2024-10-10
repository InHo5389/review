package review.application.review;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import review.domain.review.dto.PageResult;
import review.domain.review.dto.ReviewDto;
import review.domain.review.dto.ReviewResponse;
import review.application.review.dto.ReviewCommand;
import review.domain.common.dummy.DummyS3Service;
import review.domain.product.Product;
import review.domain.product.ProductService;
import review.domain.review.Review;
import review.domain.review.ReviewService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ReviewFacade {

    private final ProductService productService;
    private final ReviewService reviewService;
    private final DummyS3Service s3Service;

    @Transactional
    public void createReview(Long productId, ReviewCommand.Create command, MultipartFile image) {
        reviewService.validateHasNotReviewedBy(command.getUserId(),productId);
        String s3Url = s3Service.uploadImage(image);
        Product product = productService.getProductWithLock(productId);
        reviewService.saveReview(command.toDto(), productId, s3Url);
        product.updateWithNewReview(command.getScore());
    }

    public ReviewResponse.GetReviews getReviewsByCursor(Long productId, Long cursor, int size) {
        Product product = productService.getProduct(productId);
        List<Review> reviews = reviewService.getReviews(productId, cursor, size + 1);
        PageResult<ReviewDto.GetReview> pageReviewsDtos = reviewService.getPageReviewsDtos(reviews, size);
        Long nextCursor = reviewService.getNextCursor(pageReviewsDtos);
        return new ReviewResponse.GetReviews(product.getReviewCount(), product.getScore()
                , nextCursor, pageReviewsDtos.getContent());
    }
}
