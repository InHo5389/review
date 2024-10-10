package review.domain.review;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import review.domain.common.exception.CustomGlobalException;
import review.domain.review.dto.PageResult;
import review.domain.review.dto.ReviewDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    void validateHasNotReviewedBy() {
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
    void validateHasNotReviewedBy_fail() {
        //given
        Long userId = 1L;

        given(reviewRepository.existsByUserId(userId))
                .willReturn(true);
        //when
        //then
        assertThatThrownBy(() -> reviewService.validateHasNotReviewedBy(userId))
                .hasMessage("이미 리뷰를 작성하셨습니다.")
                .isInstanceOf(CustomGlobalException.class);
    }

    @Test
    @DisplayName("이후 페이지가 존재하면 다음 커서를 계산한다.")
    void getNextCursorExistNextPage() {
        //given
        List<ReviewDto.GetReview> reviewDtos = new ArrayList<>();
        int cursor = 5;
        for (int i = 1; i <= cursor; i++) {
            Long num = (long) i;
            reviewDtos.add(new ReviewDto.GetReview(num, num, 0, "hello", "hi.jpg", LocalDateTime.now()));
        }
        PageResult<ReviewDto.GetReview> pageReviewDtos = new PageResult<>(reviewDtos,true);
        //when
        Long nextCursor = reviewService.getNextCursor(pageReviewDtos);
        //then
        Assertions.assertThat(nextCursor).isEqualTo(cursor);
    }

    @Test
    @DisplayName("이후 페이지가 존재하지 않는다면 커서는 null을 반환한다.")
    void getNextCursorNotExistNextPage() {
        //given
        List<ReviewDto.GetReview> reviewDtos = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Long num = (long) i;
            reviewDtos.add(new ReviewDto.GetReview(num, num, 0, "hello", "hi.jpg", LocalDateTime.now()));
        }
        PageResult<ReviewDto.GetReview> pageReviewDtos = new PageResult<>(reviewDtos,false);
        //when
        Long nextCursor = reviewService.getNextCursor(pageReviewDtos);
        //then
        Assertions.assertThat(nextCursor).isEqualTo(null);
    }
}