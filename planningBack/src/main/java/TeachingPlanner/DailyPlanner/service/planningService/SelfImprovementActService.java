package TeachingPlanner.DailyPlanner.service.planningService;

import TeachingPlanner.DailyPlanner.dto.planningDto.SelfImprovementActivitiesRequest;
import TeachingPlanner.DailyPlanner.dto.planningDto.SelfImprovementActivitiesResponse;
import TeachingPlanner.DailyPlanner.entity.planning.Areas;
import TeachingPlanner.DailyPlanner.entity.planning.Learning;
import TeachingPlanner.DailyPlanner.entity.planning.SelfImprovementActivities;
import TeachingPlanner.DailyPlanner.repository.planningRespository.AreaRepository;
import TeachingPlanner.DailyPlanner.repository.planningRespository.SelfImpActRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SelfImprovementActService {

    private final SelfImpActRepository repo;
    private final AreaRepository areaRepo;

    public SelfImprovementActService(SelfImpActRepository repo, AreaRepository areaRepo) {
        this.repo = repo;
        this.areaRepo = areaRepo;
    }

    public List<SelfImprovementActivitiesResponse> list() {
        return repo.findAll().stream()
                .map(c -> new SelfImprovementActivitiesResponse(
                        c.getIdSiA(),
                        c.getName(),
                        c.getAreas(),
                        c.getPeriods()
                ))
                .toList();
    }

    public SelfImprovementActivities create(SelfImprovementActivitiesRequest req) {
        Areas area = areaRepo.findById(req.getAreaId())
                .orElseThrow(() -> new EntityNotFoundException("Actividad de profundización no encontrada"));
        SelfImprovementActivities c = SelfImprovementActivities.builder()
                .name(req.getName())
                .areas(area)
                .periods(req.getPeriods())
                .build();
        return repo.save(c);
    }

    public SelfImprovementActivities update(int id, SelfImprovementActivitiesRequest req) {
        SelfImprovementActivities existing = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Actividad de profundización no encontrada"));
        Areas area = areaRepo.findById(req.getAreaId())
                .orElseThrow(() -> new EntityNotFoundException("Área no encontrada"));

        existing.setName(req.getName());
        existing.setAreas(area);
        existing.setPeriods(req.getPeriods());
        return repo.save(existing);
    }

    public void delete(int id) {
        SelfImprovementActivities existing = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Actividad de profundización no encontrado"));
        repo.delete(existing);
    }
}
