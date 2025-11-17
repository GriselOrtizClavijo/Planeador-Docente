package TeachingPlanner.DailyPlanner.dto.daily;

import TeachingPlanner.DailyPlanner.entity.planning.Areas;
import TeachingPlanner.DailyPlanner.entity.planning.Resources;
import TeachingPlanner.DailyPlanner.enums.StatePlan;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DailyPlanResponse {

    private int idPlan;
    private LocalDate date;
    private Areas area;
    private StatePlan state;
    private String observations;

    private Integer idDba;
    private Integer idCompetencies;
    private Integer idLearning;
    private Integer idThematicAxes;
    private Integer idEvaluationCriteria;
    private Integer idSiA;
    List<Resources> resources;


}
