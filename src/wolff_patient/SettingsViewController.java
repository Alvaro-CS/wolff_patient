/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wolff_patient;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author susan
 */
public class SettingsViewController implements Initializable {

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void change_password(MouseEvent event) {
    }

    @FXML
    private void update_information(MouseEvent event) {
    }
    
}
