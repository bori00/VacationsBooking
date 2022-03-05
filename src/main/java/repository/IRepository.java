package repository;

import java.util.List;

public interface IRepository<T>{
    T findById(Long id);

    T save(T entity);

    void delete(T entity);

    List<T> findAll();
}
