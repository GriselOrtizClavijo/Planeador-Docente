package TeachingPlanner.DailyPlanner.repository.login;

import TeachingPlanner.DailyPlanner.entity.login.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<UsersEntity,Integer> {

    Optional<UsersEntity> findByUsername(String username);
}
