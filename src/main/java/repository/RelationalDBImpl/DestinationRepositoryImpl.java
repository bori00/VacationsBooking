package repository.RelationalDBImpl;

import model.Destination;
import org.hibernate.Session;
import repository.DestinationRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Optional;

public class DestinationRepositoryImpl extends RepositoryImpl<Destination> implements DestinationRepository {
    public DestinationRepositoryImpl(EntityManager entityManager) {
        super(Destination.class, entityManager);
    }

    public Optional<Destination> findByName(String name) {
        Session session = (Session) entityManager.getDelegate();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Destination> cr = cb.createQuery(Destination.class);
        Root<Destination> root = cr.from(Destination.class);

        cr.select(root).where(cb.equal(root.get("name"), name));
        Query query = session.createQuery(cr);

        return query.getResultList().stream().findFirst();
    }
}
