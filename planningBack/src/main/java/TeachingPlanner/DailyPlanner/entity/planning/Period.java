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
@Table(name = "Period")
public class Period {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPeriod", nullable = false)
    private int idPeriod;

    @JsonIgnoreProperties("periods")
    @ManyToMany(mappedBy = "periods", fetch = FetchType.EAGER)
    private List<Dba> dbas;

}
