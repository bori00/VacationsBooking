package repository.RelationalDBImpl;

import model.IEntity;
import repository.IRepository;

import javax.persistence.Entity;
import javax.persistence.EntityManager;

public class RepositoryImpl<T extends IEntity> implements IRepository<T> {

    private final Class<T> type;

    protected final EntityManager entityManager;

    protected RepositoryImpl(Class<T> type, EntityManager entityManager) {
        this.type = type;
        this.entityManager = entityManager;
    }

    @Override
    public T findById(Long id) {
        return entityManager.find(type, id);
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
}
