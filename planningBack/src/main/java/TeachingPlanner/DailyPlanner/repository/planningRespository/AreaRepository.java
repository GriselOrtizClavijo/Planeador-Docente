package TeachingPlanner.DailyPlanner.repository.planningRespository;

import TeachingPlanner.DailyPlanner.entity.planning.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AreaRepository extends JpaRepository<Area, Integer> {
    // Spring Data JPA generará automáticamente los métodos CRUD
}
