/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wolff_patient;

/**
 *
 * @author susan
 */
import POJOS.Patient;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class PatientMenuController implements Initializable{

    	private static PatientMenuController patientController;
	private static Patient patientAccount;

    public PatientMenuController() {
    }


        
       
/**
 * This method needs to be @override
 * @param location
 * @param resources 
 */
    @Override
    public void initialize(URL location, ResourceBundle resources){
        
    }
    /**
     * This method opens the Settings View
     * @param event 
     */
    public void openSettings(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("SettingsView.fxml"));
            Scene scene = new Scene(root);
            Stage registerStage = new Stage();
            registerStage.setScene(scene);
            registerStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

 
}
