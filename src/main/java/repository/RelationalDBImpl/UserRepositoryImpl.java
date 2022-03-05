package repository.RelationalDBImpl;

import model.Booking;
import model.User;
import org.hibernate.Session;
import repository.IRepository;
import repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

public class UserRepositoryImpl extends RepositoryImpl<User> implements UserRepository {
    public UserRepositoryImpl(EntityManager entityManager) {
        super(User.class, entityManager);
    }

    @Override
    public Optional<User> findByUserName(String userName) {
        Session session = (Session) entityManager.getDelegate();
        Query query = session.createQuery("Select u from User u where u.userName=:uName",
                User.class);
        query.setParameter("uName", userName);
        return query.getResultList().stream().findFirst();
    }
}
