package model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "destination")
@NoArgsConstructor
@Getter
public class Destination {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(unique = true, nullable = false)
    private String name;

    public Destination(String name) {
        this.name = name;
    }
}
