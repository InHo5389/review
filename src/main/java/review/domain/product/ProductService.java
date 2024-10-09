package review.domain.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import review.domain.common.exception.CustomGlobalException;
import review.domain.common.exception.ErrorType;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product getProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new CustomGlobalException(ErrorType.NOT_FOUND_PRODUCT));
    }
}
