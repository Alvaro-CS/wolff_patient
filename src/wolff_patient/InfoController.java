package wolff_patient;

import POJOS.Com_data_client;
import POJOS.Patient;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class InfoController implements Initializable {

    private Com_data_client com_data_client;
    private Patient patientMoved;

    /**
     * This method needs to be @override
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void initData(Patient patient, Com_data_client com_data_client) {
        this.com_data_client = com_data_client;
        this.patientMoved = patient;

    }

    @FXML
    private void backToMenu(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("PatientMenuView.fxml"));
        Parent patientMenuViewParent = loader.load();
        Scene MainMenuViewScene = new Scene(patientMenuViewParent);
        PatientMenuController controller = loader.getController();
        controller.initData(patientMoved, com_data_client);
        //this line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(MainMenuViewScene);
        window.setTitle("WOLFFGRAM");
        window.getIcons().add(new Image("/wolff_patient/images/logo.png"));
        window.centerOnScreen();

        window.show();
    }
    @FXML
    private Label label;

    @FXML
    private void button1(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/wolff_patient/InfoView1.fxml"));
        Stage stage = new Stage();
        stage.setTitle("WOLFFGRAM");
        stage.getIcons().add(new Image("/wolff_patient/images/logo.png"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);

        stage.show();

    }

    @FXML
    private void button2(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/wolff_patient/InfoView2.fxml"));
        Stage stage = new Stage();
        stage.setTitle("WOLFFGRAM");
        stage.getIcons().add(new Image("/wolff_patient/images/logo.png"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);

        stage.show();
    }

    @FXML
    private void button3(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/wolff_patient/InfoView3.fxml"));
        Stage stage = new Stage();
        stage.setTitle("WOLFFGRAM");
        stage.getIcons().add(new Image("/wolff_patient/images/logo.png"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }
}
