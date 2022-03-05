package repository;

import model.User;

public interface UserRepository extends IRepository<User> {
    User findByUserName(String userName);
}
