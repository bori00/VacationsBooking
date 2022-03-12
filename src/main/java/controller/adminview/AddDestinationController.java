package controller.adminview;

import controller.util.AlertFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.IDestinationService;
import service.IOperationStatus;
import service.service_impl.DestinationService;

public class AddDestinationController {
    @FXML
    private TextField nameTextField;

    private final IDestinationService destinationsService = DestinationService.getInstance();

    @FXML
    private void onAddButtonClicked(ActionEvent actionEvent) {
        IOperationStatus status = destinationsService.add(nameTextField.getText());
        AlertFactory.showAlert(status);
        if (status.isSuccessful()) {
            ((Stage) this.nameTextField.getScene().getWindow()).close();
        }
    }
}
