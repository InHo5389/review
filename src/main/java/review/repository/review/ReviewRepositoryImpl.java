package review.repository.review;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import review.domain.review.Review;
import review.domain.review.ReviewRepository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepository {

    private final ReviewJpaRepository reviewJpaRepository;

    @Override
    public boolean existsByUserIdAndProductId(Long userId, Long productId) {
        return reviewJpaRepository.existsByUserIdAndProductId(userId,productId);
    }

    @Override
    public Review save(Review review) {
        return reviewJpaRepository.save(review);
    }

    @Override
    public List<Review> findByProductIdOrderByIdDesc(Long productId, Pageable pageable) {
        return reviewJpaRepository.findByProductIdOrderByIdDesc(productId,pageable);
    }

    @Override
    public List<Review> findByProductIdAndIdLessThanOrderByIdDesc(Long productId, Long id, Pageable pageable) {
        return reviewJpaRepository.findByProductIdAndIdLessThanOrderByIdDesc(productId,id,pageable);
    }

}
