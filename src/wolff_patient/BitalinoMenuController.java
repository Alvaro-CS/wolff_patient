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
import POJOS.Com_data_client;
import POJOS.Patient;
import java.io.IOException;
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
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import utilities.ECGplot;

public class BitalinoMenuController implements Initializable {

    private Com_data_client com_data_client;
    private Patient patientMoved;
    BitalinoManager bitalinoManager = null;//Remove after clean
    private Integer[] ecg_data;
    @FXML
    private TextField secondsField;
    @FXML
    private Label messageLabel;

    /**
     * This method gets the patient got from the login to show the data and com
     * data.
     *
     * @param patient
     * @param com_data_client
     */
    public void initData(Patient patient, Com_data_client com_data_client) {
        this.com_data_client = com_data_client;
        this.patientMoved = patient;
    }

    /**
     * Same as initData, but called from Manual with actual ECG
     *
     * @param patient
     * @param com_data_client
     * @param bitalinoManager
     * @param ecg_data
     */
    public void initDataManual(Patient patient, Com_data_client com_data_client, BitalinoManager bitalinoManager, Integer[] ecg_data) {
        this.com_data_client = com_data_client;
        this.patientMoved = patient;
        this.bitalinoManager = bitalinoManager;
        this.ecg_data = ecg_data;
    }

    /**
     *
     * @param event
     */
    public void connectBitalino(ActionEvent event) {
        if (bitalinoManager == null) {
            bitalinoManager = new BitalinoManager("98:D3:C1:FD:2F:EC"); //El user tiene que meterlo
            if (bitalinoManager != null) {
                messageLabel.setTextFill(Color.GREEN);
                messageLabel.setText("Bitalino connected.");
            } else {
                messageLabel.setTextFill(Color.RED);
                messageLabel.setText("Bitalino connection failed.");

            }

        } else if (bitalinoManager.isConnected()) {
            messageLabel.setTextFill(Color.RED);
            messageLabel.setText("Bitalino already connected. Disconnect it to connect a different device.");
        } else { //pURPOSE OF THIS?
            bitalinoManager = new BitalinoManager("98:D3:C1:FD:2F:EC"); //El user tiene que meterlo
            messageLabel.setTextFill(Color.GREEN);

            messageLabel.setText("Bitalino connected.");
        }
    }

    /**
     *
     * @param event
     */
    public void disconnectBitalino(ActionEvent event) {
        if (bitalinoManager == null) {
            messageLabel.setTextFill(Color.RED);
            messageLabel.setText("You need to connect to a Bitalino device first");
        } else {
            bitalinoManager.disconnect();
            messageLabel.setText("Bitalino disconnected.");
        }

    }

    @FXML
    void openManualECGoptions(ActionEvent event) throws IOException {
        if (bitalinoManager != null) {

            FXMLLoader loader = new FXMLLoader();

            loader.setLocation(getClass().getResource("ManualECG.fxml"));

            Parent ManualECGViewParent = loader.load();
            Scene ManualECGViewScene = new Scene(ManualECGViewParent);

            ManualECGController controller = loader.getController();
            controller.initData(patientMoved, bitalinoManager, com_data_client);

            //this line gets the Stage information
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(ManualECGViewScene);
            window.centerOnScreen();

            window.show();
        } else {
            messageLabel.setTextFill(Color.RED);
            messageLabel.setText("You need to connect to a Bitalino device first");
        }

    }

    /**
     *
     * @param event
     * @throws java.lang.InterruptedException
     */
    public void readECGAuto(ActionEvent event) throws InterruptedException {
        Integer seconds;
        ECGThread autoECGThread;
        if (bitalinoManager != null) {
            try {
                seconds = Integer.parseInt(secondsField.getText()); //If it is not Integer, will throw Exception.

                if (seconds > 0) {
                    messageLabel.setText("Recording, please don't move..."); //Why this is not shown
                    messageLabel.setTextFill(Color.CADETBLUE);

                    autoECGThread = new ECGThread(bitalinoManager, "AUTO", seconds);
                    Thread tAuto = new Thread(autoECGThread);
                    tAuto.start();
                    tAuto.join();

                    //Thread.sleep((seconds + 2) * 1000);
                    ecg_data = autoECGThread.getEcg_data();
                    System.out.println("Before show " + ecg_data);
                    messageLabel.setText("ECG recorded!");
                    messageLabel.setTextFill(Color.SEAGREEN);
                    ECGplot e = new ECGplot(ecg_data);
                    e.openECGWindow();

                } else {
                    messageLabel.setTextFill(Color.RED);
                    messageLabel.setText("The number of seconds must be bigger \nthan 0");

                }
            } catch (NumberFormatException e) {
                messageLabel.setTextFill(Color.RED);
                messageLabel.setText("You need to enter a NUMBER of seconds");
            } catch (IOException ex) {
                Logger.getLogger(BitalinoMenuController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            messageLabel.setTextFill(Color.RED);
            messageLabel.setText("You need to connect to a Bitalino device first");
        }

    }

    @FXML
    void backToRecord(ActionEvent event) throws IOException {
        if (bitalinoManager != null && bitalinoManager.isConnected()) {
            disconnectBitalino(event); //We disconnect bitalino if we go back
        }
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

    //Similar to go back, but here we return the ecg. We could reuse the previous method, but done for clarity for the user
    @FXML
    void saveECG(ActionEvent event) throws IOException {

        if (ecg_data != null) {
            if (bitalinoManager != null && bitalinoManager.isConnected()) {
                disconnectBitalino(event); //We disconnect bitalino if we go back
            }

            FXMLLoader loader = new FXMLLoader();

            loader.setLocation(getClass().getResource("MedicalFormView.fxml"));

            Parent medicalFormViewParent = loader.load();
            Scene MedicalFormViewScene = new Scene(medicalFormViewParent);

            MedicalFormController controller = loader.getController();
            controller.initDataECG(patientMoved, com_data_client, ecg_data);
            //this line gets the Stage information
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(MedicalFormViewScene);
            window.centerOnScreen();

            window.show();
        } else {
            messageLabel.setTextFill(Color.RED);
            messageLabel.setText("No ECG recorded");
        }
    }

    //It opens a window with the ECG that has just been recorded
    /*
    void openECGWindow(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ECGShowView.fxml"));

        Stage stage = new Stage();
        stage.setTitle("Your ECG");
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);

        ECGShowController controller = loader.getController();
        controller.initData(ecg_data);

        stage.show();
    }
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

}
