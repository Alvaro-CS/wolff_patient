package wolff_patient;

import POJOS.Clinical_record;
import POJOS.Com_data_client;
import POJOS.Patient;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

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
    private Label errorLabel;
    @FXML
    TextArea infoArea;

    int signal = 1;

    boolean palpitations, dizziness, fatigue, anxiety, chest_pain, difficulty_breathing,
            fainting;
    private Clinical_record clinical_record;

    ObjectOutputStream objectOutputStream;
    ObjectInputStream objectInputStream;

    @FXML
    private void saveRecord(ActionEvent event) throws IOException, InterruptedException {
        objectOutputStream = com_data_client.getObjectOutputStream();
        objectInputStream = com_data_client.getObjectInputStream();

        //Sending order
        String order = "UPDATE";
        objectOutputStream.writeObject(order);
        System.out.println("Order ORDER" + order + "sent");
        Thread.sleep(100); //Time for receiving the signal that checks server is active.
        signal = objectInputStream.available();
        System.out.println("Signal: " + signal);

        if (signal == 0) {
            errorLabel.setTextFill(Color.RED);
            errorLabel.setText("Connection to the server lost.\nPlease log out and try again.");
        } else {
            System.out.println(objectInputStream.readByte());

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
    }

    @FXML
    private void updatePatient() throws InterruptedException {
        try {

            //Sending patient
            objectOutputStream.writeObject(patientMoved);
            objectOutputStream.reset();
            System.out.println("Patient data sent to register in server");

        } catch (IOException ex) {
            System.out.println("Unable to write the object on the server.");
            Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        window.setTitle("WOLFFGRAM");
        window.getIcons().add(new Image("/wolff_patient/images/logo.png"));
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
