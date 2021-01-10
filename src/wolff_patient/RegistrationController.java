package wolff_patient;
//for files:

import POJOS.Com_data_client;
import POJOS.Patient;
import POJOS.Patient.Gender;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
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
import java.util.Date;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;
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
    private TextField addressField;
    @FXML
    private TextField ssNumberField;
    @FXML
    private TextField phoneField;
    @FXML
    private Label regMessageLabel;
    @FXML
    private Label confirmPasswordLabel;

    /**
     * This method takes place when the createAccountButton is clicked. After
     * checking that the username is free and the passwords match, it calls the
     * registerUserOld method
     *
     * @param event
     * @throws java.io.IOException
     */
    @FXML
    private void registerButtonOnAction(ActionEvent event) throws IOException {
        if (usernameIsFree(userNameField.getText())&& userNameField.getText()!=null &&userNameField.getText().length()==9) {
            regMessageLabel.setText("Username available");

            if (passwordField.getText().equals(repeatPasswordField.getText()) && passwordField.getText().isEmpty() != true) {
                System.out.println(passwordField.getText());
                confirmPasswordLabel.setText("Passwords match");
                if (nameField.getText() == null || nameField.getText().isEmpty()) {
                    regMessageLabel.setText("Name parameter is missing");
                    System.out.println("Name parameter is missing");
                } else if (surnameField.getText() == null || surnameField.getText().isEmpty()) {
                    regMessageLabel.setText("Surname parameter is missing");
                    System.out.println("Surname parameter is missing");
                } else if (genderComboBox.getValue() == null) {
                    regMessageLabel.setText("The gender is missing");
                    System.out.println("The gender is missing");
                } else if (dobDatePicker.getValue() == null) {
                    regMessageLabel.setText("The date of birth is missing or is incorrect");
                    System.out.println("The date of birth is missing or is incorrect");
                } else if (ssNumberField.getText() == null || isNumeric(ssNumberField.getText()) == false) {
                    regMessageLabel.setText("Social security number is missing or is incorrect");
                    System.out.println("Social security number is missing or is incorrect");
                } else if (addressField.getText() == null || addressField.getText().isEmpty()) {
                    regMessageLabel.setText("Address parameter is missing");
                    System.out.println("Address parameter is missing");
                } else if (phoneField.getText() == null || isNumeric(phoneField.getText()) == false || phoneField.getText().length() != 9) {
                    regMessageLabel.setText("Phone number is missing or is incorrect");
                    System.out.println("Phone number is missing or is incorrect");
                } else {
                    System.out.println("noooo");
                    registerUser();
                    regMessageLabel.setText("Registration completed");
                    backtoLogin(event);
                }
            } else {
                confirmPasswordLabel.setText("Passwords don't match or are not valid");
            }
        } else {
            regMessageLabel.setText("That username already exists or it is not valid.\nIntroduce a valid one.");
        }

    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    @FXML
    private void backtoLogin(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("LogInView.fxml"));
        Parent LogInViewParent = loader.load();
        Scene LogInViewScene = new Scene(LogInViewParent);

        LogInController controller = loader.getController();
        System.out.println("Registration:" + com_data_client.getIp_address());
        controller.initData(com_data_client);
        //this line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(LogInViewScene);
        window.centerOnScreen();
        window.setTitle("WOLFFGRAM");
        window.getIcons().add(new Image("/wolff_patient/images/logo.png"));
        window.show();
    }

    @FXML
    private void registerUser() {
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
        String address = addressField.getText();
        int phone = Integer.parseInt(phoneField.getText());

        try {
            //Sending order
            String order = "REGISTER";
            ObjectOutputStream objectOutputStream = com_data_client.getObjectOutputStream();
            objectOutputStream.writeObject(order);
            System.out.println("Order" + order + "sent");

            //Sending patient
            Patient p1 = new Patient(ID, password, name, surname, gender, dob, address, ssnumber, phone);

            objectOutputStream.writeObject(p1);
            System.out.println("Patient data (" + p1.getDNI() + ") sent to register in server");
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
    @FXML
    private boolean usernameIsFree(String id) {//TODO
        Patient p;
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

            }
            //Sending order
            String order = "EXISTS";
            ObjectOutputStream objectOutputStream = com_data_client.getObjectOutputStream();
            objectOutputStream.writeObject(order);
            System.out.println("Order" + order + "sent");
            //Sending patient
            objectOutputStream.writeObject(id);
            System.out.println("Patient name sent to server, will check if it exists.");

            ObjectInputStream objectInputStream = com_data_client.getObjectInputStream();
            objectInputStream.readObject();//We read the ORDER. We don't need it for nothing, so we don't save it to a variable.
            Object tmp = objectInputStream.readObject();//we receive the new patient from client
            p = (Patient) tmp;
            if (p != null) {//If received, it will not be null. Username is NOT free.
                return false;
            }
        } catch (IOException ex) {
            System.out.println("Unable to write the object on the server.");
            Logger.getLogger(RegistrationController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RegistrationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true; //If patient is null, username IS free.

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
//    @FXML
//    public void comDataMenu(ActionEvent event) throws IOException {
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(getClass().getResource("ComDataView.fxml"));
//        Parent comDataViewParent = loader.load();
//
//        Scene ComDataViewScene = new Scene(comDataViewParent);
//
//        //   PatientMenuController controller = loader.getController();
//        // controller.initData(patientMoved,com_data_client);
//        //this line gets the Stage information
//        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        window.setScene(ComDataViewScene);
//        window.centerOnScreen();
//
//        window.show();
//    }
    public RegistrationController() {
    }

    void initData(Com_data_client com_data) {
        this.com_data_client = com_data;
    }

//    void initData(String ipaddress, String bitalinoMac) {
//        com_data_client.setIp_address(ipaddress);
//        com_data_client.setBitalino_mac(bitalinoMac);
//        System.out.println("en initdata register");
//        System.out.println(ipaddress);
//    }
}
