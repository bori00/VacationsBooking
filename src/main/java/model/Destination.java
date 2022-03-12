package model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "destination")
@NoArgsConstructor
@Getter
public class Destination implements IEntity {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(unique = true, nullable = false, length = 50)
    @NotBlank(message = "The destination's name cannot be blank!")
    @Size(min = 3, max = 50, message = "The destination's name should have a length between 3 and 50")
    private String name;

    @OneToMany(mappedBy = "destination", cascade = CascadeType.ALL)
    // lazy loading by default
    private List<@Valid VacationPackage> vacationPackages;

    public Destination(String name) {
        this.name = name;
        this.vacationPackages = new ArrayList<>();
    }
}
