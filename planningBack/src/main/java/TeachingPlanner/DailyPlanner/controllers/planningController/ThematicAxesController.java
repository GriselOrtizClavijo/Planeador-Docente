package TeachingPlanner.DailyPlanner.controllers.planningController;

import TeachingPlanner.DailyPlanner.dto.planningDto.ThematicAxesRequest;
import TeachingPlanner.DailyPlanner.dto.planningDto.ThematicAxesResponse;
import TeachingPlanner.DailyPlanner.entity.planning.ThematicAxes;
import TeachingPlanner.DailyPlanner.service.planningService.ThematicalAxesService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"}, allowCredentials = "true")
@RestController
@AllArgsConstructor
@RequestMapping("/api/thematic-axes")
public class ThematicAxesController {

    private final ThematicalAxesService thematicalAxesService;

    @GetMapping
    public List<ThematicAxesResponse> list(@RequestParam(required = false) Integer areaId){
        return thematicalAxesService.list(areaId);
    }


    @PostMapping
    public ResponseEntity<?> create(@RequestBody ThematicAxesRequest request) {
        try {
            ThematicAxes created = thematicalAxesService.create(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al crear el aprendizaje: " + e.getMessage());
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody ThematicAxesRequest request) {
        try {
            ThematicAxes updated = thematicalAxesService.update(id, request);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error al actualizar el eje temático: " + e.getMessage());
        }
    }

    // 🔹 4. Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        try {
            thematicalAxesService.delete(id);
            return ResponseEntity.ok("Eje temático eliminada correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error al eliminar el eje temático: " + e.getMessage());
        }
    }
}
