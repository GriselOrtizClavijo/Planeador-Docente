package TeachingPlanner.DailyPlanner.repository.daily;

import TeachingPlanner.DailyPlanner.entity.daily.DailyPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DailyPlanRepository extends JpaRepository<DailyPlan, Integer> {
    List<DailyPlan> findByDate(LocalDate date);
}
