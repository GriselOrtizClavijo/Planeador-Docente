package TeachingPlanner.DailyPlanner.entity.planning;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "EvaluationCriteria")
public class EvaluationCriteria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEvaluationCriteria", nullable = false)
    private int idEvaluationCriteria;

    @Column(nullable = false, unique = true)
    private String description;


}
