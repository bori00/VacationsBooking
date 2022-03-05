package repository;

import model.User;

import java.util.List;

public interface UserRepository extends IRepository<User> {
    List<User> findByUserName(String userName);
}
