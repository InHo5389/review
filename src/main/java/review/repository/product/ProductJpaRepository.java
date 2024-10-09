package review.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import review.domain.product.Product;

public interface ProductJpaRepository extends JpaRepository<Product,Long> {
}
