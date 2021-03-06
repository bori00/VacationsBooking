package controller.adminview;

import controller.util.AlertFactory;
import controller.util.FormatterFactory;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import service.IDestinationService;
import service.IOperationStatus;
import service.IVacationPackageService;
import service.service_impl.DestinationService;
import service.service_impl.OperationStatus;
import service.service_impl.VacationPackageService;
import service.view_models.DestinationViewModel;
import service.view_models.VacationPackageAdminViewModel;

public class EditVacationPackageController {
    @FXML
    private TextArea extraDetailsTextArea;
    //    @FXML private ChoiceBox<DestinationViewModel> destinationChoiceBox;
    @FXML
    private ComboBox<DestinationViewModel> destinationComboBox;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField priceTextField;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private TextField nrPlacesTextField;
    @FXML
    private Button editButton;

    private final IVacationPackageService vacationPackageService =
            VacationPackageService.getInstance();

    private final IDestinationService destinationsService = DestinationService.getInstance();

    private VacationPackageAdminViewModel vacationPackageAdminViewModel;

    @FXML
    void initialize() {
        destinationComboBox.setItems(FXCollections.observableList(destinationsService.findAll()));
        priceTextField.setTextFormatter(FormatterFactory.getDoubleFormatter());
        nrPlacesTextField.setTextFormatter(FormatterFactory.getIntegerFormatter());
    }

    void initVacationPackage(VacationPackageAdminViewModel vacationPackageAdminViewModel) {
        this.vacationPackageAdminViewModel = vacationPackageAdminViewModel;
        fillInputs();
    }

    @FXML
    private void onEditButtonClicked(ActionEvent actionEvent) {
        int nrPlaces;
        try {
            nrPlaces = Integer.parseInt(nrPlacesTextField.getText());
        } catch (NumberFormatException e) {
            AlertFactory.showAlert(OperationStatus.getFailedOperationStatus("Please specify the " +
                    "number of places!"));
            return;
        }

        String destinationName;
        if (destinationComboBox.getValue() == null) {
            AlertFactory.showAlert(OperationStatus.getFailedOperationStatus("Please specify the " +
                    "destination!"));
            return;
        }
        destinationName = destinationComboBox.getValue().getName();

        IOperationStatus operationStatus = vacationPackageService.edit(
                vacationPackageAdminViewModel.getId(),
                nameTextField.getText(),
                destinationName,
                Float.parseFloat(priceTextField.getText()),
                startDatePicker.getValue(),
                endDatePicker.getValue(),
                extraDetailsTextArea.getText(),
                nrPlaces);
        AlertFactory.showAlert(operationStatus);
        if (operationStatus.isSuccessful()) {
            closeStage();
        } else {
            fillInputs();
        }
    }

    private void fillInputs() {
        nameTextField.setText(vacationPackageAdminViewModel.getName());
        priceTextField.setText(String.valueOf(vacationPackageAdminViewModel.getPrice()));
        destinationComboBox.setValue(new DestinationViewModel(
                vacationPackageAdminViewModel.getDestinationId(),
                vacationPackageAdminViewModel.getDestinationName()));
        extraDetailsTextArea.setText(vacationPackageAdminViewModel.getExtraDetails());
        startDatePicker.setValue(vacationPackageAdminViewModel.getStartDate());
        endDatePicker.setValue(vacationPackageAdminViewModel.getEndDate());
        nrPlacesTextField.setText(String.valueOf(vacationPackageAdminViewModel.getTotalNrPlaces()));
    }

    private void closeStage() {
        Stage stage = (Stage) editButton.getScene().getWindow();
        stage.close();
    }

}
