package TeachingPlanner.DailyPlanner.controllers.planningController;


import TeachingPlanner.DailyPlanner.entity.planning.Area;
import TeachingPlanner.DailyPlanner.repository.planningRespository.AreaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.net.URI;
import java.util.List;
import java.util.Optional;


@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"}, allowCredentials = "true")
@RestController
@RequestMapping("/api/areas")
public class AreaController {


    private final AreaRepository areaRepository;


    public AreaController(AreaRepository areaRepository) {
        this.areaRepository = areaRepository;
    }


    @GetMapping
    public List<Area> list(){
        return areaRepository.findAll();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Area> get(@PathVariable Integer id){
        return areaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping
    public ResponseEntity<Area> create(@RequestBody Area body){
// Sólo requiere "name" (es único, ver Entity)
        Area saved = areaRepository.save(body);
        return ResponseEntity.created(URI.create("/api/areas/" + saved.getIdArea())).body(saved);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Area> update(@PathVariable Integer id, @RequestBody Area body){
        Optional<Area> found = areaRepository.findById(id);
        if(found.isEmpty()) return ResponseEntity.notFound().build();
        Area entity = found.get();
        entity.setName(body.getName());
        return ResponseEntity.ok(areaRepository.save(entity));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        if(!areaRepository.existsById(id)) return ResponseEntity.notFound().build();
        areaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}