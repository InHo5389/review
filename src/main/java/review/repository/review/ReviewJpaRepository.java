package review.repository.review;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import review.domain.review.Review;

import java.util.List;

public interface ReviewJpaRepository extends JpaRepository<Review,Long> {
    boolean existsByUserIdAndProductId(Long userId,Long productId);
    List<Review> findByProductIdOrderByIdDesc(Long productId, Pageable pageable);
    List<Review> findByProductIdAndIdLessThanOrderByIdDesc(Long productId, Long id, Pageable pageable);
}
