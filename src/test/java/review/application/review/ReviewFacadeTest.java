package review.application.review;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import review.application.review.dto.ReviewCommand;
import review.domain.product.Product;
import review.domain.product.ProductRepository;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
@ActiveProfiles("test")
class ReviewFacadeTest {

    @Autowired
    private ReviewFacade reviewFacade;

    @Autowired
    private ProductRepository productRepository;

    private static final int NUMBER_OF_THREADS = 100;


    @BeforeEach
    void setup() {
        productRepository.save(new Product(1L, 0L, 0));
    }

    @Test
    @DisplayName("")
    void test() throws InterruptedException {
        long startTime = System.currentTimeMillis();
        Long productId = 1L;

        ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
        CountDownLatch latch = new CountDownLatch(NUMBER_OF_THREADS);

        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            Long userId = (long) i + 1;
            executorService.submit(()->{
                try {
                    ReviewCommand.Create command = new ReviewCommand.Create(userId, 1, "hello");
                    reviewFacade.createReview(productId,command,null);
                }catch (Exception e){
                }finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
        executorService.shutdown();

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        System.out.println("executionTime === "+ executionTime);
        Product product = productRepository.findById(productId).get();

        System.out.println("product reviewCount " + product.getReviewCount());
        System.out.println("product score " + product.getScore());

        Assertions.assertThat(product.getReviewCount()).isEqualTo(100);
        Assertions.assertThat(product.getScore()).isEqualTo(1.0);
    }
}