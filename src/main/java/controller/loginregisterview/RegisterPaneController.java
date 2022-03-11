package controller.loginregisterview;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegisterPaneController {
    /** Text field for entering the username. */
    @FXML
    public TextField userNameTextField;

    /** Text field for entering the password. */
    @FXML
    public PasswordField passwordField;

    /**
     * Sends a request to the delivery service for registration and handles the registration result.
     */
    public void onRegisterButtonClicked() {
        String userName = userNameTextField.getText();
        String password = passwordField.getText();

       /* ValidationResult<User> validationResult = userValidator.validate(userName, password);
        if (validationResult.isValid()) {
            if (!deliveryService.existsUserWithUsername(userName)) {
                deliveryService.registerClient(userName, password);
                ((Stage) this.userNameTextField.getScene().getWindow()).close();
            } else {
                AlertFactory.showDuplicateUsernameError(userName);
            }
        } else {
            AlertFactory.showInvalidDataError(validationResult);
        }*/
    }
}
