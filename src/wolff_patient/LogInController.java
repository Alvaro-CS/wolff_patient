package wolff_patient;

import POJOS.Patient;
import java.io.IOException;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LogInController implements Initializable {

    private ClientThreadsServer clientThreadsServer; //we create a reference for accesing different methods
    private Patient patient;

    @FXML
    private TextField userNameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label loginMessageLabel;

    /**
     *This method logins the patient
     * @param event
     */
    public void loginButtonOnAction(ActionEvent event) throws IOException {

        if (userNameField.getText().isEmpty() == false && passwordField.getText().isEmpty() == false) {
            validateLogin(event);

        } else {
            //if Fields are empty
            loginMessageLabel.setText("Please enter ID and password");
        }
    }

    /**
     * This method validates login
     *
     * @param event
     * @throws IOException
     */
    @FXML
    public void validateLogin(ActionEvent event) throws IOException {
        this.patient = searchPatient();
        if (this.patient != null) {
            System.out.println("PATIENT EXISTS");
            openMainMenuPatient(event);

        } else {
            System.out.println("CONTROL VALIDATE NULL");
            loginMessageLabel.setText("User no found.\nPlease try again");
        }
    }

    /**
     * This method opens the main menu for the patient
     *
     * @param event
     * @throws IOException
     */
    public void createAccountForm(ActionEvent event) throws IOException {
        Parent registrationViewParent = FXMLLoader.load(getClass().getResource("RegistrationView.fxml"));
        Scene registrationViewScene = new Scene(registrationViewParent);
        //this line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(registrationViewScene);

        window.show();
    }

    /**
     * This method opens the main menu for the patient
     *
     * @param event
     * @throws IOException
     */
    public void openMainMenuPatient(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("PatientMenuView.fxml"));
        Parent mainMenuViewParent = loader.load();

        Scene MainMenuViewScene = new Scene(mainMenuViewParent);

        PatientMenuController controller = loader.getController();
        controller.initData(patient);
        //this line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(MainMenuViewScene);
        window.centerOnScreen();

        window.show();
    }

//    generic open menu        
//    public void openMainMenu(ActionEvent event)throws IOException{
//        Parent mainMenuViewParent = FXMLLoader.load(getClass().getResource("PatientMenuView.fxml"));
//        Scene MainMenuViewScene = new Scene(mainMenuViewParent);
//        //this line gets the Stage information
//        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        window.setScene(MainMenuViewScene);
//        window.centerOnScreen();
//
//        window.show();
//    }
    /**
     * This method searches for a patient
     *
     * @return
     */
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
            System.out.println("Order " + order + " sent to server");

            //We send the query with ID + password combination to the server
            objectOutputStream.writeObject((Object) userNameField.getText());
            objectOutputStream.writeObject((Object) passwordField.getText());
            System.out.println("Query sent");

            //We here need to receive from the server the patient found.
            clientThreadsServer = new ClientThreadsServer();
            new Thread(clientThreadsServer).start();
            /*   while(!clientThreadsServer.isPatient_logged()){}; 
            System.out.println("Patient logged in");*/
            Thread.sleep(1000); //wait until patient logs in (if not, it returns null because not enough time to get it.
            return clientThreadsServer.getPatient();

        } catch (IOException ex) {
            System.out.println("Unable to write the object on the server.");
            Logger
                    .getLogger(LogInController.class
                            .getName()).log(Level.SEVERE, null, ex);

        } catch (InterruptedException ex) {
            Logger.getLogger(LogInController.class
                    .getName()).log(Level.SEVERE, null, ex);
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
                if (patients2.get(i).getDNI().equalsIgnoreCase(username)
                        && patients2.get(i).getPassword().equals(password)) {
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
            Logger.getLogger(LogInController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        try {
            socket.close();

        } catch (IOException ex) {
            Logger.getLogger(LogInController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
