package review.domain.review;

public interface ReviewRepository {
    boolean existsByUserId(Long userId);
    Review save(Review review);
}
