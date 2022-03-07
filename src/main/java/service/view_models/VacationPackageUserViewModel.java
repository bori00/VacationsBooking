package service.view_models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import model.VacationPackage;
import service.package_status.VacationPackageStatus;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class VacationPackageUserViewModel {
    private final String name;
    private final String destinationName;
    private final float price;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String extraDetails;
    private final int totalNrPlaces;
    private final int noRemainingPlaces;

    public VacationPackageUserViewModel(VacationPackage vacationPackage) {
        this(vacationPackage.getName(),
                vacationPackage.getDestination().getName(),
                vacationPackage.getPrice(),
                vacationPackage.getStartDate(),
                vacationPackage.getEndDate(),
                vacationPackage.getExtraDetails(),
                vacationPackage.getNrPlaces(),
                vacationPackage.getNrPlaces() - vacationPackage.getBookings().size());
    }
}
