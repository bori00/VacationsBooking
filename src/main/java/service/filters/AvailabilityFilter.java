package service.filters;

import model.VacationPackage;
import service.package_status.VacationPackageStatus;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.List;

public class AvailabilityFilter implements VacationPackageFilter {

    @Override
    public boolean apply(VacationPackage vacationPackage) {
        return (new PeriodFilter(LocalDate.now(), null)).apply(vacationPackage) &&
                (new StatusFilter(List.of(VacationPackageStatus.NOT_BOOKED,
                        VacationPackageStatus.IN_PROGRESS))).apply(vacationPackage);
    }

    @Override
    public Predicate getPredicate(CriteriaBuilder cb, Root<VacationPackage> vacationPackageRoot) {
        return cb.and(new PeriodFilter(LocalDate.now(), null)
                        .getPredicate(cb, vacationPackageRoot),
                new StatusFilter(List.of(
                        VacationPackageStatus.NOT_BOOKED,
                        VacationPackageStatus.IN_PROGRESS))
                        .getPredicate(cb, vacationPackageRoot));
    }
}
