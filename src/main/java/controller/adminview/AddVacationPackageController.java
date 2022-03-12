package controller.adminview;

import controller.util.AlertFactory;
import controller.util.FormatterFactory;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import service.DestinationsService;
import service.OperationStatus;
import service.VacationPackageService;
import service.view_models.DestinationViewModel;

import java.util.Arrays;

public class AddVacationPackageController {
    @FXML private TextArea extraDetailsTextArea;
    @FXML private ChoiceBox<DestinationViewModel> destinationChoiceBox;
    @FXML private TextField nameTextField;
    @FXML private TextField priceTextField;
    @FXML private DatePicker startDatePicker;
    @FXML private DatePicker endDatePicker;
    @FXML private TextField nrPlacesTextField;
    @FXML private Button createButton;

    private final VacationPackageService vacationPackageService =
            VacationPackageService.getInstance();

    private final DestinationsService destinationsService = DestinationsService.getInstance();

    @FXML
    void initialize() {
        destinationChoiceBox.setItems(FXCollections.observableList(destinationsService.findAll()));
        priceTextField.setTextFormatter(FormatterFactory.getDoubleFormatter());
        nrPlacesTextField.setTextFormatter(FormatterFactory.getIntegerFormatter());
    }

    @FXML
    private void onAddButtonClicked(ActionEvent actionEvent) {
        int nrPlaces;
        try {
            nrPlaces = Integer.parseInt(nrPlacesTextField.getText());
        } catch (NumberFormatException e) {
            AlertFactory.showAlert(OperationStatus.getFailedOperationStatus("Please specify the " +
                    "number of places!"));
            return;
        }

        String destinationName;
        if (destinationChoiceBox.getValue() == null) {
            AlertFactory.showAlert(OperationStatus.getFailedOperationStatus("Please specify the " +
                    "destination!"));
            return;
        }
        destinationName = destinationChoiceBox.getValue().getName();

        OperationStatus operationStatus = vacationPackageService.add(
                nameTextField.getText(),
                destinationName,
                Float.parseFloat(priceTextField.getText()),
                startDatePicker.getValue(),
                endDatePicker.getValue(),
                extraDetailsTextArea.getText(),
                nrPlaces);
        AlertFactory.showAlert(operationStatus);
        if (operationStatus.isSuccessful()) {
            ((Stage) this.nameTextField.getScene().getWindow()).close();
        }
    }

}
