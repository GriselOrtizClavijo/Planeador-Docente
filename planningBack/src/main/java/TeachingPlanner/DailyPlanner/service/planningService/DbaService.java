package TeachingPlanner.DailyPlanner.service.planningService;

import TeachingPlanner.DailyPlanner.dto.planningDto.DbaRequest;
import TeachingPlanner.DailyPlanner.dto.planningDto.DbaResponse;
import TeachingPlanner.DailyPlanner.entity.planning.Areas;
import TeachingPlanner.DailyPlanner.entity.planning.Dba;
import TeachingPlanner.DailyPlanner.repository.planningRespository.AreaRepository;
import TeachingPlanner.DailyPlanner.repository.planningRespository.DbaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DbaService {

    private final DbaRepository dbaRepository;
    private final AreaRepository areaRepository;

    public DbaService(DbaRepository dbaRepository, AreaRepository areaRepository) {
        this.dbaRepository = dbaRepository;
        this.areaRepository = areaRepository;
    }

    // 🔹 Listar todos
    /*public List<DbaResponse> list() {
        return dbaRepository.findAll()
                .stream()
                .map(d -> new DbaResponse(
                        d.getIdDba(),
                        d.getDescription(),
                        d.getAreas(),
                        d.getPeriods()))
                .toList();
    }*/

    public List<DbaResponse> list(Integer areaId) {
        List<Dba> dbaList = (areaId != null)
                ? dbaRepository.findByAreas_IdArea(areaId)
                : dbaRepository.findAll();

        return dbaList.stream()
                .map(d -> new DbaResponse(
                        d.getIdDba(),
                        d.getDescription(),
                        d.getAreas(),
                        d.getPeriods()
                ))
                .toList();
    }

    // 🔹 Crear nuevo
    public Dba create(DbaRequest request) {
        Areas area = areaRepository.findById(request.getAreaId())
                .orElseThrow(() -> new EntityNotFoundException("Área no encontrada"));

        Dba dba = Dba.builder()
                .description(request.getDescription())
                .areas(area)
                .periods(request.getPeriods()) // ✅ ya es Set<Periods>
                .build();

        return dbaRepository.save(dba);
    }

    // 🔹 Actualizar existente
    public Dba update(int id, DbaRequest request) {
        Dba existing = dbaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("DBA no encontrado"));
        Areas area = areaRepository.findById(request.getAreaId())
                .orElseThrow(() -> new EntityNotFoundException("Área no encontrada"));

        existing.setDescription(request.getDescription());
        existing.setAreas(area);
        existing.setPeriods(request.getPeriods());

        return dbaRepository.save(existing);
    }

    // 🔹 Eliminar
    public void delete(int id) {
        Dba existing = dbaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("DBA no encontrado"));
        dbaRepository.delete(existing);
    }
}
