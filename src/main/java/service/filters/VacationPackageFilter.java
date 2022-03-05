package service.filters;

import model.VacationPackage;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public interface VacationPackageFilter {
    boolean apply(VacationPackage vacationPackage);

    Predicate getPredicate(CriteriaBuilder cb, Root<VacationPackage> vacationPackageRoot);
}
