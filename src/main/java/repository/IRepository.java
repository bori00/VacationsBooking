package repository;

import java.util.List;
import java.util.Optional;

public interface IRepository<T>{
    Optional<T> findById(Long id);

    T save(T entity);

    void delete(T entity);

    void deleteById(Long id);

    List<T> findAll();
}
