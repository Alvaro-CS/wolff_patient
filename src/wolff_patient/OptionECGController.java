package wolff_patient;

import POJOS.Com_data_client;
import POJOS.Patient;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class OptionECGController implements Initializable {

    private Com_data_client com_data_client;
    private Patient patientMoved;

    public void initData(Patient patient, Com_data_client com_data_client) {
        this.com_data_client = com_data_client;
        this.patientMoved = patient;

    }

    @FXML
    private void withECG(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getResource("BitalinoMenuView.fxml"));

        Parent ECGMenuParent = loader.load();
        Scene ECGMenuViewScene = new Scene(ECGMenuParent);

        BitalinoMenuController controller = loader.getController();
        controller.initData(patientMoved, com_data_client);

        //this line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(ECGMenuViewScene);
        window.centerOnScreen();
        window.setTitle("WOLFFGRAM");
        window.getIcons().add(new Image("/wolff_patient/images/logo.png"));
        window.show();

    }

    @FXML
    private void withoutECG(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getResource("MedicalFormView.fxml"));

        Parent medicalFormViewParent = loader.load();
        Scene MedicalFormViewScene = new Scene(medicalFormViewParent);

        MedicalFormController controller = loader.getController();
        controller.initData(patientMoved, com_data_client);
        //this line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(MedicalFormViewScene);
        window.centerOnScreen();
        window.setTitle("WOLFFGRAM");
        window.getIcons().add(new Image("/wolff_patient/images/logo.png"));

        window.show();
    }

    @FXML
    private void goBackMedicalHistory(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getResource("MedicalHistoryView.fxml"));

        Parent medicalHistoryViewParent = loader.load();
        Scene MedicalHistoryViewScene = new Scene(medicalHistoryViewParent);

        MedicalHistoryController controller = loader.getController();
        controller.initData(patientMoved, com_data_client);
        //this line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(MedicalHistoryViewScene);
        window.centerOnScreen();
        window.setTitle("WOLFFGRAM");
        window.getIcons().add(new Image("/wolff_patient/images/logo.png"));

        window.show();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

}
