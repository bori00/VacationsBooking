package controller.loginregisterview;

import controller.loginregisterview.util.AlertFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import service.OperationStatus;
import service.UserService;

import java.io.IOException;

public class LoginPaneController {
    @FXML
    public TextField userNameTextField;

    @FXML
    public TextField passwordField;

    private final UserService userService = new UserService();

    @FXML
    void onLoginButtonClicked() {
        String userName = userNameTextField.getText();
        String password = passwordField.getText();
        OperationStatus status = userService.logIn(userName, password);
        if (status.isSuccessful()) {
            // loadMainStage(deliveryService.getLoggedInUserType().get()); todo
            ((Stage) this.userNameTextField.getScene().getWindow()).close();
        } else {
            AlertFactory.showAlert(status);
        }
    }

    @FXML
    void onRegisterButtonClicked() {
        FXMLLoader registerPaneLoader = new FXMLLoader(getClass().getResource(
                "/register_pane.fxml"));
        try {
            AnchorPane registerPane = registerPaneLoader.load();
            Stage registerStage = new Stage();
            registerStage.setTitle("Register");
            registerStage.initOwner(this.userNameTextField.getScene().getWindow());
            registerStage.initModality(Modality.WINDOW_MODAL);
            registerStage.setScene(new Scene(registerPane));
            registerStage.show();
        } catch (IOException e) {
            e.printStackTrace(); //todo
        }
    }
}
