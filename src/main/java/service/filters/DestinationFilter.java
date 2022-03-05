package service.filters;

import model.VacationPackage;

import java.util.Collection;

public class DestinationFilter implements VacationPackageFilter {
    private final Collection<String> destinationNames;

    public DestinationFilter(Collection<String> destinationNames) {
        this.destinationNames = destinationNames;
    }


    @Override
    public boolean apply(VacationPackage vacationPackage) {
        return destinationNames
                .stream()
                .anyMatch(allowedName ->
                        vacationPackage.getDestination().getName().equals(allowedName));
    }
}
