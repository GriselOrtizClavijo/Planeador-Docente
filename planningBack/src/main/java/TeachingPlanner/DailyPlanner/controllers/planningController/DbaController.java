package TeachingPlanner.DailyPlanner.controllers.planningController;

import TeachingPlanner.DailyPlanner.entity.planning.Dba;
import TeachingPlanner.DailyPlanner.repository.planningRespository.DbaRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"}, allowCredentials = "true")
@RestController
@AllArgsConstructor
@RequestMapping("/api/dbas")
public class DbaController {

    private final DbaRepository dbaRepository;

    @GetMapping
    public List<Dba> list(){
        return dbaRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dba> get(@PathVariable Integer id){
        return dbaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping
    public ResponseEntity<Dba> create(@RequestBody Dba body){
// Sólo requiere "name" (es único, ver Entity)
        Dba saved = dbaRepository.save(body);
        return ResponseEntity.created(URI.create("/api/dbas/" + saved.getIdDba())).body(saved);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Dba> update(@PathVariable Integer id, @RequestBody Dba body){
        Optional<Dba> found = dbaRepository.findById(id);
        if(found.isEmpty()) return ResponseEntity.notFound().build();
        Dba entity = found.get();
        entity.setDescription(body.getDescription());
        return ResponseEntity.ok(dbaRepository.save(entity));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        if(!dbaRepository.existsById(id)) return ResponseEntity.notFound().build();
        dbaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
