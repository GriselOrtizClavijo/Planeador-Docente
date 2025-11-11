package TeachingPlanner.DailyPlanner.service.planningService;


import TeachingPlanner.DailyPlanner.dto.planningDto.CompetenciesResponse;
import TeachingPlanner.DailyPlanner.dto.planningDto.EvaluationCriteriaRequest;
import TeachingPlanner.DailyPlanner.dto.planningDto.EvaluationCriteriaResponse;
import TeachingPlanner.DailyPlanner.entity.planning.Areas;
import TeachingPlanner.DailyPlanner.entity.planning.Competencies;
import TeachingPlanner.DailyPlanner.entity.planning.EvaluationCriteria;
import TeachingPlanner.DailyPlanner.entity.planning.Learning;
import TeachingPlanner.DailyPlanner.repository.planningRespository.AreaRepository;
import TeachingPlanner.DailyPlanner.repository.planningRespository.EvaluationCriteriaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EvaluationCriteriaService {

    private final EvaluationCriteriaRepository evaluationCriteriaRepository;
    private final AreaRepository areaRepo;


   /* public List<EvaluationCriteriaResponse> list() {
        return evaluationCriteriaRepository.findAll().stream()
                .map(c -> new EvaluationCriteriaResponse(
                        c.getIdEvaluationCriteria(),
                        c.getDescription(),
                        c.getAreas(),
                        c.getPeriods()
                ))
                .toList();
    }*/

    public List<EvaluationCriteriaResponse> list(Integer areaId) {
        List<EvaluationCriteria> evaluationCriteriaList = (areaId != null)
                ? evaluationCriteriaRepository.findByAreas_IdArea (areaId)
                : evaluationCriteriaRepository.findAll();

        return evaluationCriteriaList.stream()
                .map(d -> new EvaluationCriteriaResponse(
                        d.getIdEvaluationCriteria(),
                        d.getDescription(),
                        d.getAreas(),
                        d.getPeriods()
                ))
                .toList();
    }

    public EvaluationCriteria create(EvaluationCriteriaRequest req) {
        Areas area = areaRepo.findById(req.getAreaId())
                .orElseThrow(() -> new EntityNotFoundException("Criterio de Evaluación no encontrada"));
        EvaluationCriteria c = EvaluationCriteria.builder()
                .description(req.getDescription())
                .areas(area)
                .periods(req.getPeriods())
                .build();
        return evaluationCriteriaRepository.save(c);
    }

    public EvaluationCriteria update(int id, EvaluationCriteriaRequest req) {
        EvaluationCriteria existing = evaluationCriteriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Criterio de Evaluación no encontrada"));
        Areas area = areaRepo.findById(req.getAreaId())
                .orElseThrow(() -> new EntityNotFoundException("Criterio de Evaluación no encontrada"));

        existing.setDescription(req.getDescription());
        existing.setAreas(area);
        existing.setPeriods(req.getPeriods());
        return evaluationCriteriaRepository.save(existing);
    }

    public void delete(int id) {
        EvaluationCriteria existing = evaluationCriteriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Criterio de evaluación no encontrado"));
        evaluationCriteriaRepository.delete(existing);
    }
}
