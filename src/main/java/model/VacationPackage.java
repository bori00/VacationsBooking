package model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "vacation_package")
@NoArgsConstructor
@Getter
public class VacationPackage implements IEntity{
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private float price;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column
    private String extraDetails;

    @Column(nullable = false)
    private Integer nrPlaces;

    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "destination_id", referencedColumnName = "Id")
    // eager loading by default
    private Destination destination;

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    // lazy loading by default
    private Set<Booking> bookings;

    public VacationPackage(String name, float price, LocalDate startDate, LocalDate endDate, String extraDetails, Integer nrPlaces) {
        this.name = name;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        this.extraDetails = extraDetails;
        this.nrPlaces = nrPlaces;
    }
}
