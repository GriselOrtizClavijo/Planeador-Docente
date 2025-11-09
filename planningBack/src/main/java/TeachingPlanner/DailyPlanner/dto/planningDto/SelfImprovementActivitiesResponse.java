package TeachingPlanner.DailyPlanner.dto.planningDto;

import TeachingPlanner.DailyPlanner.entity.planning.Areas;
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
public class SelfImprovementActivitiesResponse {

    int idSiA;
    String name;
    Areas areas;
    Set<Periods> periods;

}
