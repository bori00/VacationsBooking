package service.filters;

import model.VacationPackage;

public interface VacationPackageFilter {
    boolean apply(VacationPackage vacationPackage);
}
