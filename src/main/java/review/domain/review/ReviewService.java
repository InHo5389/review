package review.domain.review;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import review.domain.common.exception.CustomGlobalException;
import review.domain.common.exception.ErrorType;
import review.domain.review.dto.PageResult;
import review.domain.review.dto.ReviewDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public void validateHasNotReviewedBy(Long userId,Long productId) {
        if (reviewRepository.existsByUserIdAndProductId(userId,productId)) {
            throw new CustomGlobalException(ErrorType.ALREADY_EXIST_REVIEW);
        }
    }

    public void saveReview(ReviewDto.Create dto, Long productId, String s3Url) {
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

    public List<Review> getReviews(Long productId, Long cursor, int size) {
        return cursor == null ?
                reviewRepository.findByProductIdOrderByIdDesc(productId, PageRequest.of(0, size)) :
                reviewRepository.findByProductIdAndIdLessThanOrderByIdDesc(productId, cursor, PageRequest.of(0, size));
    }

    public PageResult<ReviewDto.GetReview> getPageReviewsDtos(List<Review> reviews, int size){
        boolean hasNext = reviews.size() > size;
        List<Review> pageReviews = hasNext ? reviews.subList(0, size) : reviews;
        List<ReviewDto.GetReview> pageReviewDtos = pageReviews.stream().map(this::toDto)
                .collect(Collectors.toList());
        return new PageResult<>(pageReviewDtos,hasNext);
    }

    public Long getNextCursor(PageResult<ReviewDto.GetReview> pageReviewDtos){
        return pageReviewDtos.isHasNext() ? pageReviewDtos.getContent().get(pageReviewDtos.getContent().size() - 1).getId() : null;
    }


    private ReviewDto.GetReview toDto(Review review) {
        return new ReviewDto.GetReview(
                review.getId(),
                review.getUserId(),
                review.getScore(),
                review.getContent(),
                review.getImageUrl(),
                review.getCreatedAt()
        );
    }
}
