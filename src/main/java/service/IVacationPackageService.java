package service;

import service.filters.VacationPackageFilter;
import service.view_models.VacationPackageAdminViewModel;
import service.view_models.VacationPackageUserViewModel;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface IVacationPackageService extends PropertyChangeObservable {

    List<VacationPackageAdminViewModel> findAllForAdmin(Collection<VacationPackageFilter> filters);

    List<VacationPackageUserViewModel> findAllForVacaySeeker(Collection<VacationPackageFilter> filters);

    IOperationStatus add(String name, String destinationName, float price,
                         LocalDate startDate,
                         LocalDate endDate,
                         String extraDetails, int nrPlaces);

    IOperationStatus delete(Long id);

    IOperationStatus edit(Long id, String name, String destinationName, float price,
                          LocalDate startDate,
                          LocalDate endDate,
                          String extraDetails, int nrPlaces);
}
