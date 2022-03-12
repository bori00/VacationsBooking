package service;

import model.User;

import java.util.Optional;

public interface IUserService {
    IOperationStatus register(String userName, String password);

    IOperationStatus logIn(String userName, String password);

    IOperationStatus logOut();

    Optional<User.UserType> getLoggedInUserType();
}
