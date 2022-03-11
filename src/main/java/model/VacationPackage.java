package model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

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

    @Column(unique = true, nullable = false, length = 50)
    @NotBlank(message="The vacation package's name cannot be blank!")
    @Size(min = 3, max = 50, message="The vacation package's name should have a length between 3 " +
            "and 50")
    private String name;

    @Column(nullable = false)
    @PositiveOrZero
    private float price;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(length=500)
    @Size(max=500, message = "The vacation pacgae's descriotion should not cntain more than 500 " +
            "characters!")
    private String extraDetails;

    @Column(nullable = false)
    @PositiveOrZero
    private Integer nrPlaces;

    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "destination_id", referencedColumnName = "Id")
    // eager loading by default
    private Destination destination;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    // lazy loading by default
    private Set<@UniqueElements @Valid Booking> bookings;

    public VacationPackage(String name, float price, LocalDate startDate, LocalDate endDate,
                           String extraDetails, Integer nrPlaces, Destination destination) {
        this.name = name;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        this.extraDetails = extraDetails;
        this.nrPlaces = nrPlaces;
    }
}
