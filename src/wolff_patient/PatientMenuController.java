package wolff_patient;

import POJOS.Com_data_client;
import POJOS.Patient;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class PatientMenuController implements Initializable {

    private Patient patientMoved;
    private Com_data_client com_data_client;

    @FXML
    private Label nameLabel;

    /**
     * Thid method accets a person to initialize the view
     *
     * @param patient
     * @param com_data_client
     */
    public void initData(Patient patient, Com_data_client com_data_client) {

        this.patientMoved = patient;
        this.com_data_client = com_data_client;
        nameLabel.setText("Patient's name:\n " + patientMoved.getName());

    }

    /**
     * This method needs to be @override
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     * This method let's the patient change things
     *
     * @param event
     * @throws IOException
     */
    public void openUserInfo(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("UserInfoView.fxml"));
        Parent userInfoViewParent = loader.load();
        

        UserInfoController controller = loader.getController();
        controller.initData(patientMoved, com_data_client);
        
        Scene MainMenuViewScene = new Scene(userInfoViewParent);
        //this line gets the Stage information
        //Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Stage window = new Stage();
        window.setScene(MainMenuViewScene);
        window.centerOnScreen();

        window.show();
        
        // When the X is press to close
        window.setOnCloseRequest(e -> controller.closeWindows());

        // Close the current window
        Stage myStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        myStage.close();
    }

    /**
     * This method opens the Medical History
     *
     * @param event
     * @throws IOException
     */
    public void openMedicalHistory(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getResource("MedicalHistoryView.fxml"));

        Parent medicalHistoryViewParent = loader.load();
        Scene MedicalHistoryViewScene = new Scene(medicalHistoryViewParent);

        MedicalHistoryController controller = loader.getController();
        controller.initData(patientMoved, com_data_client);
        //this line gets the Stage information
        //Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Stage window = new Stage();
        window.setScene(MedicalHistoryViewScene);
        window.centerOnScreen();

        window.show();
        
        // Indico que debe hacer al cerrar
        window.setOnCloseRequest(e -> controller.closeWindows());

        // Ciero la ventana donde estoy
        Stage myStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        myStage.close();

    }

    /**
     * This method logs out and returns to log In screen.
     *
     * @param event
     * @throws IOException
     */
    public void logOut(ActionEvent event) throws IOException {
        releaseResources(com_data_client);
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getResource("LogInView.fxml"));

        Parent LogInViewParent = loader.load();
        Scene LogInViewScene = new Scene(LogInViewParent);

        //this line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(LogInViewScene);
        window.centerOnScreen();

        window.show();
        window.close();

    }

    public static void releaseResources(Com_data_client c) {
        try {
            c.getInputStream().close();
        } catch (IOException ex) {
            Logger.getLogger(PatientMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            c.getObjectInputStream().close();
        } catch (IOException ex) {
            Logger.getLogger(PatientMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            c.getOutputStream().close();
        } catch (IOException ex) {
            Logger.getLogger(PatientMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            c.getObjectOutputStream().close();
        } catch (IOException ex) {
            Logger.getLogger(PatientMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            c.getSocket().close();
        } catch (IOException ex) {
            Logger.getLogger(PatientMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
