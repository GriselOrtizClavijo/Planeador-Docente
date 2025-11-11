package TeachingPlanner.DailyPlanner.controllers.planningController;


import TeachingPlanner.DailyPlanner.dto.planningDto.EvaluationCriteriaRequest;
import TeachingPlanner.DailyPlanner.dto.planningDto.EvaluationCriteriaResponse;
import TeachingPlanner.DailyPlanner.entity.planning.EvaluationCriteria;
import TeachingPlanner.DailyPlanner.service.planningService.EvaluationCriteriaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"}, allowCredentials = "true")
@RestController
@AllArgsConstructor
@RequestMapping("/api/evaluation-criteria")
public class EvaluationCriteriaController {

    private final EvaluationCriteriaService evaluationCriteriaService;

    @GetMapping
    public List<EvaluationCriteriaResponse> list(@RequestParam(required = false) Integer areaId){
        return evaluationCriteriaService.list(areaId);
    }


    @PostMapping
    public ResponseEntity<?> create(@RequestBody EvaluationCriteriaRequest request) {
        try {
            EvaluationCriteria created = evaluationCriteriaService.create(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al crear el criterio de evaluación: " + e.getMessage());
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody EvaluationCriteriaRequest request) {
        try {
            EvaluationCriteria updated = evaluationCriteriaService.update(id, request);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error al actualizar el criterio de evaluación: " + e.getMessage());
        }
    }

    // 🔹 4. Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        try {
            evaluationCriteriaService.delete(id);
            return ResponseEntity.ok("Criterio de evaluación eliminada correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error al eliminar el criterio de evaluación: " + e.getMessage());
        }
    }

}
