/*
In this FXML login controller, we have this methods: 
initialize-->need to override because we implement Initializable

when Login is clicked
loginButtonOnAction--> if the fields aren't empty, it checks if the id and password are correct (exist on the db). (calls validateLogin)
validateLogin--> checks if account exists 

when Signup is clicked
createAccountForm--> opens registration view
 */
package wolff_patient;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import java.sql.Connection;
import java.sql.Statement;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LogInController implements Initializable {

    @FXML
    private TextField userNameField;
    @FXML
    private PasswordField passwordField;
//    @FXML
//    private Button logInButton;
//    @FXML
//    private Button signUpButton;
    @FXML
    private Label loginMessageLabel;

    public void loginButtonOnAction(ActionEvent event) {
        if (userNameField.getText().isEmpty() == false && passwordField.getText().isEmpty() == false) {
            validateLogin();

        } else {
            //if Fields are empty
            loginMessageLabel.setText("Please enter ID and password");
        }

    }

    @FXML
    public void validateLogin() {

        //This method checks if there's a user whith the ID and password
        //  DBConnection connectNow= new dbConnection();
        //  Connection connectdb= connectNow.getConnection();
        // String verifyLogin="SELECT count(1) FROM user_account WHERE username=""+userNameField.getText()+" AND password=" " +passwordField.getText()+" "  "
//        try {
//            Statement statement= conectdb.createStatement();
//            ResultSet queryResult=statement.executeQuery(verifyLogin);
//            
//            while(queryResult.next){
//                if(queryResult.getInt(columnindex 1)==1){
//                            loginMessageLabel.setText("Log in correct)";
//                              createAccountForm(); no entiendo por que
//
//                    
//                    
//                }else{
//                                 loginMessageLabel.setText("Invalid ID or password. Please try again");
//
//                    
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @FXML
    //opens registration form
    public void createAccountForm() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("RegistrationView.fxml"));
            Scene scene = new Scene(root);
            Stage registerStage = new Stage();
            registerStage.setScene(scene);
            registerStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
