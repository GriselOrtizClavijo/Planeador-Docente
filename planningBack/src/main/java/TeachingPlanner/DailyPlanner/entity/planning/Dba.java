package TeachingPlanner.DailyPlanner.entity.planning;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Dba")
public class Dba {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idDba", nullable = false)
    private int idDba;

    @Column(nullable = false, unique = true)
    private String description;

    @ManyToMany
    @JoinTable(name = "dba_area", joinColumns = @JoinColumn(name = "idDba"), inverseJoinColumns = @JoinColumn(name = "idArea"))
    private Set<Areas> areas;



}
