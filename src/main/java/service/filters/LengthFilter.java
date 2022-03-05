package service.filters;

import model.VacationPackage;

import static java.time.temporal.ChronoUnit.DAYS;

public class LengthFilter implements VacationPackageFilter {
    private final int minNoDays;
    private final int maxNoDays;

    public LengthFilter(int minNoDays, int maxNoDays) {
        this.minNoDays = minNoDays;
        this.maxNoDays = maxNoDays;
    }

    @Override
    public boolean apply(VacationPackage vacationPackage) {
        long duration = DAYS.between(vacationPackage.getStartDate(),
                vacationPackage.getEndDate()) + 1;
        return duration >= minNoDays && duration <= maxNoDays;
    }
}
