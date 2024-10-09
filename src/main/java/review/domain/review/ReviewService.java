package review.domain.review;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import review.domain.common.exception.CustomGlobalException;
import review.domain.common.exception.ErrorType;
import review.domain.review.dto.ReviewDto;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public void validateHasNotReviewedBy (Long userId){
        if (reviewRepository.existsByUserId(userId)){
            throw new CustomGlobalException(ErrorType.ALREADY_EXIST_REVIEW);
        }
    }

    public void saveReview(ReviewDto.Create dto,Long productId, String s3Url){
        Review review = Review.builder()
                .userId(dto.getUserId())
                .productId(productId)
                .score(dto.getScore())
                .content(dto.getContent())
                .imageUrl(s3Url)
                .createdAt(LocalDateTime.now())
                .build();
        review.validateScore(dto.getScore());
        reviewRepository.save(review);
    }
}
