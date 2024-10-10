package review.domain.review;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import review.domain.common.exception.CustomGlobalException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewService reviewService;

    @Test
    @DisplayName("유저가 리뷰를 작성하지 않았으면 테스트를 성공한다.")
    void validateHasNotReviewedBy(){
        //given
        Long userId = 1L;

        given(reviewRepository.existsByUserId(userId))
                .willReturn(false);
        //when
        //then
        reviewService.validateHasNotReviewedBy(userId);
    }

    @Test
    @DisplayName("유저가 리뷰를 작성했으면 예외가 발생한다..")
    void validateHasNotReviewedBy_fail(){
        //given
        Long userId = 1L;

        given(reviewRepository.existsByUserId(userId))
                .willReturn(true);
        //when
        //then
        assertThatThrownBy(()->reviewService.validateHasNotReviewedBy(userId))
                .hasMessage("이미 리뷰를 작성하셨습니다.")
                .isInstanceOf(CustomGlobalException.class);
    }
}