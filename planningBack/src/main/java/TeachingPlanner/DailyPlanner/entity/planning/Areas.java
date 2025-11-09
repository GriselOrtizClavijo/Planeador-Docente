package TeachingPlanner.DailyPlanner.entity.planning;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "area")
public class Areas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idArea", nullable = false)
    private int idArea;

    @Column(nullable = false, unique = true)
    private String name;

    // 🔹 Relación con DBA (un área puede tener muchos DBAs)
    @OneToMany(mappedBy = "areas", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("areas")
    private List<Dba> dbas;
}
