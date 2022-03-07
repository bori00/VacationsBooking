package service.filters;

import model.VacationPackage;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class PriceFilter implements VacationPackageFilter {
    private final float minPrice;
    private final float maxPrice;

    public PriceFilter(float minPrice, float maxPrice) {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    @Override
    public boolean apply(VacationPackage vacationPackage) {
        return vacationPackage.getPrice() > minPrice && vacationPackage.getPrice() < maxPrice;
    }

    @Override
    public Predicate getPredicate(CriteriaBuilder cb, Root<VacationPackage> vacationPackageRoot) {
        return cb.between(vacationPackageRoot.get("price"), minPrice, maxPrice);
    }
}
