package TeachingPlanner.DailyPlanner.controllers.planningController;

import TeachingPlanner.DailyPlanner.entity.planning.EvaluationCriteria;
import TeachingPlanner.DailyPlanner.repository.planningRespository.EvaluationCriteriaRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"}, allowCredentials = "true")
@RestController
@AllArgsConstructor
@RequestMapping("/api/evaluation-criteria")
public class EvaluationCriteriaController {


    private final EvaluationCriteriaRepository evaluationCriteriaRepository;

    @GetMapping
    public List<EvaluationCriteria> list(){
        return evaluationCriteriaRepository.findAll();
    }


    @GetMapping("/{id}")
    public ResponseEntity<EvaluationCriteria> get(@PathVariable Integer id){
        return evaluationCriteriaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping
    public ResponseEntity<EvaluationCriteria> create(@RequestBody EvaluationCriteria body){
        EvaluationCriteria saved = evaluationCriteriaRepository.save(body);
        return ResponseEntity.created(URI.create("/api/evaluation-criteria/" + saved.getIdEvaluationCriteria())).body(saved);
    }


    @PutMapping("/{id}")
    public ResponseEntity<EvaluationCriteria> update(@PathVariable Integer id, @RequestBody EvaluationCriteria body){
        Optional<EvaluationCriteria> found = evaluationCriteriaRepository.findById(id);
        if(found.isEmpty()) return ResponseEntity.notFound().build();
        EvaluationCriteria entity = found.get();
        entity.setDescription(body.getDescription());
        return ResponseEntity.ok(evaluationCriteriaRepository.save(entity));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        if(!evaluationCriteriaRepository.existsById(id)) return ResponseEntity.notFound().build();
        evaluationCriteriaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
