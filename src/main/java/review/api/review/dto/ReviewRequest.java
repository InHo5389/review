package review.api.review.dto;

import lombok.Getter;
import review.application.review.dto.ReviewCommand;

public class ReviewRequest {

    @Getter
    public static class Create{
        private Long userId;
        private Integer score;
        private String content;

        public ReviewCommand.Create toCommand() {
            return ReviewCommand.Create.builder()
                    .userId(userId)
                    .score(score)
                    .content(content)
                    .build();
        }
    }
}
