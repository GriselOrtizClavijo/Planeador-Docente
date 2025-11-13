package TeachingPlanner.DailyPlanner.dto.daily;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Integer idResources;
    private String observations;


}
