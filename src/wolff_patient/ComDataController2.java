package wolff_patient;

import POJOS.Com_data_client;
import POJOS.Patient;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ComDataController2 implements Initializable {

    private Com_data_client com_data_client;
    private ClientThreadsServer clientThreadsServer; //we create a reference for accesing different methods
    private Patient patientMoved;
    
    @FXML
    private TextField ipAdressField;
    @FXML
    private TextField bitalinoField;
    @FXML
    private Label messageLabel;

//    @FXML
//    public void SaveComData(ActionEvent event) throws IOException {
//        com_data_client.setIp_address(ipAdressField.getText());
//        com_data_client.setBitalino_adress(bitalinoField.getText());
//        goBackLogin(event);
//
//    }
    
        public void goBackRegistration(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getResource("RegistrationView.fxml"));


        Parent RegistrationViewParent = loader.load();
        Scene LogInViewScene = new Scene(RegistrationViewParent);
        RegistrationController controller = loader.getController();
        controller.initData(ipAdressField.getText(),bitalinoField.getText());

        //this line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(LogInViewScene);
        window.centerOnScreen();

        window.show();

    }
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }


}
