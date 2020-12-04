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

import POJOS.Patient;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LogInController implements Initializable {

    private PatientMenuController patientController;
    private ClientThreadsServer clientThreadsServer; //we create a reference for accesing different methods

    @FXML
    private TextField userNameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label loginMessageLabel;

    /**
     * class constructor
     */
    public LogInController() {
    }

    /**
     *
     * @param event
     */
    public void loginButtonOnAction(ActionEvent event) {
        if (userNameField.getText().isEmpty() == false && passwordField.getText().isEmpty() == false) {
            validateLogin();

        } else {
            //if Fields are empty
            loginMessageLabel.setText("Please enter ID and password");
        }

    }

    @FXML
    public Patient validateLogin() {
        Patient p = searchPatient();
        if (p != null) {
            System.out.println("PATIENT EXISTS");
            openMainMenu(p);

        } else {
            System.out.println("CONTROL VALIDATE NULL");
            loginMessageLabel.setText("User no found.\nPlease try again");
        }

        return p;
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

    public void openMainMenu(Patient p) {
        try {
            PatientMenuController.setValues(p);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PatientMenuView.fxml"));
            Parent root = (Parent) loader.load();
            patientController = loader.getController();
            patientController.setPatientName(p.getName());
            PatientMenuController.setController(patientController);
            Scene scene = new Scene(root);
            Stage registerStage = new Stage();
            registerStage.setScene(scene);
            registerStage.show();
            patientController.setPatientName(p.getName());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Patient searchPatient() {
        OutputStream outputStream = null;
        ObjectOutputStream objectOutputStream = null;
        Socket socket = null;
        try {
            socket = new Socket("localhost", 9000);
            outputStream = socket.getOutputStream();
            objectOutputStream = new ObjectOutputStream(outputStream);
            
            //We send the order to server that we want to search for patients
            String order = "SEARCH_PATIENT";
            objectOutputStream.writeObject(order);
            System.out.println("Order "+ order+ " sent to server");

            //We send the query with ID + password combination to the server
            objectOutputStream.writeObject((Object)userNameField.getText());
            objectOutputStream.writeObject((Object)passwordField.getText());
            System.out.println("Query sent");
            
            //We here need to receive from the server the patient found.
            clientThreadsServer=new ClientThreadsServer();
            new Thread(clientThreadsServer).start();
         /*   while(!clientThreadsServer.isPatient_logged()){}; 
            System.out.println("Patient logged in");*/
            Thread.sleep(1000); //wait until patient logs in (if not, it returns null because not enough time to get it.
            return clientThreadsServer.getPatient();
            
        } catch (IOException ex) {
            System.out.println("Unable to write the object on the server.");
            Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            releaseResources(outputStream, socket);

        }
        System.out.println("Final");
        return null;

    }
/*
    public Patient searchPatientOld() {
        Patient patient;
        ArrayList<Patient> patients2 = new ArrayList<>();
        String filename = "patientFiles";

        try {
            ObjectInputStream is = new ObjectInputStream(new FileInputStream(filename));
            patients2 = (ArrayList<Patient>) is.readObject();
            for (int i = 0; i < patients2.size(); i++) {
                if (patients2.get(i).getDNI().equalsIgnoreCase(userNameField.getText())
                        && patients2.get(i).getPassword().equals(passwordField.getText())) {
                    patient = patients2.get(i);
                    return patient;
                }
            }
            is.close();
        } catch (EOFException ex) {
            System.out.println("All data have been correctly read.");
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return null;
    }*/

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public PatientMenuController getPatientController() {
        return patientController;
    }

}
