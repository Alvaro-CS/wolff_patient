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
import BITalino.Frame;
import POJOS.Com_data_client;
import POJOS.Patient;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ManualECGController implements Initializable {

    private ECGThread manualECGThread; //we create a reference for accesing different methods

    private Com_data_client com_data_client;
    private Patient patientMoved;
    private BitalinoManager bitalinoManager;
    private Integer[] ecg_data=null;
    boolean start=false;

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
    void backToECGMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getResource("BitalinoMenuView.fxml"));

        Parent BitalinoMenuParent = loader.load();
        Scene ECGMenuViewScene = new Scene(BitalinoMenuParent);

        BitalinoMenuController controller = loader.getController();
        controller.initDataManual(patientMoved, com_data_client,bitalinoManager,ecg_data); //ecg_data can be null if not recorded.

        //this line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(ECGMenuViewScene);
        window.centerOnScreen();
        window.show();
    }

    @FXML
    public void startManualECG(ActionEvent event) throws InterruptedException {
        start=true;
        msgLabel.setText("Recording, please don't move...");
        msgLabel.setTextFill(Color.CADETBLUE);
        manualECGThread = new ECGThread(bitalinoManager, "MANUAL");
        new Thread(manualECGThread).start();

    }

    @FXML
    void stopManualECG(ActionEvent event) throws InterruptedException {//GET ECG of manual here
        if(start){
        msgLabel.setText("Finishing, please wait...");
        msgLabel.setTextFill(Color.ORANGE);
        bitalinoManager.setStop(true);
       
        
        Thread.sleep(2000); //some seconds to make sure it finish properly
        
        msgLabel.setText("ECG recorded!");
        msgLabel.setTextFill(Color.SEAGREEN);
        ecg_data=manualECGThread.getEcg_data();
        System.out.println(ecg_data);
        start=false;
        }
        else{
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
