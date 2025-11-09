package TeachingPlanner.DailyPlanner.controllers.planningController;

import TeachingPlanner.DailyPlanner.dto.planningDto.CompetenciesRequest;
import TeachingPlanner.DailyPlanner.dto.planningDto.CompetenciesResponse;
import TeachingPlanner.DailyPlanner.dto.planningDto.DbaRequest;
import TeachingPlanner.DailyPlanner.entity.planning.Competencies;
import TeachingPlanner.DailyPlanner.entity.planning.Dba;
import TeachingPlanner.DailyPlanner.repository.planningRespository.CompetenciesRespository;
import TeachingPlanner.DailyPlanner.service.planningService.CompetenciesService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"}, allowCredentials = "true")
@RestController
@AllArgsConstructor
@RequestMapping("/api/competencies")
public class CompetenciesController {


    private final CompetenciesService competenciesService;

    @GetMapping
    public List<CompetenciesResponse> list(){
        return competenciesService.list();
    }


    @PostMapping
    public ResponseEntity<?> create(@RequestBody CompetenciesRequest request) {
        try {
            Competencies created = competenciesService.create(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al crear la competencia: " + e.getMessage());
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody CompetenciesRequest request) {
        try {
            Competencies updated = competenciesService.update(id, request);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error al actualizar la competencia: " + e.getMessage());
        }
    }

    // 🔹 4. Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        try {
            competenciesService.delete(id);
            return ResponseEntity.ok("Competencia eliminada correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error al eliminar la competencia: " + e.getMessage());
        }
    }

}
