package controller.adminview;

import controller.util.TableController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import model.Destination;
import service.DestinationsService;
import service.view_models.DestinationViewModel;

import java.util.List;
import java.util.Optional;

public class DestinationsManagementController {
    @FXML
    private TableView<DestinationViewModel> destinationsTable;

    private static final String DELETE_TEXT = "Delete";

    private final DestinationsService destinationsService = new DestinationsService();

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
    private void onNewProductButtonClicked(ActionEvent actionEvent) {

    }

    /**
     * Adds a delete button for each row of the table.
     */
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

    protected void onDelete(DestinationViewModel destinationViewModel) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText("Are you sure that you want to delete this destination?");
        alert.setContentText("All related" +
                " " +
                "vacation packages and bookings will be automatically deleted as well. This may " +
                "cause an incovnevience to the users who booked these vacation packages.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
           // todo: delete
        }
    }

    private void alignCenter(TableColumn tableColumn) {
        tableColumn.setStyle( "-fx-alignment: CENTER;");
    }
}
