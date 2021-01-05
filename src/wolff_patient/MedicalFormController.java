/*
In this FXML login controller, we have this methods: 
initialize-->need to override because we implement Initializable

when Login is clicked
loginButtonOnAction--> if the fields aren't empty, it checks if the id and password are correct (exist on the db). (calls validateLogin)
validateLogin--> checks if account exists 

when Signup is clicked
createAccountForm--> opens registration view
 */
package wolff_patient;

import BITalino.BitalinoManager;
import POJOS.Clinical_record;
import POJOS.Com_data_client;
import POJOS.Patient;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import utilities.ECGplot;

public class MedicalFormController implements Initializable {

    private Com_data_client com_data_client;
    private Patient patientMoved;

    private Integer[] ecg_data = null;
    @FXML
    RadioButton palpitations_no;
    @FXML
    RadioButton dizziness_no;
    @FXML
    RadioButton fatigue_no;
    @FXML
    RadioButton anxiety_no;
    @FXML
    RadioButton chest_pain_no;
    @FXML
    RadioButton difficulty_breathing_no;
    @FXML
    RadioButton fainting_no;

    @FXML
    TextArea infoArea;

    boolean palpitations, dizziness, fatigue, anxiety, chest_pain, difficulty_breathing,
            fainting;
        private Clinical_record clinical_record;


        @FXML
    public void saveRecord(ActionEvent event) throws IOException {
        //As default is no, we only need to check if it is selected. If not, "YES"
        //needs to be selected
        palpitations = !palpitations_no.isSelected();
        dizziness = !dizziness_no.isSelected();
        fatigue = !fatigue_no.isSelected();
        anxiety = !anxiety_no.isSelected();
        chest_pain = !chest_pain_no.isSelected();
        difficulty_breathing = !difficulty_breathing_no.isSelected();
        fainting = !fainting_no.isSelected();

        Integer id = patientMoved.getClinical_record_list().size() + 1;
        String extra_info = infoArea.getText();
        //Date is created automatically in the constructor.
        //Check out the order of parameters if changed in constructor of the class.
        clinical_record = new Clinical_record(id, palpitations, dizziness, fatigue, anxiety,
                chest_pain, difficulty_breathing, fainting, ecg_data, extra_info);
        patientMoved.getClinical_record_list().add(clinical_record);
        updatePatient();
        goBackMedicalHistory(event);
    }
    
       public void updatePatient() {
        try {
            ObjectOutputStream objectOutputStream = com_data_client.getObjectOutputStream();
            //Sending order
            String order = "UPDATE";
            objectOutputStream.writeObject(order);
            System.out.println("Order" + order + "sent");

            //Sending patient
            objectOutputStream.writeObject(patientMoved);
            System.out.println("Patient data sent to register in server");

        } catch (IOException ex) {
            System.out.println("Unable to write the object on the server.");
            Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
         public void goBackMedicalHistory(ActionEvent event) throws IOException {
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

        window.show();

    }
    public void initData(Patient patient, Com_data_client com_data_client) {
        this.com_data_client = com_data_client;
        this.patientMoved = patient;

    }
    
    public void initDataECG(Patient patient, Com_data_client com_data_client, Integer[] ecg_data) {
        this.com_data_client = com_data_client;
        this.patientMoved = patient;
        this.ecg_data = ecg_data;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

}
