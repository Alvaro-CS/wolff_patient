package wolff_patient;

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
import javafx.stage.Stage;

public class PatientMenuController implements Initializable {

    private Patient patientMoved;

    @FXML
    private Label nameLabel;

    /**
     * Thid method accets a person to initialize the view
     *
     * @param patient
     */
    public void initData(Patient patient) {

        this.patientMoved = patient;
        nameLabel.setText("Patient's name:\n " + patientMoved.getName());

    }

    /**
     * This method needs to be @override
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     * This method let's the patient change things
     *
     * @param event
     * @throws IOException
     */
    public void openUserInfo(ActionEvent event) throws IOException {

        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("UserInfoView.fxml"));
        Parent userInfoViewParent = loader.load();

        Scene MainMenuViewScene = new Scene(userInfoViewParent);

        UserInfoController controller = loader.getController();
        controller.initData(patientMoved);
        //this line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(MainMenuViewScene);
        window.centerOnScreen();

        window.show();
    }

    /**
     * This method opens the Medical History
     *
     * @param event
     * @throws IOException
     */
    public void openMedicalHistory(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getResource("MedicalHistoryView.fxml"));


        Parent medicalHistoryViewParent = loader.load();
        Scene MedicalHistoryViewScene = new Scene(medicalHistoryViewParent);
        
                MedicalHistoryController controller = loader.getController();
        controller.initData(patientMoved);
        //this line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(MedicalHistoryViewScene);
        window.centerOnScreen();

        window.show();

    }

}
