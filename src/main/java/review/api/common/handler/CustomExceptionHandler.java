package review.api.common.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import review.api.common.dto.CustomApiResponse;
import review.domain.common.exception.CustomGlobalException;
import review.domain.common.exception.ErrorType;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(CustomGlobalException.class)
    public ResponseEntity<?> apiExceptionHandler(CustomGlobalException e) {
        ErrorType errorType = e.getErrorType();
        HttpStatus httpStatus = HttpStatus.valueOf(errorType.getStatus());
        return ResponseEntity
                .status(httpStatus.value())
                .body(new CustomApiResponse<>(httpStatus, e.getMessage(), null));
    }
}
