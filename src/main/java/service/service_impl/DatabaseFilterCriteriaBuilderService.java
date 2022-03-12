package service.service_impl;

import model.VacationPackage;
import org.hibernate.Session;
import service.filters.VacationPackageFilter;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collection;

public class DatabaseFilterCriteriaBuilderService {
    public Query buildQuery(Collection<VacationPackageFilter> filters, EntityManager entityManager) {
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
        return query;
    }
}
