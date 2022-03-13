package repository.RelationalDBImpl;

import model.IEntity;
import repository.IRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

public abstract class RepositoryImpl<T extends IEntity> implements IRepository<T> {

    private final Class<T> type;

    protected final EntityManager entityManager;

    protected RepositoryImpl(Class<T> type, EntityManager entityManager) {
        this.type = type;
        this.entityManager = entityManager;
    }

    @Override
    public Optional<T> findById(Long id) {
        return Optional.ofNullable(entityManager.find(type, id));
    }

    @Override
    public T save(T entity) {
        if (entity.getId() == null) {
            entityManager.persist(entity);
        } else {
            entity = entityManager.merge(entity);
        }
        return entity;
    }

    @Override
    public void delete(T entity) {
        if (entityManager.contains(entity)) {
            entityManager.remove(entity);
        } else {
            entityManager.merge(entityManager.merge(entity));
        }
    }

    @Override
    public void deleteById(Long id) {
        delete(findById(id).get());
    }

    @Override
    public List<T> findAll() {
        String hql = "from " + type.getName();
        Query query = entityManager.createQuery(hql);
        return (List<T>) query.getResultList();
    }
}
