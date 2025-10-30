package TeachingPlanner.DailyPlanner.controllers.planningController;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"}, allowCredentials = "true")
@RestController
@RequestMapping("/api/learning")
public class LearningController {
}
