package controller.clientview;

import controller.adminview.EditVacationPackageController;
import controller.util.AlertFactory;
import controller.util.TableController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import service.BookingService;
import service.OperationStatus;
import service.VacationPackageService;
import service.view_models.VacationPackageAdminViewModel;
import service.view_models.VacationPackageUserViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientBookingsPaneController {
    @FXML
    private TableView<VacationPackageUserViewModel> bookingsTable;

    private final BookingService bookingService = BookingService.getInstance();

    @FXML
    public void initialize() {
        new TableController<>(
                VacationPackageUserViewModel.class,
                bookingService,
                bookingsTable) {
            @Override
            protected List<VacationPackageUserViewModel> getUpdatedEntries() {
                return bookingService.getLoggedInUsersBookedVacations();
            }
        };
        bookingsTable.setPlaceholder(new Label("You have no booked vacations"));
    }

    private void alignCenter(TableColumn tableColumn) {
        tableColumn.setStyle("-fx-alignment: CENTER;");
    }
}
