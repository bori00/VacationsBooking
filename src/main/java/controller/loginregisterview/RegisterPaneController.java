package controller.loginregisterview;

import controller.loginregisterview.util.AlertFactory;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.OperationStatus;
import service.UserService;

public class RegisterPaneController {
    @FXML
    public TextField userNameTextField;

    @FXML
    public PasswordField passwordField;

    private final UserService userService = new UserService();

    public void onRegisterButtonClicked() {
        String userName = userNameTextField.getText();
        String password = passwordField.getText();

        OperationStatus operationStatus = userService.register(userName, password);
        AlertFactory.showAlert(operationStatus);
        if (operationStatus.isSuccessful()) {
            ((Stage) this.userNameTextField.getScene().getWindow()).close();
        }
    }
}
