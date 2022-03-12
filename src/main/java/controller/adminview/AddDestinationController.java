package controller.adminview;

import controller.loginregisterview.util.AlertFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.DestinationsService;
import service.OperationStatus;

public class AddDestinationController {
    @FXML
    private TextField nameTextField;

    private final DestinationsService destinationsService = new DestinationsService();

    @FXML
    private void onAddButtonClicked(ActionEvent actionEvent) {
        OperationStatus status = destinationsService.add(nameTextField.getText());
        AlertFactory.showAlert(status);
        if (status.isSuccessful()) {
            ((Stage) this.nameTextField.getScene().getWindow()).close();
        }
    }
}
