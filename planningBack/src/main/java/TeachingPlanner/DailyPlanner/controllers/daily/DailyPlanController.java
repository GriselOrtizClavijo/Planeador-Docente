package TeachingPlanner.DailyPlanner.controllers.daily;

import TeachingPlanner.DailyPlanner.dto.daily.DailyPlanEventResponse;
import TeachingPlanner.DailyPlanner.dto.daily.DailyPlanRequest;
import TeachingPlanner.DailyPlanner.dto.daily.DailyPlanResponse;
import TeachingPlanner.DailyPlanner.entity.daily.DailyPlan;
import TeachingPlanner.DailyPlanner.service.daily.DailyPlanService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(
        origins = {"http://localhost:5173", "http://localhost:3000"},
        allowCredentials = "true"
)
@RestController
@AllArgsConstructor
@RequestMapping("/api/daily-plan")
public class DailyPlanController {

    private final DailyPlanService dailyPlanService;

    // 🔹 Create new daily plan
    @PostMapping
    public ResponseEntity<?> create(@RequestBody DailyPlanRequest request) {
        try {
            DailyPlan created = dailyPlanService.create(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error while creating daily plan: " + e.getMessage());
        }
    }

    // 🔹 Update an existing daily plan
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody DailyPlanRequest request) {
        try {
            DailyPlan updated = dailyPlanService.update(id,request);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error while updating daily plan: " + e.getMessage());
        }
    }




    // 🔹 List all plans
    @GetMapping
    public List<DailyPlanResponse> list() {
        return dailyPlanService.list();
    }

    // 🔹 List plans by specific date (for daily view)
    // 🔹 List plans by specific date (for daily view)
    @GetMapping("/by-date/{fecha}")
    public ResponseEntity<List<DailyPlanResponse>> getByDate(@PathVariable LocalDate fecha) {
        try {
            List<DailyPlanResponse> plans = dailyPlanService.listByDate(fecha);
            return ResponseEntity.ok(plans);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


    // 🔹 Get general plan status by day (for calendar coloring)
    @GetMapping("/states")
    public ResponseEntity<Map<LocalDate, String>> getStatesByDay() {
        Map<LocalDate, String> states = dailyPlanService.getStateByDay();
        return ResponseEntity.ok(states);
    }

    @GetMapping("/events")
    public List<DailyPlanEventResponse> eventosDelCalendario() {
        return dailyPlanService.eventosCalendario();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        try {
            dailyPlanService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error deleting daily plan: " + e.getMessage());
        }
    }


}
