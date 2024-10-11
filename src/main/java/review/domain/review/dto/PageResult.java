package review.domain.review.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class PageResult<T> {
    List<T> content;
    boolean hasNext;

    public PageResult(List<T> content, boolean hasNext) {
        this.content = content;
        this.hasNext = hasNext;
    }
}
