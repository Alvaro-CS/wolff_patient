package wolff_patient;
//for files:

import POJOS.Com_data_client;
import POJOS.Patient;
import POJOS.Patient.Gender;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.Socket;

import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.util.ArrayList;
import java.util.Date;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import utilities.Hashmaker;

public class RegistrationController implements Initializable {

    private Com_data_client com_data_client;

    @FXML
    private TextField userNameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField repeatPasswordField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField surnameField;
    @FXML
    private ComboBox genderComboBox;
    @FXML
    private DatePicker dobDatePicker;
    @FXML
    private TextField adressField;
    @FXML
    private TextField ssNumberField;
    @FXML
    private TextField phoneField;
    @FXML
    private Label regMessageLabel;
    @FXML
    private Label confirmPasswordLabel;
    @FXML
    private Label regStatusLabel;

    String filename = "patientFiles";

    /**
     * This method takes place when the createAccountButton is clicked. After
     * checking that the username is free and the passwords match, it calls the
     * registerUserOld method
     *
     * @param event
     */
    public void registerButtonOnAction(ActionEvent event) throws IOException {

        if (usernameIsFree(userNameField.getText())) {//FINISH
            regMessageLabel.setText("Username available");

            if (passwordField.getText().equals(repeatPasswordField.getText())) {
                confirmPasswordLabel.setText("Passwords match");

                registerUser();
                regStatusLabel.setText("Registration completed");
                backtoLogin(event);
            } else {
                confirmPasswordLabel.setText("Passwords don't match");
            }
        } else {
            regMessageLabel.setText("That username already exists");
        }

    }

    public void backtoLogin(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("LogInView.fxml"));
        Parent LogInViewParent = loader.load();
        Scene LogInViewScene = new Scene(LogInViewParent);

        LogInController controller = loader.getController();
        controller.initData(com_data_client);
        //this line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(LogInViewScene);
        window.centerOnScreen();
        window.show();
    }

    public void registerUser() {
        String ID = userNameField.getText();
        String password = Hashmaker.getSHA256(passwordField.getText());
        String name = nameField.getText();
        String surname = surnameField.getText();

        Gender gender = null;
        if (genderComboBox.getValue().equals("Male")) {
            gender = Gender.MALE;
        } else if (genderComboBox.getValue().equals("Female")) {
            gender = Gender.FEMALE;
        } else if (genderComboBox.getValue().equals("Other")) {
            gender = Gender.OTHER;
        }
        LocalDate localDate = dobDatePicker.getValue();
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        Date dob = Date.from(instant);
        int ssnumber = Integer.parseInt(ssNumberField.getText());
        String adress = adressField.getText();
        int phone = Integer.parseInt(phoneField.getText());

        try {
            //Sending order
            String order = "REGISTER";
            ObjectOutputStream objectOutputStream = com_data_client.getObjectOutputStream();
            objectOutputStream.writeObject(order);
            System.out.println("Order" + order + "sent");

            //Sending patient
            //Patient p = new Patient(ID, password, name, surname);
            Patient p1 = new Patient(ID, password, name, surname, gender, dob, adress, ssnumber, phone);

            objectOutputStream.writeObject(p1);
            System.out.println("Patient data sent to register in server");
            p1.toString();
        } catch (IOException ex) {
            System.out.println("Unable to write the object on the server.");
            Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * this method checks each patient stored in the file to see if the username
     * is already registered
     *
     * @param id
     * @return boolean
     */
    public boolean usernameIsFree(String id) {//TODO

        try {
            if (!com_data_client.isSocket_created()) {
                Socket socket = new Socket(com_data_client.getIp_address(), 9000);
                com_data_client.setSocket(socket);
                OutputStream outputStream = socket.getOutputStream();
                com_data_client.setOutputStream(outputStream);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                com_data_client.setObjectOutputStream(objectOutputStream);
                InputStream inputStream = socket.getInputStream();
                com_data_client.setInputStream(inputStream);
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                com_data_client.setObjectInputStream(objectInputStream);

                com_data_client.setSocket_created(true);

            }/*
            //Sending order
            String order = "EXISTS";
            ObjectOutputStream objectOutputStream= com_data_client.getObjectOutputStream();
            objectOutputStream.writeObject(order);
            System.out.println("Order" + order + "sent");

            //Sending patient
            objectOutputStream.writeObject(id);
            System.out.println("Patient name sent to server");
            //TODO receive patient from server. If received, return false. If received null, return true. 
             */
        } catch (IOException ex) {
            System.out.println("Unable to write the object on the server.");
            Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true; //TODO finish
    }

    /**
     * this method needs the @override
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        genderComboBox.getItems().add("Male");
        genderComboBox.getItems().add("Female");
        genderComboBox.getItems().add("Other");
    }

    /*
    private static void releaseResources(OutputStream outputStream, Socket socket) {
        try {
            outputStream.close();

        } catch (IOException ex) {
            Logger.getLogger(LogInController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        try {
            socket.close();

        } catch (IOException ex) {
            Logger.getLogger(LogInController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }*/
    public RegistrationController() {
    }

    void initData(Com_data_client com_data) {
        this.com_data_client = com_data;
    }

}
