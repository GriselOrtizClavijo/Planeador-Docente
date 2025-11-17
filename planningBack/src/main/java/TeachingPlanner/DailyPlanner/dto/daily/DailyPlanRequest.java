package TeachingPlanner.DailyPlanner.dto.daily;

import TeachingPlanner.DailyPlanner.enums.StatePlan;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DailyPlanRequest {

    private String date;
    private int areaId;
    private String state; // INCOMPLETO o COMPLETO
    private Integer idDba;
    private Integer idCompetencies;
    private Integer idLearning;
    private Integer idThematicAxes;
    private Integer idEvaluationCriteria;
    private Integer idSiA;
    private List<Integer> resources;
    private String observations;


}
