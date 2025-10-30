package TeachingPlanner.DailyPlanner.repository.planningRespository;

import TeachingPlanner.DailyPlanner.entity.planning.Learning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LearningRepository extends JpaRepository<Learning,Integer> {
}
