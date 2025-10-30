package TeachingPlanner.DailyPlanner.controllers.planningController;


import TeachingPlanner.DailyPlanner.entity.planning.Learning;
import TeachingPlanner.DailyPlanner.repository.planningRespository.LearningRepository;
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
public class LearningController {

    private final LearningRepository learningRepository;

    @GetMapping
    public List<Learning> list(){
        return learningRepository.findAll();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Learning> get(@PathVariable Integer id){
        return learningRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping
    public ResponseEntity<Learning> create(@RequestBody Learning body){
        Learning saved = learningRepository.save(body);
        return ResponseEntity.created(URI.create("/api/learning/" + saved.getIdLearning())).body(saved);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Learning> update(@PathVariable Integer id, @RequestBody Learning body){
        Optional<Learning> found = learningRepository.findById(id);
        if(found.isEmpty()) return ResponseEntity.notFound().build();
        Learning entity = found.get();
        entity.setName(body.getName());
        return ResponseEntity.ok(learningRepository.save(entity));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        if(!learningRepository.existsById(id)) return ResponseEntity.notFound().build();
        learningRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
