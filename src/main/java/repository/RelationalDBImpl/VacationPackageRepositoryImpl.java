package repository.RelationalDBImpl;

import model.User;
import model.VacationPackage;
import org.hibernate.Session;
import repository.VacationPackageRepository;
import service.filters.VacationPackageFilter;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class VacationPackageRepositoryImpl extends RepositoryImpl<VacationPackage> implements VacationPackageRepository {
    public VacationPackageRepositoryImpl(EntityManager entityManager) {
        super(VacationPackage.class, entityManager);
    }

    @Override
    public List<VacationPackage> filterVacationPackages(Collection<VacationPackageFilter> filters) {
        Session session = (Session) entityManager.getDelegate();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<VacationPackage> cr = cb.createQuery(VacationPackage.class);
        Root<VacationPackage> root = cr.from(VacationPackage.class);

        Predicate[] predicates =
                filters.stream()
                        .map(filter -> filter.getPredicate(cb, root))
                        .toArray(Predicate[]::new);

        cr.select(root).where(predicates);
        Query query = session.createQuery(cr);

        return (List<VacationPackage>) query.getResultList();
    }
}
