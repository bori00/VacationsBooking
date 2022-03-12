package controller.util;

import javafx.scene.control.Alert;
import service.OperationStatus;


public class AlertFactory {

    public static void showAlert(OperationStatus operationStatus) {
        if (!operationStatus.isSuccessful()) {
            showErrorAlert(operationStatus);
        } else {
            showSuccessAlert(operationStatus);
        }
    }

    private static void showErrorAlert(OperationStatus operationStatus) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Unsuccessful Operation");
        alert.setHeaderText(operationStatus.getErrorMsg());
        alert.show();
    }

    private static void showSuccessAlert(OperationStatus operationStatus) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Successful operation");
        alert.show();
    }
}
