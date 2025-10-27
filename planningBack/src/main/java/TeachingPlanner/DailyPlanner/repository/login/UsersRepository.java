package TeachingPlanner.DailyPlanner.repository.login;

import TeachingPlanner.DailyPlanner.entity.login.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users,Integer> {

    Optional<Users> findByUsername(String username);
}
