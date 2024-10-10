package review.domain.review;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewRepository {
    boolean existsByUserId(Long userId);
    Review save(Review review);

    List<Review> findByProductIdOrderByIdDesc(Long productId, Pageable pageable);
    List<Review> findByProductIdAndIdLessThanOrderByIdDesc(Long productId, Long id, Pageable pageable);

}

