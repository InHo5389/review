package review.application.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import review.domain.review.dto.ReviewDto;

public class ReviewCommand {
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Create{
        private Long userId;
        private Integer score;
        private String content;

        public ReviewDto.Create toDto() {
            return new ReviewDto.Create(userId,score,content);
        }
    }
}
