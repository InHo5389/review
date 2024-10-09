package review.repository.review;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import review.domain.review.Review;
import review.domain.review.ReviewRepository;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepository {

    private final ReviewJpaRepository reviewJpaRepository;

    @Override
    public boolean existsByUserId(Long userId) {
        return reviewJpaRepository.existsByUserId(userId);
    }

    @Override
    public Review save(Review review) {
        return reviewJpaRepository.save(review);
    }
}
