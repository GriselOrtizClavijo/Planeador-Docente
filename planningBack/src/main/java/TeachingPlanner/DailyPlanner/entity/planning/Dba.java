package TeachingPlanner.DailyPlanner.entity.planning;

import TeachingPlanner.DailyPlanner.enums.Periods;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "areas"})
@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "dba")
public class Dba {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idDba", nullable = false)
    private int idDba;

    @Column(nullable = false, unique = true)
    private String description;

    // 🔹 Relación con Área (muchos DBAs por un Área)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idArea")
    private Areas areas;

    // 🔹 Lista de periodos (1–5) usando Enum
    @ElementCollection(targetClass = Periods.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "dba_periods", joinColumns = @JoinColumn(name = "dba_id"))
    @Column(name = "periodo")
    private Set<Periods> periods;

}
