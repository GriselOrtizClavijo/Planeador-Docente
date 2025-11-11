package TeachingPlanner.DailyPlanner.entity.planning;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "area")
public class Areas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idArea", nullable = false)
    private int idArea;

    @Column(nullable = false, unique = true)
    private String name;

    // 🔹 Relación con DBA (un área puede tener muchos DBAs)
    @OneToMany(mappedBy = "areas", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Dba> dbas;

    @JsonIgnore
    @OneToMany(mappedBy = "areas", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Competencies> competencies;

    @JsonIgnore
    @OneToMany(mappedBy = "areas", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Learning> learnings;

    @JsonIgnore
    @OneToMany(mappedBy = "areas", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<EvaluationCriteria> evaluationCriteria;

    @JsonIgnore
    @OneToMany(mappedBy = "areas", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<SelfImprovementActivities> selfImprovementActivities;

    @JsonIgnore
    @OneToMany(mappedBy = "areas", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ThematicAxes> thematicAxes;



}
