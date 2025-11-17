package TeachingPlanner.DailyPlanner.controllers.planningController;

import TeachingPlanner.DailyPlanner.dto.daily.DailyPlanRequest;
import TeachingPlanner.DailyPlanner.entity.daily.DailyPlan;
import TeachingPlanner.DailyPlanner.entity.planning.Resources;
import TeachingPlanner.DailyPlanner.repository.planningRespository.ResourcesRepository;
import TeachingPlanner.DailyPlanner.service.daily.DailyPlanService;
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
@RequestMapping("/api/resources")
public class ResourcesController {


    private final ResourcesRepository resourcesRepository;
    private final DailyPlanService dailyPlanService;

    @GetMapping
    public List<Resources> list(){
        return resourcesRepository.findAll();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Resources> get(@PathVariable Integer id){
        return resourcesRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping
    public ResponseEntity<Resources> create(@RequestBody Resources body){
        System.out.println("📥 PETICIÓN RECIBIDA PARA CREATE:");
        System.out.println(body);
        Resources saved = resourcesRepository.save(body);
        return ResponseEntity.created(URI.create("/api/resources/" + saved.getIdResources())).body(saved);
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody DailyPlanRequest request) {
        System.out.println("📥 PETICIÓN RECIBIDA PARA UPDATE:");
        System.out.println(request);

        try {
            DailyPlan updated = dailyPlanService.update(id, request);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            e.printStackTrace(); // ← esto mostrará el error real
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error while updating daily plan: " + e.getMessage());
        }
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        if(!resourcesRepository.existsById(id)) return ResponseEntity.notFound().build();
        resourcesRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
