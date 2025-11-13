package TeachingPlanner.DailyPlanner.service.daily;

import TeachingPlanner.DailyPlanner.dto.daily.DailyPlanRequest;
import TeachingPlanner.DailyPlanner.dto.daily.DailyPlanResponse;
import TeachingPlanner.DailyPlanner.entity.daily.DailyPlan;
import TeachingPlanner.DailyPlanner.entity.planning.Areas;
import TeachingPlanner.DailyPlanner.enums.StatePlan;
import TeachingPlanner.DailyPlanner.repository.daily.DailyPlanRepository;
import TeachingPlanner.DailyPlanner.repository.planningRespository.AreaRepository;
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
                .idResources(req.getIdResources())
                .observations(req.getObservations())
                .build();

        return planRepo.save(plan);
    }

    // 🔹 List all plans
    public List<DailyPlanResponse> list() {
        return planRepo.findAll().stream()
                .map(p -> new DailyPlanResponse(
                        p.getIdPlan(),
                        p.getDate(),
                        p.getArea(),
                        p.getState(),
                        p.getObservations()
                )).toList();
    }

    // 🔹 List plans by date
    public List<DailyPlanResponse> listByDate(LocalDate date) {
        return planRepo.findByDate(date).stream()
                .map(p -> new DailyPlanResponse(
                        p.getIdPlan(),
                        p.getDate(),
                        p.getArea(),
                        p.getState(),
                        p.getObservations()
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
}
