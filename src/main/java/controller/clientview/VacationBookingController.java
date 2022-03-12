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
import service.filters.AvailabilityFilter;
import service.filters.VacationPackageFilter;
import service.view_models.VacationPackageUserViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VacationBookingController {
    @FXML
    private TableView<VacationPackageUserViewModel> vacationsTable;

    private static final String BOOK_TEXT = "Book";

    private final VacationPackageService vacationPackageService =
            VacationPackageService.getInstance();

    private final BookingService bookingService = BookingService.getInstance();

    @FXML
    public void initialize() {
        new TableController<>(
                VacationPackageUserViewModel.class,
                vacationPackageService,
                vacationsTable) {
            @Override
            protected List<VacationPackageUserViewModel> getUpdatedEntries() {
                return vacationPackageService.findAllForVacaySeeker(List.of(new AvailabilityFilter()));
                // todo: add the other filters
            }
        };

        vacationsTable.setPlaceholder(new Label("No Available Vacations Found"));
        setupBookVacationButtonColumn();
    }

    private void setupBookVacationButtonColumn() {
        TableColumn<VacationPackageUserViewModel, Void> actionCol = new TableColumn<>(BOOK_TEXT);
        alignCenter(actionCol);

        Callback<TableColumn<VacationPackageUserViewModel, Void>, TableCell<VacationPackageUserViewModel, Void>> cellFactory
                = new Callback<>() {
            @Override
            public TableCell<VacationPackageUserViewModel, Void> call(final TableColumn<VacationPackageUserViewModel, Void> param) {
                final TableCell<VacationPackageUserViewModel, Void> cell = new TableCell<>() {

                    private final Button btn = new Button(BOOK_TEXT);

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            VacationPackageUserViewModel vacationPackageToBook =
                                    getTableView().getItems().get(getIndex());
                            onBook(vacationPackageToBook);
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };
        actionCol.setCellFactory(cellFactory);
        vacationsTable.getColumns().add(actionCol);
    }

    private void onBook(VacationPackageUserViewModel vacationPackageUserViewModel) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Booking");
        alert.setHeaderText("Are you sure that you want to book a place for this vacation?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            OperationStatus operationStatus =
                    bookingService.add(vacationPackageUserViewModel.getId());
            AlertFactory.showAlert(operationStatus);
        }
    }

    private void alignCenter(TableColumn tableColumn) {
        tableColumn.setStyle("-fx-alignment: CENTER;");
    }
}
