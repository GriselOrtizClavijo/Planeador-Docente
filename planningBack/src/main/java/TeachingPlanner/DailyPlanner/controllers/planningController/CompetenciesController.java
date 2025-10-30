package TeachingPlanner.DailyPlanner.controllers.planningController;

import TeachingPlanner.DailyPlanner.entity.planning.Competencies;
import TeachingPlanner.DailyPlanner.repository.planningRespository.CompetenciesRespository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"}, allowCredentials = "true")
@RestController
@AllArgsConstructor
@RequestMapping("/api/competencies")
public class CompetenciesController {


    private final CompetenciesRespository competenciesRespository;

    @GetMapping
    public List<Competencies> list(){
        return competenciesRespository.findAll();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Competencies> get(@PathVariable Integer id){
        return competenciesRespository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping
    public ResponseEntity<Competencies> create(@RequestBody Competencies body){
// Sólo requiere "name" (es único, ver Entity)
        Competencies saved = competenciesRespository.save(body);
        return ResponseEntity.created(URI.create("/api/competencies/" + saved.getIdCompetencies())).body(saved);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Competencies> update(@PathVariable Integer id, @RequestBody Competencies body){
        Optional<Competencies> found = competenciesRespository.findById(id);
        if(found.isEmpty()) return ResponseEntity.notFound().build();
        Competencies entity = found.get();
        entity.setName(body.getName());
        return ResponseEntity.ok(competenciesRespository.save(entity));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        if(!competenciesRespository.existsById(id)) return ResponseEntity.notFound().build();
        competenciesRespository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
