package service.service_impl;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import model.Destination;
import model.VacationPackage;
import repository.DestinationRepository;
import repository.RelationalDBImpl.DestinationRepositoryImpl;
import repository.RelationalDBImpl.VacationPackageRepositoryImpl;
import repository.VacationPackageRepository;
import service.IOperationStatus;
import service.IVacationPackageService;
import service.filters.VacationPackageFilter;
import service.view_models.VacationPackageAdminViewModel;
import service.view_models.VacationPackageUserViewModel;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class VacationPackageService extends AbstractService<VacationPackage> implements IVacationPackageService {
    private static final VacationPackageService instance = new VacationPackageService();

    public static final String INVALID_DESTINATION = "This destination does not exist. Please " +
            "choose another destination for the vacation package!";
    public static final String VACATION_NAME_TAKEN = "This vacation package name is already taken" +
            ". Pleace choose another one";

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private VacationPackageService() {}

    public static VacationPackageService getInstance() {
        return instance;
    }

    public List<VacationPackageAdminViewModel> findAllForAdmin(Collection<VacationPackageFilter> filters) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Query query = new DatabaseFilterCriteriaBuilderService().buildQuery(filters, entityManager);
        VacationPackageRepository vacationPackageRepository = new VacationPackageRepositoryImpl(entityManager);
        Collection<VacationPackage> packages =
                vacationPackageRepository.filterVacationPackages(query);
        return packages
                .stream()
                .map(VacationPackageAdminViewModel::new)
                .collect(Collectors.toList());
    }

    public List<VacationPackageUserViewModel> findAllForVacaySeeker(Collection<VacationPackageFilter> filters) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Query query = new DatabaseFilterCriteriaBuilderService().buildQuery(filters, entityManager);
        VacationPackageRepository vacationPackageRepository = new VacationPackageRepositoryImpl(entityManager);
        Collection<VacationPackage> packages =
                vacationPackageRepository.filterVacationPackages(query);
        return packages
                .stream()
                .map(VacationPackageUserViewModel::new)
                .collect(Collectors.toList());
    }

    public IOperationStatus add(String name, String destinationName, float price,
                                LocalDate startDate,
                                LocalDate endDate,
                                String extraDetails, int nrPlaces) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        // validate vacation package data
        // find destination
        DestinationRepository destinationRepository =
                new DestinationRepositoryImpl(entityManager);
        Optional<Destination> optDestination =
                destinationRepository.findByName(destinationName);

        // validate all fields
        VacationPackage vacationPackage = new VacationPackage(name, price,
                startDate, endDate, extraDetails, nrPlaces, optDestination.orElse(null));
        Set<ConstraintViolation<VacationPackage>> constraintViolations =
                validator.validate(vacationPackage);
        if (!constraintViolations.isEmpty()) {
            return OperationStatus.getFailedOperationStatus(
                    constraintViolations
                            .stream()
                            .map(ConstraintViolation::getMessage)
                            .collect(Collectors.joining("\n")));
        }

        // check that there's no other vacation package with the same name
        VacationPackageRepository vacationPackageRepository =
                new VacationPackageRepositoryImpl(entityManager);
        Optional<VacationPackage> sameNameVacationPackage =
                vacationPackageRepository.findByName(name);
        if (sameNameVacationPackage.isPresent()) {
            return OperationStatus.getFailedOperationStatus(VACATION_NAME_TAKEN);
        }

        // valid vacation package --> save it
        entityManager.getTransaction().begin();
        vacationPackageRepository.save(vacationPackage);
        entityManager.getTransaction().commit();
        support.firePropertyChange(Events.NEW_ENTITY.toString(), null,
                new VacationPackageAdminViewModel(vacationPackage));
        return OperationStatus.getSuccessfulOperationStatus();
    }

    public IOperationStatus delete(Long id) {
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

    public OperationStatus edit(Long id, String name, String destinationName, float price,
                                 LocalDate startDate,
                                 LocalDate endDate,
                                 String extraDetails, int nrPlaces) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        // validate vacation package data
        // find destination
        DestinationRepository destinationRepository =
                new DestinationRepositoryImpl(entityManager);
        Optional<Destination> optDestination =
                destinationRepository.findByName(destinationName);

        // validate all fields
        VacationPackage vacationPackage = new VacationPackage(
                id, name, price,
                startDate, endDate, extraDetails, nrPlaces, optDestination.orElse(null));
        Set<ConstraintViolation<VacationPackage>> constraintViolations =
                validator.validate(vacationPackage);
        if (!constraintViolations.isEmpty()) {
            return OperationStatus.getFailedOperationStatus(
                    constraintViolations
                            .stream()
                            .map(ConstraintViolation::getMessage)
                            .collect(Collectors.joining("\n")));
        }

        // check that there's no other vacation package with the same name
        VacationPackageRepository vacationPackageRepository =
                new VacationPackageRepositoryImpl(entityManager);
        Optional<VacationPackage> sameNameVacationPackage =
                vacationPackageRepository.findByName(name);
        if (sameNameVacationPackage.isPresent() && !sameNameVacationPackage.get().getId().equals(vacationPackage.getId())) {
            return OperationStatus.getFailedOperationStatus(VACATION_NAME_TAKEN);
        }

        // valid vacation package data --> save changes
        entityManager.getTransaction().begin();
        vacationPackageRepository.save(vacationPackage);
        entityManager.getTransaction().commit();
        support.firePropertyChange(Events.EDITED_ENTITY.toString(), null,
                new VacationPackageAdminViewModel(vacationPackage));
        return OperationStatus.getSuccessfulOperationStatus();
    }

    public void fireNewBookingEvent() {
        support.firePropertyChange(String.valueOf(Events.EDITED_ENTITY), null, null);
    }

    public void fireDeletionEvent() {
        support.firePropertyChange(String.valueOf(Events.REMOVED_ENTITY), null, null);
    }
}
