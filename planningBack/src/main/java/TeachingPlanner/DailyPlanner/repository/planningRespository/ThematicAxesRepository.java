package TeachingPlanner.DailyPlanner.repository.planningRespository;

import TeachingPlanner.DailyPlanner.entity.planning.ThematicAxes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThematicAxesRepository extends JpaRepository<ThematicAxes,Integer> {
}
