package service.filters;

import model.VacationPackage;
import service.package_status.VacationPackageStatus;

import java.util.Collection;

public class StatusFilter implements VacationPackageFilter {
    private final Collection<VacationPackageStatus> allowedStatus;

    public StatusFilter(Collection<VacationPackageStatus> allowedStatus) {
        this.allowedStatus = allowedStatus;
    }

    @Override
    public boolean apply(VacationPackage vacationPackage) {
        return allowedStatus
                .contains(VacationPackageStatus.getVacationPackageStatus(vacationPackage));
    }
}
