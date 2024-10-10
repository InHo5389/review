package review.domain.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "product")
@NoArgsConstructor
@AllArgsConstructor
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
