package service.service_impl;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import model.Destination;
import repository.DestinationRepository;
import repository.RelationalDBImpl.DestinationRepositoryImpl;
import service.IDestinationService;
import service.IOperationStatus;
import service.view_models.DestinationViewModel;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class DestinationService extends AbstractService<Destination> implements IDestinationService {

    private static final DestinationService instance = new DestinationService();

    public static String DELETING_DESTINATION_WITH_BOOKED_PACKAGES = "You cannot delete a " +
            "destination for which booked packages exist!";
    public static String DESTINATION_NAME_TAKEN = "Another destination with the same name already" +
            " exists. Please choose another name!";

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private DestinationService() {
    }

    public static DestinationService getInstance() {
        return instance;
    }

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

    public IOperationStatus add(String name) {
        // validate data
        Destination destination = new Destination(name);
        Set<ConstraintViolation<Destination>> constraintViolations =
                validator.validate(destination);
        if (!constraintViolations.isEmpty()) {
            return OperationStatus.getFailedOperationStatus(
                    constraintViolations
                            .stream()
                            .map(ConstraintViolation::getMessage)
                            .collect(Collectors.joining("\n")));
        }

        // check that there's no other destination with the same name
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        DestinationRepository destinationRepository = new DestinationRepositoryImpl(entityManager);
        Optional<Destination> sameNameDestination = destinationRepository.findByName(name);
        if (sameNameDestination.isPresent()) {
            return OperationStatus.getFailedOperationStatus(DESTINATION_NAME_TAKEN);
        }

        // valid data --> save new destination
        entityManager.getTransaction().begin();
        Destination savedDestination = destinationRepository.save(destination);
        entityManager.getTransaction().commit();
        entityManager.close();
        support.firePropertyChange(Events.NEW_ENTITY.toString(), null,
                new DestinationViewModel(savedDestination));
        System.out.println("Event fired");
        return OperationStatus.getSuccessfulOperationStatus();
    }

    public IOperationStatus delete(DestinationViewModel destinationViewModel) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        DestinationRepository destinationRepository =
                new DestinationRepositoryImpl(entityManager);
        destinationRepository.deleteById(destinationViewModel.getId());
        entityManager.getTransaction().commit();
        entityManager.close();
        support.firePropertyChange(Events.REMOVED_ENTITY.toString(), null, null);
        VacationPackageService.getInstance().fireDeletionEvent();
        return OperationStatus.getSuccessfulOperationStatus();
    }
}
