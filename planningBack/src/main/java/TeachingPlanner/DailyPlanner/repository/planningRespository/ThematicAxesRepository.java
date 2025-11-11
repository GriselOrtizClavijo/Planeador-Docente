package TeachingPlanner.DailyPlanner.repository.planningRespository;

import TeachingPlanner.DailyPlanner.entity.planning.Dba;
import TeachingPlanner.DailyPlanner.entity.planning.ThematicAxes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThematicAxesRepository extends JpaRepository<ThematicAxes,Integer> {
    List<ThematicAxes> findByAreas_IdArea(Integer areaId);
}
