package controller.clientview;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import service.UserService;

import java.io.IOException;

public class ClientPaneController {
    private final UserService userService = new UserService();

    @FXML
    private Button logoutButton;
    @FXML
    private Tab bookingsTab;
    @FXML
    private Tab vacationPackagesTab;

    @FXML
    void initialize() throws IOException {
        setupVacationManagementTab();
        setupBookingsTab();
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

    private void setupVacationManagementTab() throws IOException {
        FXMLLoader vacationsPaneLoader = new FXMLLoader(getClass().getResource(
                "/client_view/book_vacation_packages_pane.fxml"));
        AnchorPane vacationsPane = vacationsPaneLoader.load();
        vacationPackagesTab.setContent(vacationsPane);
    }

    private void setupBookingsTab() throws IOException {
        FXMLLoader bookingsPaneLoader = new FXMLLoader(getClass().getResource(
                "/client_view/view_bookings_pane.fxml"));
        AnchorPane bookingsPane = bookingsPaneLoader.load();
        bookingsTab.setContent(bookingsPane);
    }
}
