package review.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

public class ReviewResponse {

    @Getter
    @AllArgsConstructor
    public static class GetReviews {
        private Long totalCount;
        private double score;
        private Long cursor;
        private List<ReviewDto.GetReview> reviews;
    }
}
