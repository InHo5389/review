package review.domain.common.exception;

import lombok.Getter;

@Getter
public class CustomGlobalException extends RuntimeException{

    private final ErrorType errorType;

    public CustomGlobalException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }

    public CustomGlobalException(ErrorType errorType,Exception e) {
        super(errorType.getMessage(),e);
        this.errorType = errorType;
    }
}
