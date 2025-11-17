package TeachingPlanner.DailyPlanner.controllers.daily;

import TeachingPlanner.DailyPlanner.entity.daily.DailyPlan;
import TeachingPlanner.DailyPlanner.entity.daily.DailyPlanImage;
import TeachingPlanner.DailyPlanner.repository.daily.DailyPlanImageRepository;
import TeachingPlanner.DailyPlanner.repository.daily.DailyPlanRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/daily-plan/images")
public class DailyPlanImageController {

    private final DailyPlanRepository planRepo;
    private final DailyPlanImageRepository imageRepo;

    private final String uploadDir = "uploads/";

    @PostMapping("/{idPlan}")
    public ResponseEntity<?> uploadImage(
            @PathVariable int idPlan,
            @RequestParam("file") MultipartFile file) {

        try {
            // Verificar plan
            DailyPlan plan = planRepo.findById(idPlan)
                    .orElseThrow(() -> new RuntimeException("Plan no encontrado"));

            // Crear nombre único
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

            // Crear carpeta si no existe
            File folder = new File(uploadDir);
            if (!folder.exists()) folder.mkdirs();

            // Guardar archivo físico
            Files.copy(file.getInputStream(), Paths.get(uploadDir + fileName));

            // Guardar registro en BD
            String url = "/uploads/" + fileName;

            DailyPlanImage image = DailyPlanImage.builder()
                    .plan(plan)
                    .url(url)
                    .build();

            imageRepo.save(image);

            return ResponseEntity.ok(image);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error subiendo archivo: " + e.getMessage());
        }
    }

    @GetMapping("/{idPlan}")
    public ResponseEntity<List<DailyPlanImage>> getImages(@PathVariable int idPlan) {
        return ResponseEntity.ok(imageRepo.findByPlan_IdPlan(idPlan));
    }
}
