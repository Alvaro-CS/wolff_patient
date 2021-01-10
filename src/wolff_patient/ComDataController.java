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

public class ComDataController implements Initializable {

    @FXML
    private TextField ipAddressField;
    @FXML
    private TextField bitalinoField;
    @FXML
    private Label messageLabel;

    public void goBackLogin(ActionEvent event) throws IOException {
        if (ipAddressField.getText().isEmpty()) {
            messageLabel.setText("The IP address is required");
        } else {

            FXMLLoader loader = new FXMLLoader();

            loader.setLocation(getClass().getResource("LogInView.fxml"));

            Parent LogInViewParent = loader.load();
            Scene LogInViewScene = new Scene(LogInViewParent);
            LogInController controller = loader.getController();
            controller.initData(ipAddressField.getText(), bitalinoField.getText());

            //this line gets the Stage information
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(LogInViewScene);
            window.centerOnScreen();

            window.show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
    public void closeWindows() throws IOException {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LogInView.fxml"));

            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(scene);
            stage.show();
            
            try{

            Stage myStage = (Stage) (this.messageLabel.getScene().getWindow());
            myStage.close();
            
            }catch(NullPointerException e){
                System.out.println("Exception caught");
            }

        } catch (IOException ex) {
            Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
