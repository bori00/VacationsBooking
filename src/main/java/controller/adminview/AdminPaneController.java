package controller.adminview;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import service.UserService;

import java.io.IOException;

public class AdminPaneController {

    private final UserService userService = new UserService();

    @FXML
    private Tab destinationsTab;
    @FXML
    private Button logoutButton;
    @FXML
    private Tab bookingsTab;
    @FXML
    private Tab vacationPackagesTab;

    @FXML
    void initialize() throws IOException {
        setupDestinationManagementTab();
    }

    @FXML
    private void onLogoutButtonClicked() {
        userService.logOut();
        loadLoginStage();
        ((Stage) this.logoutButton.getScene().getWindow()).close();
    }

    private void loadLoginStage() {
        FXMLLoader loginPaneLoader = new FXMLLoader(getClass().getResource(
                "/login_pane.fxml"));
        AnchorPane loginPane = null;
        try {
            loginPane = loginPaneLoader.load();
            Stage loginStage = new Stage();
            loginStage.setTitle("Login");
            loginStage.setScene(new Scene(loginPane));
            loginStage.show();
        } catch (IOException e) {
            e.printStackTrace(); //todo
        }
    }

    private void setupDestinationManagementTab() throws IOException {
        FXMLLoader productsPaneLoader = new FXMLLoader(getClass().getResource(
                "/admin_view/manage_destinations_pane.fxml"));
        AnchorPane destinationsPane = productsPaneLoader.load();
        destinationsTab.setContent(destinationsPane);
    }
}
