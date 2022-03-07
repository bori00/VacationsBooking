package service;

import model.Destination;
import model.VacationPackage;
import repository.DestinationRepository;
import repository.RelationalDBImpl.DestinationRepositoryImpl;
import repository.RelationalDBImpl.VacationPackageRepositoryImpl;
import repository.VacationPackageRepository;
import service.filters.VacationPackageFilter;
import service.view_models.VacationPackageAdminViewModel;
import service.view_models.VacationPackageUserViewModel;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class VacationPackageService extends AbstractService<VacationPackage> {
    // todo: extract name
    private final EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("vacationbooking.mysql");

    public static final String INVALID_DESTINATION = "This destination does not exist. Please " +
            "choose another destination for the vacation package!";

    public List<VacationPackageAdminViewModel> findAllForAdmin(Collection<VacationPackageFilter> filters) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        VacationPackageRepository vacationPackageRepository = new VacationPackageRepositoryImpl(entityManager);
        Collection<VacationPackage> packages =
                vacationPackageRepository.filterVacationPackages(filters);
        return packages
                .stream()
                .map(VacationPackageAdminViewModel::new)
                .collect(Collectors.toList());
    }

    public List<VacationPackageUserViewModel> findAllForVacaySeeker(Collection<VacationPackageFilter> filters) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        VacationPackageRepository vacationPackageRepository = new VacationPackageRepositoryImpl(entityManager);
        Collection<VacationPackage> packages =
                vacationPackageRepository.filterVacationPackages(filters);
        return packages
                .stream()
                .map(VacationPackageUserViewModel::new)
                .collect(Collectors.toList());
    }

    public OperationStatus add(String name, String destinationName, float price,
                               LocalDate startDate,
                               LocalDate endDate,
                               String extraDetails, int nrPlaces) {
        // todo: validate
        boolean validData = true;
        if (validData) {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            DestinationRepository destinationRepository =
                    new DestinationRepositoryImpl(entityManager);
            Optional<Destination> optDestination =
                    destinationRepository.findByName(destinationName);

            if (optDestination.isPresent()) {
                // valid destination
                entityManager.getTransaction().begin();
                VacationPackage vacationPackage = new VacationPackage(name, price, startDate,
                        endDate, extraDetails, nrPlaces, optDestination.get());
                VacationPackageRepository vacationPackageRepository =
                        new VacationPackageRepositoryImpl(entityManager);
                vacationPackageRepository.save(vacationPackage);
                entityManager.getTransaction().commit();
                support.firePropertyChange(Events.NEW_ENTITY.toString(), null,
                        new VacationPackageAdminViewModel(vacationPackage));
                return OperationStatus.getSuccessfulOperationStatus();
            } else {
                return OperationStatus.getFailedOperationStatus(INVALID_DESTINATION);
            }
        } else {
            return OperationStatus.getFailedOperationStatus(""); // todo: send message
        }
    }

    public OperationStatus delete(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        VacationPackageRepository vacationPackageRepository = new VacationPackageRepositoryImpl(entityManager);
        vacationPackageRepository.deleteById(id);
        entityManager.getTransaction().commit();
        entityManager.close();
        support.firePropertyChange(Events.REMOVED_ENTITY.toString(), null,
                null);
        return OperationStatus.getSuccessfulOperationStatus();
    }

    public OperationStatus edit(VacationPackageAdminViewModel vacationPackageAdminViewModel) {
        // todo: validate
        boolean validData = true;
        if (validData) {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            DestinationRepository destinationRepository =
                    new DestinationRepositoryImpl(entityManager);
            Optional<Destination> optDestination =
                    destinationRepository.findByName(vacationPackageAdminViewModel.getDestinationName());

            if (optDestination.isPresent()) {
                // valid destination
                entityManager.getTransaction().begin();
                VacationPackage vacationPackage =
                        new VacationPackage(vacationPackageAdminViewModel.getName(),
                                vacationPackageAdminViewModel.getPrice(),
                                vacationPackageAdminViewModel.getStartDate(),
                                vacationPackageAdminViewModel.getEndDate(),
                                vacationPackageAdminViewModel.getExtraDetails(),
                                vacationPackageAdminViewModel.getTotalNrPlaces(),
                                optDestination.get());
                VacationPackageRepository vacationPackageRepository =
                        new VacationPackageRepositoryImpl(entityManager);
                vacationPackageRepository.save(vacationPackage);
                entityManager.getTransaction().commit();
                support.firePropertyChange(Events.NEW_ENTITY.toString(), null,
                        new VacationPackageAdminViewModel(vacationPackage));
                return OperationStatus.getSuccessfulOperationStatus();
            } else {
                return OperationStatus.getFailedOperationStatus(INVALID_DESTINATION);
            }
        } else {
            return OperationStatus.getFailedOperationStatus(""); // todo: send message
        }
    }
}
