package service;

import service.view_models.DestinationViewModel;

import java.util.List;

public interface IDestinationService extends PropertyChangeObservable {
     List<DestinationViewModel> findAll();

     IOperationStatus add(String name);

     IOperationStatus delete(DestinationViewModel destinationViewModel);
}
