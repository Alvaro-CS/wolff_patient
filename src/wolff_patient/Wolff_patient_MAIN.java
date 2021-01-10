package wolff_patient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Wolff_patient_MAIN extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("LogInView.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene); 
        stage.centerOnScreen();
        stage.setTitle("WOLFFGRAM");
        stage.getIcons().add(new Image("/wolff_patient/images/logo.png"));

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
