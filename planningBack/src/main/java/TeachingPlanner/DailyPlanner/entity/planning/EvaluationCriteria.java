package TeachingPlanner.DailyPlanner.entity.planning;

import TeachingPlanner.DailyPlanner.enums.Periods;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Setter
@Getter
@Builder
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


    // 🔹 Relación con Área (muchos DBAs por un Área)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idArea")
    private Areas areas;

    // 🔹 Lista de periodos (1–5) usando Enum
    @ElementCollection(targetClass = Periods.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "evaluation_criteria_periods", joinColumns = @JoinColumn(name = "evaluation_criteria_id"))
    @Column(name = "periodo")
    private Set<Periods> periods;
}
