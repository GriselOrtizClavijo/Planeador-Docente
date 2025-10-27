package TeachingPlanner.DailyPlanner.entity.planning;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SelfImprovementActivities")
public class SelfImprovementActivities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idSiA", nullable = false)
    private int idSiA;

}
