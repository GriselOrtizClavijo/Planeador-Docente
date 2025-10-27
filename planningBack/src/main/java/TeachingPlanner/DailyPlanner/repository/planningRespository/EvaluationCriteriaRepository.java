package TeachingPlanner.DailyPlanner.repository.planningRespository;

import TeachingPlanner.DailyPlanner.entity.planning.EvaluationCriteria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvaluationCriteriaRepository extends JpaRepository<EvaluationCriteria,Integer> {
}
