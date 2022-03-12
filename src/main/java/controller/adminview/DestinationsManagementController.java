package controller.adminview;

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
import model.Destination;
import service.DestinationsService;
import service.OperationStatus;
import service.view_models.DestinationViewModel;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class DestinationsManagementController {
    @FXML
    private TableView<DestinationViewModel> destinationsTable;

    private static final String DELETE_TEXT = "Delete";

    private final DestinationsService destinationsService = DestinationsService.getInstance();

    @FXML
    public void initialize() {
        new TableController<>(
                DestinationViewModel.class,
                destinationsService,
                destinationsTable) {
            @Override
            protected List<DestinationViewModel> getUpdatedEntries() {
                return destinationsService.findAll();
            }
        };

        destinationsTable.setPlaceholder(new Label("No Destinations to display"));
        setupDeleteButtonColumn();
    }


    @FXML
    private void onNewDestinationButtonClicked(ActionEvent actionEvent) {
        FXMLLoader addDestinationPaneLoader = new FXMLLoader(getClass().getResource(
                "/admin_view/add_destination_pane.fxml"));
        try {
            AnchorPane registerPane = addDestinationPaneLoader.load();
            Stage registerStage = new Stage();
            registerStage.setTitle("Add New Destination");
            registerStage.initOwner(this.destinationsTable.getScene().getWindow());
            registerStage.initModality(Modality.WINDOW_MODAL);
            registerStage.setScene(new Scene(registerPane));
            registerStage.show();
        } catch (IOException e) {
            e.printStackTrace(); //todo
        }
    }

    private void setupDeleteButtonColumn() {
        TableColumn<DestinationViewModel, Void> actionCol = new TableColumn<>(DELETE_TEXT);
        alignCenter(actionCol);

        Callback<TableColumn<DestinationViewModel, Void>, TableCell<DestinationViewModel, Void>> cellFactory
                = new Callback<>() {
            @Override
            public TableCell<DestinationViewModel, Void> call(final TableColumn<DestinationViewModel, Void> param) {
                final TableCell<DestinationViewModel, Void> cell = new TableCell<>() {

                    private final Button btn = new Button(DELETE_TEXT);
                    {
                        btn.setOnAction((ActionEvent event) -> {
                            DestinationViewModel destinationViewModelToDelete =
                                    getTableView().getItems().get(getIndex());
                            onDelete(destinationViewModelToDelete);
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
        destinationsTable.getColumns().add(actionCol);
    }

    private void onDelete(DestinationViewModel destinationViewModel) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText("Are you sure that you want to delete this destination?");
        alert.setContentText("All related" +
                " " +
                "vacation packages and bookings will be automatically deleted as well. This may " +
                "cause an inconvenience to the users who booked these vacation packages.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
           OperationStatus operationStatus = destinationsService.delete(destinationViewModel);
            AlertFactory.showAlert(operationStatus);
        }
    }

    private void alignCenter(TableColumn tableColumn) {
        tableColumn.setStyle( "-fx-alignment: CENTER;");
    }
}
