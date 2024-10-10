package review.domain.review;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import review.domain.common.exception.CustomGlobalException;

import static org.junit.jupiter.api.Assertions.*;

class ReviewTest {

    @Test
    @DisplayName("리뷰를 추가할 때 1점 이상 5점 이하면 테스트를 성공한다.")
    void validateScore(){
        //given
        Review review = new Review();
        //when
        //then
        review.validateScore(5);
    }

    @Test
    @DisplayName("리뷰를 추가할 때 1점 이상 5점 이하가 아니면 예외가 발생한다.")
    void validateScore_fail(){
        //given
        Review review = new Review();
        //when
        //then
        Assertions.assertThatThrownBy(()->review.validateScore(6))
                .hasMessage("평가 점수는 1점에서 5점까지 입력 가능합니다.")
                .isInstanceOf(CustomGlobalException.class);
    }

    @Test
    @DisplayName("리뷰를 추가할 때 1점 이상 5점 이하가 아니면 예외가 발생한다.")
    void validateScore_fail2(){
        //given
        Review review = new Review();
        //when
        //then
        Assertions.assertThatThrownBy(()->review.validateScore(0))
                .hasMessage("평가 점수는 1점에서 5점까지 입력 가능합니다.")
                .isInstanceOf(CustomGlobalException.class);
    }
}