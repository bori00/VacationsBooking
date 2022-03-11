package controller.loginregisterview;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginPaneController {
    @FXML
    public TextField userNameTextField;

    @FXML
    public TextField passwordField;

    @FXML
    void onLoginButtonClicked() {
        String userName = userNameTextField.getText();
        String password = passwordField.getText();
//        if (deliveryService.logIn(userName, password)) {
//            loadMainStage(deliveryService.getLoggedInUserType().get());
//            ((Stage) this.userNameTextField.getScene().getWindow()).close();
//        } else {
//            AlertFactory.showLoginErrorAlert();
//        }
    }

    @FXML
    void onRegisterButtonClicked() {
        FXMLLoader registerPaneLoader = new FXMLLoader(getClass().getResource(
                "/register_pane.fxml"));
        try {
            AnchorPane registerPane = registerPaneLoader.load();
//            RegisterPaneController registerPaneController = registerPaneLoader.getController();
//            registerPaneController.initDeliveryService(deliveryService);
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
