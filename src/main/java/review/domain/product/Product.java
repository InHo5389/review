package review.domain.product;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long reviewCount;
    private double score;

    public void updateWithNewReview(int newScore){
        double totalScore = this.score * reviewCount;
        this.reviewCount++;
        this.score = Math.floor((totalScore + newScore) / this.reviewCount * 10) / 10;
    }
}
