package TeachingPlanner.DailyPlanner.controllers.daily;

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

    // 🔹 List all plans
    @GetMapping
    public List<DailyPlanResponse> list() {
        return dailyPlanService.list();
    }

    // 🔹 List plans by specific date (for daily view)
    @GetMapping("/by-date/{date}")
    public ResponseEntity<List<DailyPlanResponse>> getByDate(@PathVariable String date) {
        try {
            LocalDate localDate = LocalDate.parse(date);
            List<DailyPlanResponse> plans = dailyPlanService.listByDate(localDate);
            return ResponseEntity.ok(plans);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .build();
        }
    }

    // 🔹 Get general plan status by day (for calendar coloring)
    @GetMapping("/states")
    public ResponseEntity<Map<LocalDate, String>> getStatesByDay() {
        Map<LocalDate, String> states = dailyPlanService.getStateByDay();
        return ResponseEntity.ok(states);
    }

    @GetMapping("/eventos")
    public List<Map<String, Object>> obtenerEventos() {
        return dailyPlanService.list().stream()
                .map(p -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("idPlan", p.getIdPlan());
                    map.put("areaName", p.getArea().getName());
                    map.put("date", p.getDate().toString());
                    map.put("state", p.getState().toString());
                    return map;
                })
                .collect(Collectors.toList());
    }
}
