package controller.util;

import javafx.scene.control.Alert;
import service.IOperationStatus;


public class AlertFactory {

    public static void showAlert(IOperationStatus operationStatus) {
        if (!operationStatus.isSuccessful()) {
            showErrorAlert(operationStatus);
        } else {
            showSuccessAlert(operationStatus);
        }
    }

    private static void showErrorAlert(IOperationStatus operationStatus) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Unsuccessful Operation");
        alert.setHeaderText(operationStatus.getErrorMessage());
        alert.show();
    }

    private static void showSuccessAlert(IOperationStatus operationStatus) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Successful operation");
        alert.show();
    }
}
