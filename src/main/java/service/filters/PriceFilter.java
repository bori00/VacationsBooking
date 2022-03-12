package service.filters;

import model.VacationPackage;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Objects;

public class PriceFilter implements VacationPackageFilter {
    private final Float minPrice;
    private final Float maxPrice;

    public PriceFilter(Float minPrice, Float maxPrice) {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    @Override
    public boolean apply(VacationPackage vacationPackage) {
        return (minPrice == null ||
                    vacationPackage.getPrice() > minPrice) &&
                (maxPrice == null || vacationPackage.getPrice() < maxPrice);
    }

    @Override
    public Predicate getPredicate(CriteriaBuilder cb, Root<VacationPackage> vacationPackageRoot) {
        return cb.between(vacationPackageRoot.get("price"),
                Objects.requireNonNullElse(minPrice, Float.MIN_VALUE),
                Objects.requireNonNullElse(maxPrice, Float.MAX_VALUE));
    }
}
