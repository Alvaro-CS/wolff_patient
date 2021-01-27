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
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import utilities.ECGplot;

public class ManualECGController implements Initializable {

    private ECGThread manualECGThread; //we create a reference for accesing different methods
    private Thread t;

    private Com_data_client com_data_client;
    private Patient patientMoved;
    private BitalinoManager bitalinoManager;
    private Integer[] ecg_data = null;
    boolean start = false;

    @FXML
    private Label msgLabel;

    /**
     * This method gets the patient got from the login to show the data.
     *
     * @param patient
     * @param bitalinoManager
     * @param com_data_client
     */
    public void initData(Patient patient, BitalinoManager bitalinoManager, Com_data_client com_data_client) {
        this.com_data_client = com_data_client;
        this.patientMoved = patient;
        this.bitalinoManager = bitalinoManager;
    }

    @FXML
    private void backToECGMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getResource("BitalinoMenuView.fxml"));

        Parent BitalinoMenuParent = loader.load();
        Scene ECGMenuViewScene = new Scene(BitalinoMenuParent);

        BitalinoMenuController controller = loader.getController();
        controller.initDataManual(patientMoved, com_data_client, bitalinoManager, ecg_data); //ecg_data can be null if not recorded.

        //this line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(ECGMenuViewScene);
        window.setTitle("WOLFFGRAM");
        window.getIcons().add(new Image("/wolff_patient/images/logo.png"));
        window.centerOnScreen();
        window.show();
    }

    @FXML
    private void startManualECG(ActionEvent event) throws InterruptedException {
        if (bitalinoManager == null) {
            msgLabel.setText("Please, go back and connect to the Bitalino again.");
            msgLabel.setTextFill(Color.RED);
        } else {
            start = true;
            msgLabel.setText("Recording, please don't move...");
            msgLabel.setTextFill(Color.CADETBLUE);
            manualECGThread = new ECGThread(bitalinoManager, "MANUAL");
            t = new Thread(manualECGThread);
            t.start();
        }
    }

    @FXML
    private void stopManualECG(ActionEvent event) throws InterruptedException {//GET ECG of manual here
        if (start) {
            try {
                msgLabel.setText("Finishing, please wait...");
                msgLabel.setTextFill(Color.ORANGE);
                bitalinoManager.setStop(true);
                t.join();
                if (bitalinoManager.isLost_com()) {
                    msgLabel.setText("Communications interrupted.\nPlease, go back and connect again to the Bitalino.");
                    msgLabel.setTextFill(Color.RED);
                    bitalinoManager = null;
                } else {
                    msgLabel.setText("ECG recorded!");
                    msgLabel.setTextFill(Color.SEAGREEN);
                    ecg_data = manualECGThread.getEcg_data();
                    ECGplot e = new ECGplot(ecg_data);
                    e.openECGWindow();
                    System.out.println(bitalinoManager.isLost_com());
                }
                start = false;

            } catch (IOException ex) {
                Logger.getLogger(ManualECGController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            msgLabel.setText("Press START first");
            msgLabel.setTextFill(Color.RED);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public BitalinoManager getBitalinoManager() {
        return bitalinoManager;
    }

}
