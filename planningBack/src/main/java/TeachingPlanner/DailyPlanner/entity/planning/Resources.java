package TeachingPlanner.DailyPlanner.entity.planning;

import TeachingPlanner.DailyPlanner.enums.Periods;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Resources")
public class Resources {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idResources", nullable = false)
    private int idResources;

    @Column(nullable = false, unique = true)
    private String name;


}
