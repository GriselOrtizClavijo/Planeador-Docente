package TeachingPlanner.DailyPlanner.repository.planningRespository;

import TeachingPlanner.DailyPlanner.entity.planning.Dba;
import TeachingPlanner.DailyPlanner.entity.planning.SelfImprovementActivities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SelfImpActRepository extends JpaRepository<SelfImprovementActivities,Integer> {
    List<SelfImprovementActivities> findByAreas_IdArea(Integer areaId);
}
