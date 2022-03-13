package repository.RelationalDBImpl;

import model.VacationPackage;
import org.hibernate.Session;
import repository.VacationPackageRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

public class VacationPackageRepositoryImpl extends RepositoryImpl<VacationPackage> implements VacationPackageRepository {
    public VacationPackageRepositoryImpl(EntityManager entityManager) {
        super(VacationPackage.class, entityManager);
    }

    @Override
    public List<VacationPackage> filterVacationPackages(Query query) {
        return (List<VacationPackage>) query.getResultList();
    }

    @Override
    public Optional<VacationPackage> findByName(String name) {
        Session session = (Session) entityManager.getDelegate();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<VacationPackage> cr = cb.createQuery(VacationPackage.class);
        Root<VacationPackage> root = cr.from(VacationPackage.class);

        cr.select(root).where(cb.equal(root.get("name"), name));
        Query query = session.createQuery(cr);

        return query.getResultList().stream().findFirst();
    }
}
