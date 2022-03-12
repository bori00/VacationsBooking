package service.view_models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import model.VacationPackage;
import service.package_status.VacationPackageStatus;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class VacationPackageAdminViewModel {
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

    @ViewField(name = "Details")
    private final String extraDetails;

    @ViewField(name = "Status")
    private final VacationPackageStatus status;

    @ViewField(name = "No. places")
    private final int totalNrPlaces;

    @ViewField(name = "No. bookings")
    private final int noBookedPlaces;

    public VacationPackageAdminViewModel(VacationPackage vacationPackage) {
        this(vacationPackage.getId(),
                vacationPackage.getName(),
                vacationPackage.getDestination().getName(),
                vacationPackage.getPrice(),
                vacationPackage.getStartDate(),
                vacationPackage.getEndDate(),
                vacationPackage.getExtraDetails(),
                VacationPackageStatus.getVacationPackageStatus(vacationPackage),
                vacationPackage.getNrPlaces(),
                vacationPackage.getBookings().size());
    }
}
