package model;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "booking")
@NoArgsConstructor
@Getter
public class Booking implements IEntity {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id", referencedColumnName = "Id")
    // eager loading by default
    private User user;

    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "vacation_id", referencedColumnName = "Id")
    // eager loading by default
    private @Valid VacationPackage vacationPackage;

    public Booking(User user, VacationPackage vacationPackage) {
        this.user = user;
        this.vacationPackage = vacationPackage;
    }
}
