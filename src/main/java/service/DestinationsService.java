package service;

import model.Destination;
import model.VacationPackage;
import repository.DestinationRepository;
import repository.RelationalDBImpl.DestinationRepositoryImpl;
import repository.RelationalDBImpl.UserRepositoryImpl;
import repository.RelationalDBImpl.VacationPackageRepositoryImpl;
import repository.UserRepository;
import repository.VacationPackageRepository;
import service.filters.StatusFilter;
import service.package_status.VacationPackageStatus;
import service.view_models.DestinationViewModel;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class DestinationsService extends AbstractService<Destination> {
    private final EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("vacationbooking.mysql");

    public static String DELETING_DESTINATION_WITH_BOOKED_PACKAGES = "You cannot delete a " +
            "destination for which booked packages exist!";

    public List<DestinationViewModel> findAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        DestinationRepository destinationRepository = new DestinationRepositoryImpl(entityManager);
        List<Destination> destinations = destinationRepository.findAll();
        entityManager.close();
        return destinations
                .stream()
                .map(DestinationViewModel::new)
                .collect(Collectors.toList());
    }

    public OperationStatus add(String name) {
        // todo: validate
        boolean validData = true;
        if (validData) {
            Destination destination = new Destination(name);
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            DestinationRepository destinationRepository =
                    new DestinationRepositoryImpl(entityManager);
            Destination savedDestination = destinationRepository.save(destination);
            entityManager.getTransaction().commit();
            entityManager.close();
            support.firePropertyChange(Events.NEW_ENTITY.toString(), null,
                    new DestinationViewModel(savedDestination));
            return OperationStatus.getSuccessfulOperationStatus();
        } else {
            return OperationStatus.getFailedOperationStatus(""); // todo: send message
        }
    }

    public OperationStatus delete(DestinationViewModel destinationViewModel) {
        // todo: ask teacher about the expected behavior here!!!
        // don't allow deletion of any booked packages exist
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        VacationPackageRepository vacationPackageRepository = new VacationPackageRepositoryImpl(entityManager);
        Collection<VacationPackage> bookedPacakgesForDestination =
                vacationPackageRepository.filterVacationPackages(List.of(new StatusFilter(
                        List.of(VacationPackageStatus.IN_PROGRESS, VacationPackageStatus.BOOKED))));

        if (bookedPacakgesForDestination.isEmpty()) {
            // allow deletion
            entityManager.getTransaction().begin();
            DestinationRepository destinationRepository =
                    new DestinationRepositoryImpl(entityManager);
            destinationRepository.deleteById(destinationViewModel.getId());
            entityManager.close();
            support.firePropertyChange(Events.REMOVED_ENTITY.toString(), null, null);
            return OperationStatus.getSuccessfulOperationStatus();
        } else {
            return OperationStatus.getFailedOperationStatus(DELETING_DESTINATION_WITH_BOOKED_PACKAGES);
        }
    }
}
