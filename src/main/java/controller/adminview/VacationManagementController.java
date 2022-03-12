package controller.adminview;

import controller.util.TableController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import service.VacationPackageService;
import service.view_models.VacationPackageAdminViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VacationManagementController {
    @FXML
    private TableView<VacationPackageAdminViewModel> vacationsTable;

    @FXML
    private Button newVacationButton;

    private static final String DELETE_TEXT = "Delete";

    private final VacationPackageService vacationPackageService =
            VacationPackageService.getInstance();

    @FXML
    public void initialize() {
        new TableController<>(
                VacationPackageAdminViewModel.class,
                vacationPackageService,
                vacationsTable) {
            @Override
            protected List<VacationPackageAdminViewModel> getUpdatedEntries() {
                return vacationPackageService.findAllForAdmin(new ArrayList<>());
            }
        };

        vacationsTable.setPlaceholder(new Label("No Vacations to display"));
        setupDeleteButtonColumn();
    }

    @FXML
    private void onNewVacationButtonClicked(ActionEvent actionEvent) {
    }

    private void setupDeleteButtonColumn() {
        TableColumn<VacationPackageAdminViewModel, Void> actionCol = new TableColumn<>(DELETE_TEXT);
        alignCenter(actionCol);

        Callback<TableColumn<VacationPackageAdminViewModel, Void>, TableCell<VacationPackageAdminViewModel, Void>> cellFactory
                = new Callback<>() {
            @Override
            public TableCell<VacationPackageAdminViewModel, Void> call(final TableColumn<VacationPackageAdminViewModel, Void> param) {
                final TableCell<VacationPackageAdminViewModel, Void> cell = new TableCell<>() {

                    private final Button btn = new Button(DELETE_TEXT);

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            VacationPackageAdminViewModel vacationPackageAdminViewModelToDelete =
                                    getTableView().getItems().get(getIndex());
                            onDelete(vacationPackageAdminViewModelToDelete);
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

    private void onDelete(VacationPackageAdminViewModel vacationPackageAdminViewModel) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText("Are you sure that you want to delete this vacation package?");
        alert.setContentText("All related bookings will be automatically deleted as well. This " +
                "may " +
                "cause an inconvenience to the users who booked these vacation packages.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            // todo: delete
        }
    }


    private void alignCenter(TableColumn tableColumn) {
        tableColumn.setStyle("-fx-alignment: CENTER;");
    }
}
