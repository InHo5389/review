package review.application.review;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import review.api.review.dto.ReviewRequest;
import review.application.review.dto.ReviewCommand;
import review.domain.product.Product;
import review.domain.product.ProductRepository;
import review.domain.review.ReviewService;
import review.domain.review.dto.ReviewResponse;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class ReviewFacadeTest {

    @Autowired
    private ReviewFacade reviewFacade;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ProductRepository productRepository;

    private static final int NUMBER_OF_THREADS = 100;


    @BeforeEach
    void setup() {
        productRepository.save(new Product(1L, 0L, 0));
    }

    @Test
    @DisplayName("100명의 유저가 동시에 1점의 리뷰를 달면 리뷰 카운트가 100이 되어야 하고" +
            "평균 평점은 1.0이 되어야 한다.")
    void createReviewConcurrencyTest() throws InterruptedException {
        long startTime = System.currentTimeMillis();
        Long productId = 1L;

        ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
        CountDownLatch latch = new CountDownLatch(NUMBER_OF_THREADS);

        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            Long userId = (long) i + 1;
            executorService.submit(() -> {
                try {
                    ReviewCommand.Create command = new ReviewCommand.Create(userId, 1, "hello");
                    reviewFacade.createReview(productId, command, null);
                } catch (Exception e) {
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
        executorService.shutdown();

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        System.out.println("executionTime === " + executionTime);
        Product product = productRepository.findById(productId).get();

        System.out.println("product reviewCount " + product.getReviewCount());
        System.out.println("product score " + product.getScore());

        assertThat(product.getReviewCount()).isEqualTo(100);
        assertThat(product.getScore()).isEqualTo(1.0);
    }

    @Test
    @DisplayName("커서기반 페이징으로 리뷰를 조회할때 ID가 1인 상품에 리뷰가 11개가 있으면 리뷰 10개가 출력 되고 커서는 2로 지정된다.")
    void getReviewsByCursor() {
        //given
        Long productId = 1L;
        Long reviewCount = 11L;
        Long cursor = 2L;

        for (int i = 1; i <= reviewCount; i++) {
            long userId = i;
            ReviewCommand.Create command = new ReviewCommand.Create(userId, 1, "hello");
            reviewFacade.createReview(productId,command,null);
        }

        Product product = productRepository.findById(productId).get();
        Long dbReviewCount = product.getReviewCount();
        //when
        ReviewResponse.GetReviews reviewResponse = reviewFacade.getReviewsByCursor(productId, null, 10);
        //then
        assertThat(reviewResponse).extracting("totalCount","cursor")
                .containsExactly(reviewCount,cursor);
        assertThat(reviewCount).isEqualTo(dbReviewCount);
    }

    @Test
    @DisplayName("커서기반 페이징으로 리뷰를 조회할때 ID가 1인 상품에 리뷰가 5개가 있으면 이후 페이지가 없으니" +
            " 출력 되고 커서는 null로 지정된다.")
    void getReviewsByCursorNotExistNextPage() {
        //given
        Long productId = 1L;
        Long reviewCount = 5L;
        Long cursor = null;

        for (int i = 1; i <= reviewCount; i++) {
            long userId = i;
            ReviewCommand.Create command = new ReviewCommand.Create(userId, 1, "hello");
            reviewFacade.createReview(productId,command,null);
        }

        Product product = productRepository.findById(productId).get();
        Long dbReviewCount = product.getReviewCount();
        //when
        ReviewResponse.GetReviews reviewResponse = reviewFacade.getReviewsByCursor(productId, null, 10);
        //then
        assertThat(reviewResponse).extracting("totalCount","cursor")
                .containsExactly(reviewCount,cursor);
        assertThat(reviewCount).isEqualTo(dbReviewCount);
    }
}