/*
In this FXML login controller, we have this methods: 
initialize-->need to override because we implement Initializable

when Create Account is clicked
registerButtonOnAction--> checks if passwords match. If they do, it calls registerUser
registerUser-->inserts user into db
 */
package wolff_patient;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

//imports for database connection
//import java.net.URL;
//import java.sql.Connection;
//import java.sql.Statement;
public class RegistrationController implements Initializable {
    
    @FXML
    private TextField userNameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField repeatPasswordField;
    @FXML
    private TextField SSNumberField;
    @FXML
    private TextField NameField;
    @FXML
    private TextField SurnameField;
    @FXML
    private TextField AdressField;
    @FXML
    private TextField PhoneField;
//    @FXML
//    private Button createAccountButton;
//    @FXML
//    private Label regMessageLabel;
    @FXML
    private Label confirmPasswordLabel;

    
    
    //checks if passwords match and calls register method.
    public void registerButtonOnAction(ActionEvent event) {
        if (passwordField.getText().equals(repeatPasswordField.getText())) {
            confirmPasswordLabel.setText("Passwords match");
            registerUser();
        } else {
            confirmPasswordLabel.setText("Passwords don't match");
        }
    }

    //This method registers new user
    public void registerUser() {

        //  DBConnection connectNow= new dbConnection();
        //  Connection connectdb= connectNow.getConnection();
        String ID = userNameField.getText();
        String password = passwordField.getText();
        String name = NameField.getText();
        String surname = SurnameField.getText();
        String ssnumber = SSNumberField.getText();
        String adress = AdressField.getText();
        String phone = PhoneField.getText();

//        String insertFields = "INSERT INTO user_account(surname, name, username,password, phone, adress, ssnumber) VALUES ("
//        String insertValues = name+surname+username+password;
//        String insertRegister = insertFields +","+ insertValues;...
        //        try {
        //            Statement statement= conectdb.createStatement();
        //              statement.executeUpdate();
        //            regMessageLabel.setText("User registered succesfully");
        //        } catch (Exception e) {
        //            e.printStackTrace();
        //        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}
