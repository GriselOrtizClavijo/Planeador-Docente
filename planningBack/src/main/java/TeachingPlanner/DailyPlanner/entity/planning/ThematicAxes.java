package TeachingPlanner.DailyPlanner.entity.planning;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ThematicAxes")
public class ThematicAxes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idThematicAxes", nullable = false)
    private int idThematicAxes;

    @Column(nullable = false, unique = true)
    private String name;
}
