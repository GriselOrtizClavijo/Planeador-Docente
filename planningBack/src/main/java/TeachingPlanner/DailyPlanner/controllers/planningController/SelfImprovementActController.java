package TeachingPlanner.DailyPlanner.controllers.planningController;


import TeachingPlanner.DailyPlanner.entity.planning.SelfImprovementActivities;
import TeachingPlanner.DailyPlanner.repository.planningRespository.SelfImpActRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"}, allowCredentials = "true")
@RestController
@AllArgsConstructor
@RequestMapping("/api/learning")
public class SelfImprovementActController {

    private final SelfImpActRepository selfImpActRepository;

    @GetMapping
    public List<SelfImprovementActivities> list(){
        return selfImpActRepository.findAll();
    }


    @GetMapping("/{id}")
    public ResponseEntity<SelfImprovementActivities> get(@PathVariable Integer id){
        return selfImpActRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SelfImprovementActivities> create(@RequestBody SelfImprovementActivities body){
        SelfImprovementActivities saved = selfImpActRepository.save(body);
        return ResponseEntity.created(URI.create("/api/evaluation-criteria/" + saved.getName())).body(saved);
    }


    @PutMapping("/{id}")
    public ResponseEntity<SelfImprovementActivities> update(@PathVariable Integer id, @RequestBody SelfImprovementActivities body){
        Optional<SelfImprovementActivities> found = selfImpActRepository.findById(id);
        if(found.isEmpty()) return ResponseEntity.notFound().build();
        SelfImprovementActivities entity = found.get();
        entity.setName(body.getName());
        return ResponseEntity.ok(selfImpActRepository.save(entity));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        if(!selfImpActRepository.existsById(id)) return ResponseEntity.notFound().build();
        selfImpActRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
