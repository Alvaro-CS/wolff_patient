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

    private Patient patientMoved;
    private BitalinoManager bitalinoManager;
    @FXML
    private Label msgLabel;

    /**
     * This method gets the patient got from the login to show the data.
     *
     * @param patient
     */
    public void initData(Patient patient, BitalinoManager bitalinoManager) {

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
        controller.initData(patientMoved);

        //this line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(ECGMenuViewScene);
        window.centerOnScreen();
        window.show();
    }

    @FXML
    void startManualECG(ActionEvent event) throws InterruptedException {
        msgLabel.setText("Recording, please don't move...");
        msgLabel.setTextFill(Color.CADETBLUE);
        Thread.sleep(500);
        bitalinoManager.startManualECG();
        msgLabel.setText("ECG recorded!");
        msgLabel.setTextFill(Color.SEAGREEN);
        //getECG(); FINISH WHEN DIFFERENT SCREENS

    }

    @FXML
    void stopManualECG(ActionEvent event) {
        bitalinoManager.setStop(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
