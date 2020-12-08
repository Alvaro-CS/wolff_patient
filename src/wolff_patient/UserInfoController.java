/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wolff_patient;

import POJOS.Patient;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author susan
 */
public class UserInfoController implements Initializable {

    private static Patient patientMoved;

    @FXML
    private PasswordField new_password_field;
    @FXML
    private PasswordField repeat_password_field;
    @FXML
    private PasswordField password_field;
    @FXML
    private Button change_button;
    @FXML
    private TextField name_field;
    @FXML
    private TextField surname_field;
    @FXML
    private TextField email_field;
    @FXML
    private TextField telephone_field;
    @FXML
    private TextField name_field1;
    @FXML
    private TextField email_field1;
    @FXML
    private TextField telephone_field1;
    @FXML
    private Button update_button;
    @FXML
    private Button done_button;
    @FXML
    private Label NameLabel;
    @FXML
    private Label passwordLabel;

    /**
     * Initializes the controller class.
     */
    public void initData(Patient patient) {

        this.patientMoved = patient;
        NameLabel.setText("Patient's name:\n " + patientMoved.getName());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    /**
     * Updates information
     *
     * @param event
     * @throws IOException
     */
    private void changePassword(ActionEvent event) throws IOException {
        if (password_field.getText().equals(patientMoved.getPassword()) && new_password_field.getText().equals(repeat_password_field.getText())) {
            patientMoved.setPassword(new_password_field.getText());
            passwordLabel.setText("Password Updated");
        } else {
            passwordLabel.setText("Password can't be updated");
        }
    }

    /**
     * This method updates all the information that has been changed
     *
     * @param event
     * @throws IOException
     */
    private void updateInformation(ActionEvent event) throws IOException {
        if (name_field.getText().isEmpty() == false && name_field.getText().equals(patientMoved.getName()) == false) {
            patientMoved.setName(name_field.getText());
            System.out.println("Name updated");
        }
        if (surname_field.getText().isEmpty() == false && surname_field.getText().equals(patientMoved.getLastName()) == false) {
            patientMoved.setName(name_field.getText());
            System.out.println("Last Name updated");
        }

    }

    /**
     * This method takes the user back to the main menu
     *
     * @param event
     * @throws IOException
     */
    public void backToMenu(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("PatientMenuView.fxml"));
        Parent patientMenuViewParent = loader.load();
        Scene MainMenuViewScene = new Scene(patientMenuViewParent);
        PatientMenuController controller = loader.getController();
        controller.initData(patientMoved);
        //this line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(MainMenuViewScene);
        window.centerOnScreen();

        window.show();
    }

}
