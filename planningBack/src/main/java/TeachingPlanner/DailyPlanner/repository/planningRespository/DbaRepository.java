package TeachingPlanner.DailyPlanner.repository.planningRespository;

import TeachingPlanner.DailyPlanner.entity.planning.Dba;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DbaRepository extends JpaRepository<Dba, Integer> {
    // Spring Data JPA generará automáticamente los métodos CRUD
}
