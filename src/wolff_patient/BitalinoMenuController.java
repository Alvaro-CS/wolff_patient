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
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
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
     * @throws java.io.IOException
     */
    @FXML
    private void connectBitalino(ActionEvent event) throws IOException {
        if (com_data_client.getBitalino_mac().isEmpty()) {
            bitalinoMacNeeded(event);
        } else {
            System.out.println("mac:" + com_data_client.getBitalino_mac());
            if (bitalinoManager == null) {
                bitalinoManager = new BitalinoManager(com_data_client.getBitalino_mac());
                if (bitalinoManager.isMac_correct()) {
                    if (bitalinoManager != null && bitalinoManager.isConnected()) { //It has been connected succesfully
                        messageLabel.setTextFill(Color.GREEN);
                        messageLabel.setText("Bitalino connected.");
                    } else { //Error connecting
                        messageLabel.setTextFill(Color.RED);
                        bitalinoManager = null;
                        messageLabel.setText("Bitalino connection failed.");
                    }
                } else {
                    messageLabel.setTextFill(Color.RED);
                    messageLabel.setText("The introduced MAC address does not exist.");
                    com_data_client.setBitalino_mac("");
                    bitalinoManager = null;
                }
            } else if (bitalinoManager != null && bitalinoManager.isConnected()) { //It was connected in a beginning.
                messageLabel.setTextFill(Color.RED);
                messageLabel.setText("Bitalino already connected. Disconnect it to connect a different device.");
            }
        }
    }

    @FXML
    private void bitalinoMacNeeded(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("BitalinoMacView.fxml"));
        Parent bitalinoMacViewParent = loader.load();

        Scene BitalinoMacViewScene = new Scene(bitalinoMacViewParent);

        BitalinoMacController controller = loader.getController();
        controller.initData(patientMoved, com_data_client);
        //this line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(BitalinoMacViewScene);
        window.setTitle("WOLFFGRAM");
        window.getIcons().add(new Image("/wolff_patient/images/logo.png"));
        window.centerOnScreen();

        window.show();
    }

    /**
     *
     * @param event
     */
    @FXML
    private void disconnectBitalino(ActionEvent event) {
        if (bitalinoManager == null) {
            messageLabel.setTextFill(Color.RED);
            messageLabel.setText("You need to connect to a Bitalino device first");
        } else {
            bitalinoManager.disconnect();
            messageLabel.setText("Bitalino disconnected.");
        }

    }

    @FXML
    private void openManualECGoptions(ActionEvent event) throws IOException {
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
            window.setTitle("WOLFFGRAM");
            window.getIcons().add(new Image("/wolff_patient/images/logo.png"));
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
    @FXML
    private void readECGAuto(ActionEvent event) throws InterruptedException {
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

                    ecg_data = autoECGThread.getEcg_data();
                    if (bitalinoManager.isLost_com()) {
                        messageLabel.setText("Communications interrupted.\nYou can save the resulting ECG or connect again to the Bitalino.");
                        messageLabel.setTextFill(Color.RED);
                        bitalinoManager = null;
                    } else {
                        messageLabel.setText("ECG recorded!");
                        messageLabel.setTextFill(Color.SEAGREEN);
                    }
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
    private void backToRecord(ActionEvent event) throws IOException {
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
        window.setTitle("WOLFFGRAM");
        window.getIcons().add(new Image("/wolff_patient/images/logo.png"));
        window.centerOnScreen();

        window.show();
    }

    //Similar to go back, but here we return the ecg. We could reuse the previous method, but done for clarity for the user
    @FXML
    private void saveECG(ActionEvent event) throws IOException {

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
            window.setTitle("WOLFFGRAM");
            window.getIcons().add(new Image("/wolff_patient/images/logo.png"));
            window.centerOnScreen();

            window.show();
        } else {
            messageLabel.setTextFill(Color.RED);
            messageLabel.setText("No ECG recorded");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

}
