package TeachingPlanner.DailyPlanner.controllers.planningController;

import TeachingPlanner.DailyPlanner.entity.planning.Resources;
import TeachingPlanner.DailyPlanner.repository.planningRespository.ResourcesRepository;
import lombok.AllArgsConstructor;
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
        Resources saved = resourcesRepository.save(body);
        return ResponseEntity.created(URI.create("/api/resources/" + saved.getIdResources())).body(saved);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Resources> update(@PathVariable Integer id, @RequestBody Resources body){
        Optional<Resources> found = resourcesRepository.findById(id);
        if(found.isEmpty()) return ResponseEntity.notFound().build();
        Resources entity = found.get();
        entity.setName(body.getName());
        return ResponseEntity.ok(resourcesRepository.save(entity));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        if(!resourcesRepository.existsById(id)) return ResponseEntity.notFound().build();
        resourcesRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
