package TeachingPlanner.DailyPlanner.controllers.planningController;


import TeachingPlanner.DailyPlanner.dto.planningDto.LearningRequest;
import TeachingPlanner.DailyPlanner.dto.planningDto.LearningResponse;
import TeachingPlanner.DailyPlanner.entity.planning.Learning;
import TeachingPlanner.DailyPlanner.service.planningService.LearningService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"}, allowCredentials = "true")
@RestController
@AllArgsConstructor
@RequestMapping("/api/learning")
public class LearningController {

    private final LearningService learningService;

    @GetMapping
    public List<LearningResponse> list(@RequestParam(required = false) Integer areaId){
        return learningService.list(areaId);
    }


    @PostMapping
    public ResponseEntity<?> create(@RequestBody LearningRequest request) {
        try {
            Learning created = learningService.create(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al crear el aprendizaje: " + e.getMessage());
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody LearningRequest request) {
        try {
            Learning updated = learningService.update(id, request);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error al actualizar el aprendizaje: " + e.getMessage());
        }
    }

    // 🔹 4. Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        try {
            learningService.delete(id);
            return ResponseEntity.ok("Aprendizaje eliminada correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error al eliminar el aprendizaje: " + e.getMessage());
        }
    }

}
