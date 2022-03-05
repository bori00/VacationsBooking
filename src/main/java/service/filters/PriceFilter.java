package service.filters;

import model.VacationPackage;

public class PriceFilter implements VacationPackageFilter {
    private final float minPrice;
    private final float maxPrice;

    public PriceFilter(float minPrice, float maxPrice) {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    @Override
    public boolean apply(VacationPackage vacationPackage) {
        return vacationPackage.getPrice() >= minPrice && vacationPackage.getPrice() >= maxPrice;
    }
}
