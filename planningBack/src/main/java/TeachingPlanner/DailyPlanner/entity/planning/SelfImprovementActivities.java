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
@Table(name = "SelfImprovementActivities")
public class SelfImprovementActivities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idSiA", nullable = false)
    private int idSiA;

    @Column(nullable = false, unique = true)
    private String name;

    // 🔹 Relación con Área (muchos DBAs por un Área)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idArea")
    private Areas areas;

    // 🔹 Lista de periodos (1–5) usando Enum
    @ElementCollection(targetClass = Periods.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "sia_periods", joinColumns = @JoinColumn(name = "sia_id"))
    @Column(name = "periodo")
    private Set<Periods> periods;

}
