/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wolff_patient;

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
    private Label nameLabel;
    @FXML
    private Label passwordLabel;

    /**
     * Initializes the controller class.
     * @param patient
     */
    public void initData(Patient patient) {

        patientMoved = patient;
        nameLabel.setText("Patient's name: " + patientMoved.getName());
        
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
    @FXML
    private void changePassword(ActionEvent event) throws IOException {
        if (password_field.getText().equals(patientMoved.getPassword()) && new_password_field.getText().equals(repeat_password_field.getText())) {
            patientMoved.setPassword(new_password_field.getText());
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
        if (!name_field.getText().isEmpty() && !name_field.getText().equals(patientMoved.getName())) {
            patientMoved.setName(name_field.getText());
            nameLabel.setText("Patient's name: " + patientMoved.getName());

            System.out.println("Name updated");
        }
        if (!surname_field.getText().isEmpty() && !surname_field.getText().equals(patientMoved.getLastName())) {
            patientMoved.setName(name_field.getText());
            System.out.println("Last Name updated");
        }
        updatePatient();
    }

    //For updating patient in server
    public void updatePatient() {
        OutputStream outputStream = null;
        ObjectOutputStream objectOutputStream = null;
        Socket socket = null;
        try {
            socket = new Socket("localhost", 9000);
            outputStream = socket.getOutputStream();
            objectOutputStream = new ObjectOutputStream(outputStream);
            //Sending order
            String order = "UPDATE";
            objectOutputStream.writeObject(order);
            System.out.println("Order" + order + "sent");

            //Sending patient
            objectOutputStream.writeObject(patientMoved);
            System.out.println("Patient data sent to register in server");

        } catch (IOException ex) {
            System.out.println("Unable to write the object on the server.");
            Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            releaseResources(outputStream, socket);

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
        controller.initData(patientMoved);
        //this line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(MainMenuViewScene);
        window.centerOnScreen();

        window.show();
    }

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
}
