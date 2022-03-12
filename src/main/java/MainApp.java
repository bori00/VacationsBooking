import controller.loginregisterview.LoginPaneController;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Starter class of the JavaFX application.
 */
public class MainApp extends Application {
    private AnchorPane root;

    /** Method called when the application is started. */
    @Override
    public void start(Stage primaryStage) throws Exception {
        loadRoot();

        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.setTitle("Vacation Booking");
        primaryStage.show();
    }

    private void  loadRoot() throws IOException {
        FXMLLoader mainStageLoader = new FXMLLoader(getClass().getResource("/login_pane.fxml"));
        root = mainStageLoader.load();
        LoginPaneController controller = mainStageLoader.getController();
        root.setPadding(new Insets(10, 10, 10, 10));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
