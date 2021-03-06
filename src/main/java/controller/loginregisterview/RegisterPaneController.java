package controller.loginregisterview;

import controller.util.AlertFactory;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.IOperationStatus;
import service.IUserService;
import service.service_impl.UserService;

public class RegisterPaneController {
    @FXML
    public TextField userNameTextField;

    @FXML
    public PasswordField passwordField;

    private final IUserService userService = new UserService();

    public void onRegisterButtonClicked() {
        String userName = userNameTextField.getText();
        String password = passwordField.getText();

        IOperationStatus operationStatus = userService.register(userName, password);
        AlertFactory.showAlert(operationStatus);
        if (operationStatus.isSuccessful()) {
            ((Stage) this.userNameTextField.getScene().getWindow()).close();
        }
    }
}
