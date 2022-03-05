import model.User;
import repository.RelationalDBImpl.UserRepositoryImpl;
import repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(
                "vacationbooking.mysql");

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        User user = new User("hanna4", "hpass", User.UserType.VacaySeeker);

        UserRepository userRepository = new UserRepositoryImpl(entityManager);

        entityManager.getTransaction().begin();

        userRepository.save(user);

        entityManager.getTransaction().commit();


        entityManager.getTransaction().begin();

        Optional<User> users1 = userRepository.findByUserName("hanna5");
        Optional<User> users2 = userRepository.findByUserName("hanna3");

        System.out.println(users1);
        System.out.println(users2);

        entityManager.getTransaction().commit();

        entityManager.close();

    }
}
