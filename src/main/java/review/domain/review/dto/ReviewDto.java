package review.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import review.domain.review.Review;

import java.time.LocalDateTime;

public class ReviewDto {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Create{
        private Long userId;
        private int score;
        private String content;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetReview {
        private Long id;
        private Long userId;
        private int score;
        private String content;
        private String imageUrl;
        private LocalDateTime createdAt;
    }
}
