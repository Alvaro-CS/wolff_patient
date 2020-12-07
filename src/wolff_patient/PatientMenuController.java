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

public class PatientMenuController implements Initializable {

    private static PatientMenuController patientMenuController;
    private static Patient patientAccount;
    private static UserInfoController userController;
    private static LogInController loginController;

    public PatientMenuController() {
    }

    @FXML
    private Label nameLabel;

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
     * This method opens the Settings View
     *
     * @param event
     */
    public void openUserInfo(ActionEvent event) {
        try {
            
            Parent root = FXMLLoader.load(getClass().getResource("UserInfoView.fxml"));
            Scene scene = new Scene(root);
            Stage registerStage = new Stage();
            registerStage.setScene(scene);
            registerStage.show();
           // Patient p=loginController.searchPatient();
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("UserInfoView.fxml"));
//            Parent root = (Parent) loader.load();
//            userController = loader.getController();
////            patientMenuController.setPatientName(p.getName());
//            UserInfoController.setController(userController);
//            Scene scene = new Scene(root);
//            Stage registerStage = new Stage();
//            registerStage.setScene(scene);
//            registerStage.show();
          //  pc.setPatientName(p.getName());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
      public void openMedicalHistory(ActionEvent event) {
        try {
            
            Parent root = FXMLLoader.load(getClass().getResource("MedicalHistoryView.fxml"));
            Scene scene = new Scene(root);
            Stage registerStage = new Stage();
            registerStage.setScene(scene);
            registerStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    	public static void setValues(Patient p) {
		patientAccount = p;
	}

    

    	public void setPatientName(String name) {
		nameLabel.setText("Patient's name: " + name );

	}
        	
	public static void setController(PatientMenuController controller) {
		patientMenuController = controller;
	}
    
}
