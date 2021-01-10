package wolff_patient;

import POJOS.Com_data_client;
import POJOS.Patient;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class BitalinoMacController implements Initializable {


    @FXML
    private TextField bitalinoField;
    @FXML
    private Label messageLabel;
    private Com_data_client com_data_client;
    private Patient patientMoved;
/**
 * Goes back to bitalino menu after introducing the bitalino mac address
 * @param event
 * @throws IOException 
 */
    @FXML
    private void goBackBitalinoMenu(ActionEvent event) throws IOException {
        if (bitalinoField.getText().isEmpty()) {
            messageLabel.setText("The bitalino mac address is required");
        } else {

            FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getResource("BitalinoMenuView.fxml"));

        Parent ECGMenuParent = loader.load();
        Scene ECGMenuViewScene = new Scene(ECGMenuParent);

        BitalinoMenuController controller = loader.getController();
        com_data_client.setBitalino_mac(bitalinoField.getText());
        controller.initData(patientMoved, com_data_client);

        //this line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(ECGMenuViewScene);
        window.setTitle("WOLFFGRAM");
        window.getIcons().add(new Image("/wolff_patient/images/logo.png"));
        window.centerOnScreen();
        window.show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    public void initData(Patient patient, Com_data_client com_data_client) {
        this.com_data_client = com_data_client;
        this.patientMoved = patient;

    }
}
