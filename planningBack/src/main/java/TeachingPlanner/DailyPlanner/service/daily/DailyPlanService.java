package TeachingPlanner.DailyPlanner.service.daily;

import TeachingPlanner.DailyPlanner.dto.daily.DailyPlanEventResponse;
import TeachingPlanner.DailyPlanner.dto.daily.DailyPlanRequest;
import TeachingPlanner.DailyPlanner.dto.daily.DailyPlanResponse;
import TeachingPlanner.DailyPlanner.entity.daily.DailyPlan;
import TeachingPlanner.DailyPlanner.entity.planning.Areas;
import TeachingPlanner.DailyPlanner.entity.planning.Resources;
import TeachingPlanner.DailyPlanner.enums.StatePlan;
import TeachingPlanner.DailyPlanner.repository.daily.DailyPlanRepository;
import TeachingPlanner.DailyPlanner.repository.planningRespository.AreaRepository;
import TeachingPlanner.DailyPlanner.repository.planningRespository.ResourcesRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DailyPlanService {

    private final DailyPlanRepository planRepo;
    private final AreaRepository areaRepo;
    private final ResourcesRepository resourcesRepository;

    // 🔹 Create new daily plan
    public DailyPlan create(DailyPlanRequest req) {
        Areas area = areaRepo.findById(req.getAreaId())
                .orElseThrow(() -> new EntityNotFoundException("Area not found"));

        DailyPlan plan = DailyPlan.builder()
                .date(LocalDate.parse(req.getDate()))
                .area(area)
                .state(StatePlan.valueOf(req.getState().toUpperCase()))
                .idDba(req.getIdDba())
                .idCompetencies(req.getIdCompetencies())
                .idLearning(req.getIdLearning())
                .idThematicAxes(req.getIdThematicAxes())
                .idEvaluationCriteria(req.getIdEvaluationCriteria())
                .idSiA(req.getIdSiA())
                .resources(
                        req.getResources() == null
                                ? List.of()
                                : req.getResources()
                                .stream()
                                .map(id -> resourcesRepository.findById(id)
                                        .orElseThrow(() -> new RuntimeException("Recurso no encontrado: " + id))
                                )
                                .toList()
                )

                .observations(req.getObservations())
                .build();

        return planRepo.save(plan);
    }

    public List<DailyPlanResponse> list() {
        return planRepo.findAll().stream()
                .map(p -> new DailyPlanResponse(
                        p.getIdPlan(),
                        p.getDate(),
                        p.getArea(),
                        p.getState(),
                        p.getObservations(),
                        p.getIdDba(),
                        p.getIdCompetencies(),
                        p.getIdLearning(),
                        p.getIdThematicAxes(),
                        p.getIdEvaluationCriteria(),
                        p.getIdSiA(),
                        p.getResources()

                )).toList();
    }

    public List<DailyPlanResponse> listByDate(LocalDate date) {
        return planRepo.findByDate(date).stream()
                .map(p -> new DailyPlanResponse(
                        p.getIdPlan(),
                        p.getDate(),
                        p.getArea(),
                        p.getState(),
                        p.getObservations(),
                        p.getIdDba(),
                        p.getIdCompetencies(),
                        p.getIdLearning(),
                        p.getIdThematicAxes(),
                        p.getIdEvaluationCriteria(),
                        p.getIdSiA(),
                        p.getResources()
                )).toList();
    }

    // 🔹 Calculate general state by day (for calendar coloring)
    public Map<LocalDate, String> getStateByDay() {
        List<DailyPlan> plans = planRepo.findAll();
        Map<LocalDate, String> stateByDay = new HashMap<>();

        plans.stream()
                .collect(Collectors.groupingBy(DailyPlan::getDate))
                .forEach((date, planList) -> {
                    boolean allComplete = planList.stream()
                            .allMatch(p -> p.getState() == StatePlan.COMPLETE);
                    stateByDay.put(date, allComplete ? "COMPLETE" : "INCOMPLETE");
                });

        return stateByDay;
    }

    public List<DailyPlanEventResponse> eventosCalendario() {
        return planRepo.findAll().stream()
                .map(p -> new DailyPlanEventResponse(
                        p.getDate(),
                        p.getArea().getName(),
                        p.getState().name()
                ))
                .toList();
    }


    public DailyPlan update(int id, DailyPlanRequest req) {

        DailyPlan plan = planRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Daily plan not found"));

        Areas area = areaRepo.findById(req.getAreaId())
                .orElse(plan.getArea());

        plan.setDate(LocalDate.parse(req.getDate()));
        plan.setArea(area);
        plan.setState(StatePlan.valueOf(req.getState().toUpperCase()));
        plan.setIdDba(req.getIdDba());
        plan.setIdCompetencies(req.getIdCompetencies());
        plan.setIdLearning(req.getIdLearning());
        plan.setIdThematicAxes(req.getIdThematicAxes());
        plan.setIdEvaluationCriteria(req.getIdEvaluationCriteria());
        plan.setIdSiA(req.getIdSiA());

        // 🔹 CORREGIDO: multi-resources
        if (req.getResources() != null) {
            List<Resources> recursoList = req.getResources().stream()
                    .map(resourceId -> resourcesRepository.findById(resourceId)
                            .orElseThrow(() -> new RuntimeException("Recurso no encontrado: " + resourceId))
                    )
                    .toList();

            plan.getResources().clear();
            plan.getResources().addAll(recursoList);
        }

        plan.setObservations(req.getObservations());

        return planRepo.save(plan);
    }


    public void delete(int id) {
        if (!planRepo.existsById(id)) {
            throw new EntityNotFoundException("Daily plan not found");
        }
        planRepo.deleteById(id);
    }






}
