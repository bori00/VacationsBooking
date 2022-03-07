package repository;

import model.VacationPackage;
import service.filters.VacationPackageFilter;

import java.util.Collection;
import java.util.List;

public interface VacationPackageRepository extends IRepository<VacationPackage> {
    List<VacationPackage> filterVacationPackages(Collection<VacationPackageFilter> filters);
}
