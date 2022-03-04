package model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "booking")
@NoArgsConstructor
@Getter
public class Booking {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "Id")
    // eager loading by default
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "vacation_id", referencedColumnName = "Id")
    // eager loading by default
    private VacationPackage vacationPackage;

    public Booking(User user, VacationPackage vacationPackage) {
        this.user = user;
        this.vacationPackage = vacationPackage;
    }
}