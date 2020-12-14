package wolff_patient;

import POJOS.Clinical_record;
import POJOS.Patient;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class NewMedicalHistoryController implements Initializable {

    private Patient patientMoved;

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

    /**
     * This method gets the info from symptoms, extra info and ECG to create the
     * Clinical Record object.
     *
     * @param event
     */
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
        Integer[] ECG = null;//TODO save REAL ECG
        //Date is created automatically in the constructor.
        //Check out the order of parameters if changed in constructor of the class.
        clinical_record = new Clinical_record(id, palpitations, dizziness, fatigue, anxiety,
                chest_pain, difficulty_breathing, fainting, ECG, extra_info);
        patientMoved.getClinical_record_list().add(clinical_record);
        //TODO send the new patient to the server and replace it by this new version.
        updatePatient();
        goBackMedicalHistory(event);
    }

    public void updatePatient() {
        OutputStream outputStream = null;
        ObjectOutputStream objectOutputStream = null;
        Socket socket = null;
        try {
            socket = new Socket("localhost", 9000);
            outputStream = socket.getOutputStream();
            objectOutputStream = new ObjectOutputStream(outputStream);
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
        } finally {
            releaseResources(outputStream, socket);

        }
    }

    private static void releaseResources(OutputStream outputStream, Socket socket) {
        try {
            outputStream.close();
        } catch (IOException ex) {
            Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     * This method goes back to the Medical History
     *
     * @param event
     * @throws IOException
     */
    public void goBackMedicalHistory(ActionEvent event) throws IOException {
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
    
    @FXML
    public void ECGMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getResource("BitalinoMenuView.fxml"));

        Parent ECGMenuParent = loader.load();
        Scene ECGMenuViewScene = new Scene(ECGMenuParent);
        
        BitalinoMenuController controller = loader.getController();
        controller.initData(patientMoved);
        
        //this line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(ECGMenuViewScene);
        window.centerOnScreen();
        window.show();
    }

    /**
     * Thid method gets the patient got from the login to show the data.
     *
     * @param patient
     */
    public void initData(Patient patient) {

        this.patientMoved = patient;

    }
}
