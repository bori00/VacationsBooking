package model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "destination")
@NoArgsConstructor
@Getter
public class Destination implements IEntity {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "destination", cascade = CascadeType.ALL)
    // lazy loading by default
    private List<VacationPackage> vacationPackages;

    public Destination(String name) {
        this.name = name;
    }
}
