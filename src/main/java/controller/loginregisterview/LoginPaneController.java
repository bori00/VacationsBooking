package controller.loginregisterview;

import controller.util.AlertFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.User;
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
            loadMainStage(userService.getLoggedInUserType().get());
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

    private void loadMainStage(User.UserType userType) {
        FXMLLoader mainPaneLoader = new FXMLLoader(getClass().getResource(
                getMainPanePath(userType)));
        AnchorPane mainPane = null;
        try {
            mainPane = mainPaneLoader.load();
            Stage mainStage = new Stage();
            mainStage.setTitle("Home");
            mainStage.setScene(new Scene(mainPane));
            mainStage.show();
        } catch (IOException e) {
            e.printStackTrace(); //todo
        }
    }

    private String getMainPanePath(User.UserType userType) {
        switch (userType) {
            case VacaySeeker:
                return "/ClientView/client_main_pane.fxml";
            case TravellingAgency:
                return "/admin_view/admin_main_pane.fxml";
        }
        return "/admin_view/admin_main_pane.fxml";
    }
}
