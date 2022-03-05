package repository;

import model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends IRepository<User> {
    Optional<User> findByUserName(String userName);
}
