/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wolff_patient;

import POJOS.Com_data_client;
import POJOS.Patient;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
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
    @FXML
    private Label errorLabel;

    /**
     * Initializes the controller class.
     *
     * @param patient
     * @param com_data_client
     */
    public void initData(Patient patient, Com_data_client com_data_client) {
        this.com_data_client = com_data_client;
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
    private void changePassword(ActionEvent event) throws IOException, InterruptedException {
        if (Hashmaker.getSHA256(password_field.getText()).equals(patientMoved.getPassword()) && new_password_field.getText().equals(repeat_password_field.getText())) {
            patientMoved.setPassword(Hashmaker.getSHA256(new_password_field.getText()));
            updatePatient();
            passwordLabel.setTextFill(Color.GREEN);
            passwordLabel.setText("Password Updated");

        } else {
            passwordLabel.setTextFill(Color.RED);
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
    private void updateInformation(ActionEvent event) throws IOException, InterruptedException {
        if (!nameField.getText().isEmpty() && !nameField.getText().equals(patientMoved.getName())) {
            patientMoved.setName(nameField.getText());
            nameLabel.setText("Patient's name: " + patientMoved.getName());
            System.out.println("Name updated");
        }
        if (!surnameField.getText().isEmpty() && !surnameField.getText().equals(patientMoved.getLastName())) {
            patientMoved.setLastName(surnameField.getText());
            surnameLabel.setText("Patient's surname: " + patientMoved.getLastName());
            System.out.println("Last Name updated");
        }
        if (!telephoneField.getText().isEmpty()&& Integer.parseInt(telephoneField.getText()) != patientMoved.getTelf()) {
            patientMoved.setTelf(Integer.parseInt(telephoneField.getText()));
            telephoneLabel.setText("Patient's phone number: " + patientMoved.getTelf());
            System.out.println("Telephone updated");
        }
        if (!adressField.getText().isEmpty() && !adressField.getText().equals(patientMoved.getAddress())) {
            patientMoved.setAddress(adressField.getText());
            adressLabel.setText("Patient's address: " + patientMoved.getAddress());
            System.out.println("Adress updated");
        }
        if (genderComboBox.getValue() != null) {
            if (!genderComboBox.getValue().equals(patientMoved.getGender()) && genderComboBox.getValue().equals("Female")) {
                patientMoved.setGender(Patient.Gender.FEMALE);
                genderLabel.setText("Patient's gender: " + patientMoved.getGender());
            }
            if (!genderComboBox.getValue().equals(patientMoved.getGender()) && genderComboBox.getValue().equals("Male")) {
                patientMoved.setGender(Patient.Gender.MALE);
                 genderLabel.setText("Patient's gender: " + patientMoved.getGender());
            }
            if (!genderComboBox.getValue().equals(patientMoved.getGender()) && genderComboBox.getValue().equals("Other")) {
                patientMoved.setGender(Patient.Gender.OTHER);
                 genderLabel.setText("Patient's gender: " + patientMoved.getGender());
            }
        }
        updatePatient();
    }

    /**
     * This method updates patient's data in the server
     *
     */
    @FXML
    private void updatePatient() throws InterruptedException {
        ObjectOutputStream objectOutputStream;
        ObjectInputStream objectInputStream;

        try {
            objectInputStream = com_data_client.getObjectInputStream();
            objectOutputStream = com_data_client.getObjectOutputStream();

            //Sending order
            String order = "UPDATE";
            objectOutputStream.writeObject(order);
            System.out.println("Order" + order + "sent");
            Thread.sleep(100); //Time for receiving the signal that checks server is active.
            int signal = objectInputStream.available();
            System.out.println("Signal: " + signal);
            if (signal == 0) {//Connection with the server refused, double check.
                errorLabel.setTextFill(Color.RED);
                errorLabel.setText("Connection to the server lost.\nPlease log out and try again.");
            } else {
                //Sending patient
                System.out.println(objectInputStream.readByte());
                objectOutputStream.writeObject(patientMoved);
                objectOutputStream.reset();
                System.out.println("Patient data sent to register in server");
            }

            // }
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
        controller.initData(patientMoved, com_data_client);
        //this line gets the Stage information
        //Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Stage window = new Stage();
        window.setScene(MainMenuViewScene);
        window.centerOnScreen();
        window.setTitle("WOLFFGRAM");
        window.getIcons().add(new Image("/wolff_patient/images/logo.png"));

        window.show();
        
        Stage myStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        myStage.close();
    }

    /**
     * This method handles closing the app by X button
     *
     */
    
}
