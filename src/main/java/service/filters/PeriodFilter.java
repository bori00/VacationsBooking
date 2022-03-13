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
        return (earliestStartDate == null || !vacationPackage.getStartDate().isBefore(earliestStartDate)) &&
                (latestEndDate == null || !vacationPackage.getEndDate().isAfter(latestEndDate));
    }

    @Override
    public Predicate getPredicate(CriteriaBuilder cb, Root<VacationPackage> vacationPackageRoot) {
        Predicate startingFrom;
        if (earliestStartDate != null) {
            startingFrom = cb.greaterThanOrEqualTo(vacationPackageRoot.get("startDate"),
                    earliestStartDate);
        } else {
            startingFrom = cb.conjunction();
        }
        Predicate endingAt;
        if (latestEndDate != null) {
            endingAt = cb.lessThanOrEqualTo(vacationPackageRoot.get("endDate"),
                    latestEndDate);
        } else {
            endingAt = cb.conjunction();
        }
        return cb.and(startingFrom, endingAt);
    }
}
