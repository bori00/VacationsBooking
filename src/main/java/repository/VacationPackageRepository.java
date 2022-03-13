package repository;

import model.VacationPackage;

import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

public interface VacationPackageRepository extends IRepository<VacationPackage> {
    List<VacationPackage> filterVacationPackages(Query query);

    Optional<VacationPackage> findByName(String name);
}
