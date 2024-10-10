package review.domain.product;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import review.domain.common.exception.CustomGlobalException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    @DisplayName("상품이 있으면 상품을 가져온다.")
    void getProduct() {
        //given
        Long productId = 1L;
        long reviewCount = 1L;
        double score = 4.5;

        given(productRepository.findById(productId))
                .willReturn(Optional.of(new Product(productId, reviewCount, score)));
        //when
        //then
        assertThat(productService.getProduct(productId))
                .extracting("id","reviewCount","score")
                .containsExactly(productId,reviewCount,score);
    }

    @Test
    @DisplayName("상품이 없으면 예외가 발생한다.")
    void getProduct_fail() {
        //given
        Long productId = 1L;

        given(productRepository.findById(productId))
                .willReturn(Optional.empty());
        //when
        //then
        assertThatThrownBy(()->productService.getProduct(productId))
                .hasMessage("상품을 찾을수 없습니다.")
                .isInstanceOf(CustomGlobalException.class);
    }

}