package review.repository.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import review.domain.product.Product;
import review.domain.product.ProductRepository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductJpaRepository productJpaRepository;

    @Override
    public Optional<Product> findById(Long productId) {
        return productJpaRepository.findById(productId);
    }
}
