package controller.clientview;

import controller.util.TableController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import service.IBookingService;
import service.service_impl.BookingService;
import service.view_models.VacationPackageUserViewModel;

import java.util.List;

public class ClientBookingsPaneController {
    @FXML
    private TableView<VacationPackageUserViewModel> bookingsTable;

    private final IBookingService bookingService = BookingService.getInstance();

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
