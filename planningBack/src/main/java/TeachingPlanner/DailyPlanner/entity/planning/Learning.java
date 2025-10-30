package TeachingPlanner.DailyPlanner.entity.planning;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "Learning")
public class Learning {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idLearning", nullable = false)
    private int idLearning;


    @Column(nullable = false, unique = true)
    private String name;
}
