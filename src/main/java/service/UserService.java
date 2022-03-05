package service;

import model.User;
import repository.RelationalDBImpl.UserRepositoryImpl;
import repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Optional;

public class UserService {
    private final EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("vacationbooking.mysql");

    public static final String INVALID_PASSWORD_ERROR = "We're sorry, but the username and the " +
            "password don't match. Please try again!";
    public static final String INEXISTENT_USER_ERROR = "We're sorry, but it seems like no user " +
            "with this username exists. Please try again!";


    public OperationStatus register(String userName, String password) {
        // todo: validate
        boolean validData = true;
        if (validData) {
            User user = new User(userName, password, User.UserType.VacaySeeker);
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            UserRepository userRepository = new UserRepositoryImpl(entityManager);
            userRepository.save(user);
            entityManager.getTransaction().commit();
            entityManager.close();
            return OperationStatus.getSuccessfulOperationStatus();
        } else {
            // return message saying what the problem is
            return OperationStatus.getFailedOperationStatus(""); // todo: message
        }
    }

    public OperationStatus logIn(String userName, String password) {
        // find user in the database
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
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

    public OperationStatus logOut() {
        ActiveUserStatus.getInstance().logOut();
        return OperationStatus.getSuccessfulOperationStatus();
    }

}
