package TeachingPlanner.DailyPlanner.repository.daily;

import TeachingPlanner.DailyPlanner.entity.daily.DailyPlanImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DailyPlanImageRepository extends JpaRepository<DailyPlanImage, Integer> {

    List<DailyPlanImage> findByPlan_IdPlan(int idPlan);
}
