package TeachingPlanner.DailyPlanner.service.planningService;


import TeachingPlanner.DailyPlanner.dto.planningDto.DbaResponse;
import TeachingPlanner.DailyPlanner.dto.planningDto.ThematicAxesRequest;
import TeachingPlanner.DailyPlanner.dto.planningDto.ThematicAxesResponse;
import TeachingPlanner.DailyPlanner.entity.planning.Areas;
import TeachingPlanner.DailyPlanner.entity.planning.Dba;
import TeachingPlanner.DailyPlanner.entity.planning.Learning;
import TeachingPlanner.DailyPlanner.entity.planning.ThematicAxes;
import TeachingPlanner.DailyPlanner.repository.planningRespository.AreaRepository;
import TeachingPlanner.DailyPlanner.repository.planningRespository.ThematicAxesRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThematicalAxesService {

    private final ThematicAxesRepository repo;
    private final AreaRepository areaRepo;

    public ThematicalAxesService(ThematicAxesRepository repo, AreaRepository areaRepo) {
        this.repo = repo;
        this.areaRepo = areaRepo;
    }

    /*public List<ThematicAxesResponse> list() {
        return repo.findAll()
                .stream()
                .map(c -> new ThematicAxesResponse(
                        c.getIdThematicAxes(),
                        c.getName(),
                        c.getAreas(),
                        c.getPeriods()
                ))
                .toList();
    }*/

    public List<ThematicAxesResponse> list(Integer areaId) {
        List<ThematicAxes> thematicAxesList = (areaId != null)
                ? repo.findByAreas_IdArea(areaId)
                : repo.findAll();

        return thematicAxesList.stream()
                .map(d -> new ThematicAxesResponse(
                        d.getIdThematicAxes(),
                        d.getName(),
                        d.getAreas(),
                        d.getPeriods()
                ))
                .toList();
    }

    public ThematicAxes create(ThematicAxesRequest req) {
        Areas area = areaRepo.findById(req.getAreaId())
                .orElseThrow(() -> new EntityNotFoundException("Eje Temático no encontrado"));
        ThematicAxes c = ThematicAxes.builder()
                .name(req.getName())
                .areas(area)
                .periods(req.getPeriods())
                .build();
        return repo.save(c);
    }

    public ThematicAxes update(int id, ThematicAxesRequest req) {
        ThematicAxes existing = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Eje Temático no encontrada"));
        Areas area = areaRepo.findById(req.getAreaId())
                .orElseThrow(() -> new EntityNotFoundException("Área no encontrada"));

        existing.setName(req.getName());
        existing.setAreas(area);
        existing.setPeriods(req.getPeriods());

        return repo.save(existing);
    }

    public void delete(int id) {
        ThematicAxes existing = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Eje Temático no encontrado"));
        repo.delete(existing);
    }
}
