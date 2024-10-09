package review.repository.review;

import org.springframework.data.jpa.repository.JpaRepository;
import review.domain.review.Review;

public interface ReviewJpaRepository extends JpaRepository<Review,Long> {
    boolean existsByUserId(Long userId);
}
