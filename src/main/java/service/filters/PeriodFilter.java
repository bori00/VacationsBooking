package service.filters;

import model.VacationPackage;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;

public class PeriodFilter implements VacationPackageFilter {
    private final LocalDate earliestStartDate;
    private final LocalDate latestEndDate;

    public PeriodFilter(LocalDate earliestStartDate, LocalDate latestEndDate) {
        this.earliestStartDate = earliestStartDate;
        this.latestEndDate = latestEndDate;
    }

    @Override
    public boolean apply(VacationPackage vacationPackage) {
        return (earliestStartDate == null || !vacationPackage.getStartDate().isBefore(earliestStartDate))&&
               (latestEndDate == null || !vacationPackage.getEndDate().isAfter(latestEndDate));
    }

    @Override
    public Predicate getPredicate(CriteriaBuilder cb, Root<VacationPackage> vacationPackageRoot) {
        Predicate startingFrom =  cb.greaterThanOrEqualTo(vacationPackageRoot.get("startDate"),
                earliestStartDate);
        Predicate endingAt = cb.lessThanOrEqualTo(vacationPackageRoot.get("endDate"),
                latestEndDate);
        return cb.and(startingFrom, endingAt);
    }
}
