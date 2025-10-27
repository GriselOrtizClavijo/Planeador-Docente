package TeachingPlanner.DailyPlanner.repository.planningRespository;

import TeachingPlanner.DailyPlanner.entity.planning.Period;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeriodRepository extends JpaRepository<Period, Integer> {
}
