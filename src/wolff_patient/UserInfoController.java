/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wolff_patient;

import POJOS.Com_data_client;
import POJOS.Patient;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utilities.Hashmaker;

/**
 * FXML Controller class
 *
 * @author susan
 */
public class UserInfoController implements Initializable {
    
    private Com_data_client com_data_client;
    private static Patient patientMoved;

    @FXML
    private Label nameLabel;

    @FXML
    private TextField nameField;

    @FXML
    private Label surnameLabel;

    @FXML
    private TextField surnameField;

    @FXML
    private Label genderLabel;

    @FXML
    private ComboBox genderComboBox;

    @FXML
    private Label telephoneLabel;

    @FXML
    TextField telephoneField;

    @FXML
    private Label adressLabel;

    @FXML
    private TextField adressField;
    @FXML
    private PasswordField new_password_field;

    @FXML
    private PasswordField repeat_password_field;

    @FXML
    private PasswordField password_field;

    @FXML
    private Label passwordLabel;

    /**
     * Initializes the controller class.
     *
     * @param patient
     * @param com_data_client
     */
    public void initData(Patient patient, Com_data_client com_data_client) {
        this.com_data_client=com_data_client;
        patientMoved = patient;
        nameLabel.setText("Patient's name: " + patientMoved.getName());
        surnameLabel.setText("Patient's surname: " + patientMoved.getLastName());
        telephoneLabel.setText("Patient's phone: " + patientMoved.getTelf());
        adressLabel.setText("Patient's adress: " + patientMoved.getAddress());
        genderLabel.setText("Patient's gender: " + patientMoved.getGender());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {        
        genderComboBox.getItems().add("Male");
        genderComboBox.getItems().add("Female");
        genderComboBox.getItems().add("Other");
    }

    /**
     * Updates information
     *
     * @param event
     * @throws IOException
     */
    @FXML
    private void changePassword(ActionEvent event) throws IOException {
        if (Hashmaker.getSHA256(password_field.getText()).equals(patientMoved.getPassword()) && new_password_field.getText().equals(repeat_password_field.getText())) {
            patientMoved.setPassword(Hashmaker.getSHA256(new_password_field.getText()));
            updatePatient();

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
    @FXML
    private void updateInformation(ActionEvent event) throws IOException {
        if (!nameField.getText().isEmpty() && !nameField.getText().equals(patientMoved.getName())) {
            patientMoved.setName(nameField.getText());
            nameLabel.setText("Patient's name: " + patientMoved.getName());
            System.out.println("Name updated");
        }
        if (!surnameField.getText().isEmpty() && !surnameField.getText().equals(patientMoved.getLastName())) {
            patientMoved.setLastName(surnameField.getText());
            System.out.println("Last Name updated");
        }
        if (!telephoneField.getText().isEmpty()
                && Integer.parseInt(telephoneField.getText()) != patientMoved.getTelf()) {
            patientMoved.setTelf(Integer.parseInt(telephoneField.getText()));
            System.out.println("Telephone updated");
        }
        if (!adressField.getText().isEmpty() && !adressField.getText().equals(patientMoved.getAddress())) {
            patientMoved.setAddress(adressField.getText());
            System.out.println("Adress updated");
        }
        if(genderComboBox.getValue()!=null){
        if (!genderComboBox.getValue().equals(patientMoved.getGender())  && genderComboBox.getValue().equals("Female")) {
            patientMoved.setGender(Patient.Gender.FEMALE);
        }
        if (!genderComboBox.getValue().equals(patientMoved.getGender())  && genderComboBox.getValue().equals("Male")) {
            patientMoved.setGender(Patient.Gender.MALE);
        }
        if (!genderComboBox.getValue().equals(patientMoved.getGender())  && genderComboBox.getValue().equals("Other")) {
            patientMoved.setGender(Patient.Gender.OTHER);
        }
        }
        updatePatient();
    }

    //For updating patient in server
    public void updatePatient() {
        ObjectOutputStream objectOutputStream;
        try {

            objectOutputStream = com_data_client.getObjectOutputStream();
            //Sending order
            String order = "UPDATE";
            objectOutputStream.writeObject(order);
            System.out.println("Order" + order + "sent");

            //Sending patient
            objectOutputStream.writeObject(patientMoved);
            objectOutputStream.reset();
            System.out.println("Patient data sent to register in server");
        } catch (IOException ex) {
            System.out.println("Unable to write the object on the server.");
            Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    /**
     * This method takes the user back to the main menu
     *
     * @param event
     * @throws IOException
     */
    @FXML
    public void backToMenu(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("PatientMenuView.fxml"));
        Parent patientMenuViewParent = loader.load();
        Scene MainMenuViewScene = new Scene(patientMenuViewParent);
        PatientMenuController controller = loader.getController();
        controller.initData(patientMoved,com_data_client);
        //this line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(MainMenuViewScene);
        window.centerOnScreen();

        window.show();
    }
/*
    private static void releaseResources(OutputStream outputStream, Socket socket) {
        try {
            outputStream.close();
        } catch (IOException ex) {
            Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
*/
}
