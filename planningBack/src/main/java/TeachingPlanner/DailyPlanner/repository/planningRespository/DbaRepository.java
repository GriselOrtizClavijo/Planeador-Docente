package TeachingPlanner.DailyPlanner.repository.planningRespository;

import TeachingPlanner.DailyPlanner.entity.planning.Dba;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DbaRepository extends JpaRepository<Dba, Integer> {
    List<Dba> findByAreas_IdArea(Integer areaId);
}
