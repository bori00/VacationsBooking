package service.filters;

import model.VacationPackage;

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
}
