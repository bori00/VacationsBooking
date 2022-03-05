package service.view_models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import service.package_status.VacationPackageStatus;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class VacationPackageAdminViewModel {
    private final String name;
    private final String destinationName;
    private final float price;
    private final LocalDate startTime;
    private final LocalDate endTime;
    private final String extraDetails;
    private final VacationPackageStatus status;
    private final int totalNrPlaces;
    private final int noBookedPlaces;
}
