package service.filters;

import model.VacationPackage;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

    @Override
    public Predicate getPredicate(CriteriaBuilder cb, Root<VacationPackage> vacationPackageRoot) {
        List<Predicate> predicates = new ArrayList<>();

        for (String destinationName : destinationNames) {
            predicates.add(cb.equal(vacationPackageRoot.get("destination").get("name"), destinationName));
        }

        return cb.or(predicates.toArray(new Predicate[0]));
    }
}
