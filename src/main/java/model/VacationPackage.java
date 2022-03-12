package model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;
import org.hibernate.validator.constraints.time.DurationMin;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.time.temporal.ChronoUnit.DAYS;

@Entity
@Table(name = "vacation_package")
@NoArgsConstructor
@Getter
public class VacationPackage implements IEntity{
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(unique = true, nullable = false, length = 50)
    @NotBlank(message="The vacation package's name cannot be blank.")
    @Size(min = 3, max = 50, message="The vacation package's name should have a length between 3 " +
            "and 50.")
    private String name;

    @Column(nullable = false)
    @PositiveOrZero(message = "The vacation package's price bust be >= 0.")
    private float price;

    @Column(nullable = false)
    @NotNull(message = "The vacation package should have a start date.")
    @FutureOrPresent(message = "The vacation package's start date cannot be in the past.")
    private LocalDate startDate;

    @Column(nullable = false)
    @NotNull(message = "The vacation package should have an end date.")
    @FutureOrPresent(message = "The vacation package's start date cannot be in the past.")
    private LocalDate endDate;

    @Column(length=500)
    @Size(max=500, message = "The vacation pacgkage's descriotion should not contain more than " +
            "500" +
            " " +
            "characters!")
    private String extraDetails;

    @Column(nullable = false)
    @NotNull(message = "Missing data on the number of places for the vacation package.")
    @PositiveOrZero(message = "The number of places for each vacation package must be >= 0.")
    private Integer nrPlaces;

    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "destination_id", referencedColumnName = "Id")
    @NotNull(message = "The vacation package should have a destination.")
    // eager loading by default
    private Destination destination;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    // lazy loading by default
    private Set<@UniqueElements @Valid Booking> bookings;

    @Transient
    @NotNull(message = "")
    @DurationMin(days=0, inclusive = true, message = "The end date must be later than or equal to" +
            " the start date.")
    private Duration duration;

    public VacationPackage(String name, float price, LocalDate startDate, LocalDate endDate,
                           String extraDetails, Integer nrPlaces, Destination destination) {
        this.name = name;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        this.extraDetails = extraDetails;
        this.nrPlaces = nrPlaces;
        this.destination = destination;
        this.bookings = new HashSet<>();
        computeDuration();
    }

    public VacationPackage(Long id, String name, float price, LocalDate startDate,
                           LocalDate endDate,
                           String extraDetails, Integer nrPlaces, Destination destination) {
        this(name, price, startDate, endDate, extraDetails, nrPlaces, destination);
        this.Id = id;
    }

    @PrePersist
    @PostLoad
    public void computeDuration() {
        if (startDate != null && endDate !=null) {
            duration = Duration.ofDays(DAYS.between(startDate, endDate));
        } else {
            duration = null;
        }
    }
}
