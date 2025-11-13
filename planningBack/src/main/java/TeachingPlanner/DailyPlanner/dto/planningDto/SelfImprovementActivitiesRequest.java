package TeachingPlanner.DailyPlanner.dto.planningDto;

import TeachingPlanner.DailyPlanner.enums.Periods;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SelfImprovementActivitiesRequest {

    private Integer areaId;
    private String name;   // 👈 debe coincidir con el front
    private Set<Periods> periods;
}
