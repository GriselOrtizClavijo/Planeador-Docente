package TeachingPlanner.DailyPlanner.entity.planning;

import TeachingPlanner.DailyPlanner.enums.Periods;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    // 🔹 Relación con Área (muchos DBAs por un Área)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idArea")
    private Areas areas;

    // 🔹 Lista de periodos (1–5) usando Enum
    @ElementCollection(targetClass = Periods.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "learning_periods", joinColumns = @JoinColumn(name = "learning_id"))
    @Column(name = "periodo")
    private Set<Periods> periods;
}
