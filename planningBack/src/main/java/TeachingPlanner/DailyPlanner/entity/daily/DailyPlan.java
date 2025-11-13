package TeachingPlanner.DailyPlanner.entity.daily;

import TeachingPlanner.DailyPlanner.entity.planning.Areas;
import TeachingPlanner.DailyPlanner.enums.StatePlan;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "dailyPlan")
public class DailyPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPlan;

    private LocalDate date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idArea")
    private Areas area;

    @Enumerated(EnumType.STRING)
    private StatePlan state; // INCOMPLETO o COMPLETO

    @Column(length = 1000)
    private String observations;


    // 🔹 Asociaciones opcionales con otros catálogos
    private Integer idDba;
    private Integer idCompetencies;
    private Integer idLearning;
    private Integer idThematicAxes;
    private Integer idEvaluationCriteria;
    private Integer idSiA;
    private Integer idResources;

}
