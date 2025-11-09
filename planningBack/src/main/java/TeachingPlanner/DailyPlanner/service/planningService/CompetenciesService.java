package TeachingPlanner.DailyPlanner.service.planningService;

import TeachingPlanner.DailyPlanner.dto.planningDto.CompetenciesRequest;
import TeachingPlanner.DailyPlanner.dto.planningDto.CompetenciesResponse;
import TeachingPlanner.DailyPlanner.entity.planning.Areas;
import TeachingPlanner.DailyPlanner.entity.planning.Competencies;
import TeachingPlanner.DailyPlanner.entity.planning.EvaluationCriteria;
import TeachingPlanner.DailyPlanner.repository.planningRespository.AreaRepository;
import TeachingPlanner.DailyPlanner.repository.planningRespository.CompetenciesRespository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompetenciesService {

    private final CompetenciesRespository repo;
    private final AreaRepository areaRepo;

    public CompetenciesService(CompetenciesRespository repo, AreaRepository areaRepo) {
        this.repo = repo;
        this.areaRepo = areaRepo;
    }

    public List<CompetenciesResponse> list() {
        return repo.findAll().stream()
                .map(c -> new CompetenciesResponse(
                        c.getIdCompetencies(),
                        c.getName(),
                        c.getAreas(),
                        c.getPeriods()
                ))
                .toList();
    }

    public Competencies create(CompetenciesRequest req) {
        Areas area = areaRepo.findById(req.getAreaId())
                .orElseThrow(() -> new EntityNotFoundException("Competencia no encontrada"));
        Competencies c = Competencies.builder()
                .name(req.getName())
                .areas(area)
                .periods(req.getPeriods())
                .build();
        return repo.save(c);
    }

    public Competencies update(int id, CompetenciesRequest req) {
        Competencies existing = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Competencia no encontrada"));
        Areas area = areaRepo.findById(req.getAreaId())
                .orElseThrow(() -> new EntityNotFoundException("Área no encontrada"));

        existing.setName(req.getName());
        existing.setAreas(area);
        existing.setPeriods(req.getPeriods());
        return repo.save(existing);
    }

    public void delete(int id) {
        Competencies existing = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Criterio de evaluación no encontrado"));
        repo.delete(existing);
    }
}
