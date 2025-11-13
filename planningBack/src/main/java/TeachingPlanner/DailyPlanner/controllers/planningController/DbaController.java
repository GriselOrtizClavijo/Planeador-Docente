package TeachingPlanner.DailyPlanner.controllers.planningController;

import TeachingPlanner.DailyPlanner.dto.planningDto.DbaRequest;
import TeachingPlanner.DailyPlanner.dto.planningDto.DbaResponse;
import TeachingPlanner.DailyPlanner.entity.planning.Dba;
import TeachingPlanner.DailyPlanner.service.planningService.DbaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:5173",  "http://localhost:3000"}, allowCredentials = "true") // Ajusta según tu frontend
@RestController
@RequestMapping("/api/dbas")
public class DbaController {

    private final DbaService dbaService;

    public DbaController(DbaService dbaService) {
        this.dbaService = dbaService;
    }

    // 🔹 1. Listar todos los DBA
    @GetMapping
    public List<DbaResponse> list(@RequestParam(required = false) Integer areaId) {
        return dbaService.list(areaId);
    }

    /*@GetMapping
    public List<DbaResponse> list(@RequestParam(required = false) Integer areaId) {
        if (areaId != null) {
            return dbaRepository.findByAreas_IdArea(areaId)
                    .stream()
                    .map(d -> new DbaResponse(
                            d.getIdDba(),
                            d.getDescription(),
                            d.getAreas(),
                            d.getPeriods()
                    ))
                    .toList();
        }
        return dbaService.list();
    }*/

    // 🔹 2. Crear nuevo DBA
    @PostMapping
    public ResponseEntity<?> create(@RequestBody DbaRequest request) {
        try {
            Dba created = dbaService.create(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al crear el DBA: " + e.getMessage());
        }
    }

    // 🔹 3. Editar un DBA existente
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody DbaRequest request) {
        try {
            Dba updated = dbaService.update(id, request);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error al actualizar el DBA: " + e.getMessage());
        }
    }

    // 🔹 4. Eliminar un DBA
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        try {
            dbaService.delete(id);
            return ResponseEntity.ok("DBA eliminado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error al eliminar el DBA: " + e.getMessage());
        }
    }
}
