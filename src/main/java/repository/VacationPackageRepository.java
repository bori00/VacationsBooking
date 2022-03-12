package repository;

import model.Destination;
import model.VacationPackage;
import service.filters.VacationPackageFilter;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface VacationPackageRepository extends IRepository<VacationPackage> {
    List<VacationPackage> filterVacationPackages(Collection<VacationPackageFilter> filters);

    Optional<VacationPackage> findByName(String name);
}
