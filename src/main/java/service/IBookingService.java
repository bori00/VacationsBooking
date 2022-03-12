package service;

import service.view_models.VacationPackageUserViewModel;

import java.util.List;

public interface IBookingService extends PropertyChangeObservable {
    IOperationStatus add(Long vacationPackageId);

    List<VacationPackageUserViewModel> getLoggedInUsersBookedVacations();
}
