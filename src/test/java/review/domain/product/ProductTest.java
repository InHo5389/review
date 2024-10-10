package review.domain.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTest {
    @Test
    @DisplayName("평균 평점이 3.5점이고 리뷰 개수가 2개인 상품이 있을때 " +
            "그 상품에 대해서 새로운 리뷰에서 평점 5점을 받으면 그 상품의 평점은 4.0점이여야 한다.")
    void updateWithNewReview() {
        //given
        int newScore = 5;
        long reviewCount = 2L;
        double score = 3.5;
        Product product = new Product(1L, reviewCount, score);
        double expectedScore = Math.floor(((score * reviewCount) + newScore) / (reviewCount + 1) * 10) / 10;
        //when
        //then
        product.updateWithNewReview(newScore);
        assertThat(product.getReviewCount()).isEqualTo(reviewCount + 1);
        assertThat(product.getScore()).isEqualTo(expectedScore);
    }
}