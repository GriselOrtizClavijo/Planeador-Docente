package TeachingPlanner.DailyPlanner.service.planningService;

import TeachingPlanner.DailyPlanner.dto.planningDto.CompetenciesResponse;
import TeachingPlanner.DailyPlanner.dto.planningDto.LearningRequest;
import TeachingPlanner.DailyPlanner.dto.planningDto.LearningResponse;
import TeachingPlanner.DailyPlanner.entity.planning.Areas;
import TeachingPlanner.DailyPlanner.entity.planning.Competencies;
import TeachingPlanner.DailyPlanner.entity.planning.Dba;
import TeachingPlanner.DailyPlanner.entity.planning.Learning;
import TeachingPlanner.DailyPlanner.repository.planningRespository.AreaRepository;
import TeachingPlanner.DailyPlanner.repository.planningRespository.LearningRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LearningService {

    private final LearningRepository repo;
    private final AreaRepository areaRepo;

    public LearningService(LearningRepository repo, AreaRepository areaRepo) {
        this.repo = repo;
        this.areaRepo = areaRepo;
    }

    /*public List<LearningResponse> list() {
        return repo.findAll().stream()
                .map(c -> new LearningResponse(
                        c.getIdLearning(),
                        c.getName(),
                        c.getAreas(),
                        c.getPeriods()
                ))
                .toList();
    }*/

    public List<LearningResponse> list(Integer areaId) {
        List<Learning> learningList = (areaId != null)
                ? repo.findByAreas_IdArea(areaId)
                : repo.findAll();

        return learningList.stream()
                .map(d -> new LearningResponse(
                        d.getIdLearning(),
                        d.getName(),
                        d.getAreas(),
                        d.getPeriods()
                ))
                .toList();
    }


    public Learning create(LearningRequest req) {
        Areas area = areaRepo.findById(req.getAreaId())
                .orElseThrow(() -> new EntityNotFoundException("Aprendizaje no encontrado"));
        Learning c = Learning.builder()
                .name(req.getName())
                .areas(area)
                .periods(req.getPeriods())
                .build();
        return repo.save(c);
    }

    public Learning update(int id, LearningRequest req) {
        Learning existing = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aprendizaje no encontrado"));
        Areas area = areaRepo.findById(req.getAreaId())
                .orElseThrow(() -> new EntityNotFoundException("Área no encontrado"));

        existing.setName(req.getName());
        existing.setAreas(area);
        existing.setPeriods(req.getPeriods());
        return repo.save(existing);
    }

    public void delete(int id) {
        Learning existing = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aprendizaje no encontrado"));
        repo.delete(existing);
    }

}
