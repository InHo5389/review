package review.domain.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorType {

    NOT_FOUND_PRODUCT(404,"상품을 찾을수 없습니다."),
    ALREADY_EXIST_REVIEW(409,"이미 리뷰를 작성하셨습니다."),
    FAIL_FILE_UPLOAD(500,"파일을 읽는 중 오류가 발생했습니다."),
    INVALID_REVIEW_SCORE(400, "평가 점수는 1점에서 5점까지 입력 가능합니다.")
    ;

    private final int status;
    private final String message;
}
