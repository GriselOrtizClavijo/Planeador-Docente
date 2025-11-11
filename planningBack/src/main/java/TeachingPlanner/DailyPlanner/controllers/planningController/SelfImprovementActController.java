package TeachingPlanner.DailyPlanner.controllers.planningController;


import TeachingPlanner.DailyPlanner.dto.planningDto.SelfImprovementActivitiesRequest;
import TeachingPlanner.DailyPlanner.dto.planningDto.SelfImprovementActivitiesResponse;
import TeachingPlanner.DailyPlanner.entity.planning.SelfImprovementActivities;
import TeachingPlanner.DailyPlanner.service.planningService.SelfImprovementActService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"}, allowCredentials = "true")
@RestController
@AllArgsConstructor
@RequestMapping("/api/self-improvement-activities")
public class SelfImprovementActController {

    private final SelfImprovementActService selfImprovementActService;

    @GetMapping
    public List<SelfImprovementActivitiesResponse> list(@RequestParam(required = false) Integer areaId){
        return selfImprovementActService.list(areaId);
    }


    @PostMapping
    public ResponseEntity<?> create(@RequestBody SelfImprovementActivitiesRequest request) {
        try {
            SelfImprovementActivities created = selfImprovementActService.create(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al crear actividad de profundización: " + e.getMessage());
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody SelfImprovementActivitiesRequest request) {
        try {
            SelfImprovementActivities updated = selfImprovementActService.update(id, request);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error al actualizar actividad de profundización: " + e.getMessage());
        }
    }

    // 🔹 4. Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        try {
            selfImprovementActService.delete(id);
            return ResponseEntity.ok("Actividad de profundización eliminada correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error al eliminar actividad de profundización: " + e.getMessage());
        }
    }
}
