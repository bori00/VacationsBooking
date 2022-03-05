package service.filters;

import model.VacationPackage;
import service.package_status.VacationPackageStatus;

import java.time.LocalDate;
import java.util.List;

public class AvailabilityFilter implements VacationPackageFilter {

    @Override
    public boolean apply(VacationPackage vacationPackage) {
        return (new PeriodFilter(LocalDate.now(), null)).apply(vacationPackage) &&
                (new StatusFilter(List.of(VacationPackageStatus.NOT_BOOKED,
                        VacationPackageStatus.IN_PROGRESS))).apply(vacationPackage);
    }
}
