package TeachingPlanner.DailyPlanner.entity.planning;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Area")
public class Area {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idArea", nullable = false)
    private int idArea;

    @JsonIgnoreProperties("areas")
    @ManyToMany(mappedBy = "areas", fetch = FetchType.EAGER)
    private List<Dba> dbas;


}
