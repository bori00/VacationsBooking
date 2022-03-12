package service.filters;

import model.VacationPackage;
import service.package_status.VacationPackageStatus;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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

    @Override
    public Predicate getPredicate(CriteriaBuilder cb, Root<VacationPackage> vacationPackageRoot) {
        return cb.or(allowedStatus.stream().map(status -> status.getPredicate(cb,
                vacationPackageRoot)).toArray(Predicate[]::new));
    }
}
