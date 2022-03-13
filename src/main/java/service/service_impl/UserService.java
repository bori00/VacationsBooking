package service.service_impl;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import model.User;
import repository.RelationalDBImpl.UserRepositoryImpl;
import repository.UserRepository;
import service.IOperationStatus;
import service.IUserService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class UserService implements IUserService {
    private final EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("vacationbooking.mysql");

    public static final String INVALID_PASSWORD_ERROR = "We're sorry, but the username and the " +
            "password don't match. Please try again!";
    public static final String INEXISTENT_USER_ERROR = "We're sorry, but it seems like no user " +
            "with this username exists. Please try again!";
    public static final String USERNAME_TAKEN_ERROR = "We're sorry, but this username is already " +
            "taken. Please choose another one.";

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();


    public IOperationStatus register(String userName, String password) {
        // validate he new user's data
        User user = new User(userName, password, User.UserType.VacaySeeker);
        Set<ConstraintViolation<User>> constraintViolations =
                validator.validate(user);
        if (!constraintViolations.isEmpty()) {
            return OperationStatus.getFailedOperationStatus(
                    constraintViolations
                            .stream()
                            .map(ConstraintViolation::getMessage)
                            .collect(Collectors.joining("\n")));
        }

        // check that the username is now taken
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        UserRepository userRepository = new UserRepositoryImpl(entityManager);
        Optional<User> sameUsernameUser = userRepository.findByUserName(userName);
        if (sameUsernameUser.isPresent()) {
            return OperationStatus.getFailedOperationStatus(USERNAME_TAKEN_ERROR);
        }

        // valid data: perform registration
        entityManager.getTransaction().begin();
        userRepository.save(user);
        entityManager.getTransaction().commit();
        entityManager.close();
        return OperationStatus.getSuccessfulOperationStatus();
    }

    public IOperationStatus logIn(String userName, String password) {
        // find user in the database
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        UserRepository userRepository = new UserRepositoryImpl(entityManager);
        Optional<User> possibleUser = userRepository.findByUserName(userName);
        entityManager.close();

        // user not found
        if (possibleUser.isEmpty()) {
            return OperationStatus.getFailedOperationStatus(INEXISTENT_USER_ERROR);
        }

        User user = possibleUser.get();
        // passwords don't match
        if (!user.getPassword().equals(password)) {
            return OperationStatus.getFailedOperationStatus(INVALID_PASSWORD_ERROR);
        }

        ActiveUserStatus.getInstance().logIn(user);
        return OperationStatus.getSuccessfulOperationStatus();
    }

    public IOperationStatus logOut() {
        ActiveUserStatus.getInstance().logOut();
        return OperationStatus.getSuccessfulOperationStatus();
    }

    public Optional<User.UserType> getLoggedInUserType() {
        User loggedInUser = ActiveUserStatus.getInstance().getLoggedInUser();
        if (loggedInUser == null) {
            return Optional.empty();
        } else {
            return Optional.of(loggedInUser.getUserType());
        }
    }

}
