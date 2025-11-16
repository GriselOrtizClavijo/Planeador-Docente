package TeachingPlanner.DailyPlanner.dto.daily;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class DailyPlanEventResponse {
    private LocalDate date;
    private String areaName;
    private String state; // COMPLETO o INCOMPLETO
}