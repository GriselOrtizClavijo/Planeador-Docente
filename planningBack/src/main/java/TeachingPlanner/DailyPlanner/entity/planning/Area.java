package TeachingPlanner.DailyPlanner.entity.planning;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "Area")
public class Area {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idArea", nullable = false)
    private int idArea;

    @JsonIgnoreProperties("areas")
    @ManyToMany(mappedBy = "areas", fetch = FetchType.EAGER)
    private List<Dba> dbas;

    @Column(nullable = false, unique = true)
    private String name;

  /* public Area() {
    }

    public Area(int idArea, List<Dba> dbas, String name) {
        this.idArea = idArea;
        this.dbas = dbas;
        this.name = name;
    }

    public int getIdArea() {
        return idArea;
    }

    public void setIdArea(int idArea) {
        this.idArea = idArea;
    }

    public List<Dba> getDbas() {
        return dbas;
    }

    public void setDbas(List<Dba> dbas) {
        this.dbas = dbas;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }*/
}
