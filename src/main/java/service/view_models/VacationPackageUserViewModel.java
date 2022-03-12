package service.view_models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import model.VacationPackage;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class VacationPackageUserViewModel {
    @ViewField(displayed = false)
    private final Long id;

    @ViewField(name = "Name")
    private final String name;

    @ViewField(name = "Destination")
    private final String destinationName;

    @ViewField(name = "Price")
    private final float price;

    @ViewField(name = "Start Date")
    private final LocalDate startDate;

    @ViewField(name = "End Date")
    private final LocalDate endDate;

    @ViewField(name = "Extra Details")
    private final String extraDetails;

    @ViewField(name = "Nr. Places")
    private final int totalNrPlaces;

    @ViewField(name = "Nr. Rem. Places")
    private final int noRemainingPlaces;

    public VacationPackageUserViewModel(VacationPackage vacationPackage) {
        this(vacationPackage.getId(),
                vacationPackage.getName(),
                vacationPackage.getDestination().getName(),
                vacationPackage.getPrice(),
                vacationPackage.getStartDate(),
                vacationPackage.getEndDate(),
                vacationPackage.getExtraDetails(),
                vacationPackage.getNrPlaces(),
                vacationPackage.getNrPlaces() - vacationPackage.getBookings().size());
    }
}
