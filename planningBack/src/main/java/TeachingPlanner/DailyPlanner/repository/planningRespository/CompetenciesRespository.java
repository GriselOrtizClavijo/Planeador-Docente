package TeachingPlanner.DailyPlanner.repository.planningRespository;

import TeachingPlanner.DailyPlanner.entity.planning.Competencies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompetenciesRespository extends JpaRepository<Competencies, Integer> {
    List<Competencies> findByAreas_IdArea(Integer areaId);
}
