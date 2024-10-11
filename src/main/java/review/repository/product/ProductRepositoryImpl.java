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

    @Override
    public Product save(Product product) {
        return productJpaRepository.save(product);
    }

    @Override
    public Optional<Product> findByIdWithLock(Long id) {
        return productJpaRepository.findByIdWithLock(id);
    }

    @Override
    public void deleteAll() {
        productJpaRepository.deleteAll();
    }
}
