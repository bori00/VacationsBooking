package repository;

public interface IRepository<T>{
    T findById(Long id);

    T save(T entity);

    void delete(T entity);
}
