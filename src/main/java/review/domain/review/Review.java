package review.domain.review;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import review.domain.common.exception.CustomGlobalException;
import review.domain.common.exception.ErrorType;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@Table(name = "review")
@NoArgsConstructor
@AllArgsConstructor
public class Review {

    private static final int MIN_SCORE = 1;
    private static final int MAX_SCORE = 5;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;
    private Long userId;
    private int score;
    private String content;
    private String imageUrl;
    private LocalDateTime createdAt;

    public void validateScore(int score) {
        if (score < MIN_SCORE || score > MAX_SCORE) {
            throw new CustomGlobalException(ErrorType.INVALID_REVIEW_SCORE);
        }
    }
}
