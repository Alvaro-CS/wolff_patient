package wolff_patient;

import POJOS.Com_data_client;
import POJOS.Patient;
import java.io.IOException;
import java.io.InputStream;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import utilities.Hashmaker;

public class LogInController implements Initializable {

    private Com_data_client com_data_client;
    private ClientThreadsServer clientThreadsServer; //we create a reference for accesing different methods
    private Patient patientMoved;
    @FXML
    private TextField userNameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label loginMessageLabel;

    /**
     * This method logins the patient
     *
     * @param event
     * @throws java.io.IOException
     */
    @FXML
    private void loginButtonOnAction(ActionEvent event) throws IOException {
        if (com_data_client.getIp_address() == null) {
            loginMessageLabel.setText("Please click on the settings button and introduce "
                    + "\n the server's IP address and Bitalino MAC address");
        } else {

            if (!userNameField.getText().isEmpty() && !passwordField.getText().isEmpty()) {

                validateLogin(event);

            } else {
                //if Fields are empty
                loginMessageLabel.setText("Please enter ID and password");
            }
        }
    }

    /**
     * This method validates login
     *
     * @param event
     * @throws IOException
     */
    @FXML
    private void validateLogin(ActionEvent event) throws IOException {
        this.patientMoved = searchPatient();
        System.out.println("Captured patient:" + patientMoved);
        if (this.patientMoved != null) {
            System.out.println("PATIENT EXISTS");
            openMainMenuPatient(event);

        } else if (com_data_client.getSocket() == null) {
            loginMessageLabel.setText("Connection could not be established");

        } else {
            System.out.println("CONTROL VALIDATE NULL");
            loginMessageLabel.setText("User-password combination not found.\nPlease try again");
        }
    }

    /**
     * This method opens the main menu for the patient
     *
     * @param event
     * @throws IOException
     */
    @FXML
    private void createAccountForm(ActionEvent event) throws IOException {
        if (com_data_client.getIp_address() == null) {
            loginMessageLabel.setText("Please click on the settings button and introduce "
                    + "\n the server's IP address and Bitalino MAC address");
        } else {
            FXMLLoader loader = new FXMLLoader();

            loader.setLocation(getClass().getResource("RegistrationView.fxml"));
            Parent registrationViewParent = loader.load();

            Scene registrationViewScene = new Scene(registrationViewParent);

            RegistrationController controller = loader.getController();
            controller.initData(com_data_client);

            //this line gets the Stage information
            //Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Stage window = new Stage();
            window.setScene(registrationViewScene);
            window.setTitle("WOLFFGRAM");
            window.getIcons().add(new Image("/wolff_patient/images/logo.png"));
            window.centerOnScreen();

            window.show();

            //press the X to close 
            window.setOnCloseRequest(e -> controller.closeWindows(com_data_client));

            // Close the current window
            Stage myStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            myStage.close();
        }

    }

    /**
     * This method opens the main menu for the patient
     *
     * @param event
     * @throws IOException
     */
    @FXML
    private void openMainMenuPatient(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("PatientMenuView.fxml"));
        Parent mainMenuViewParent = loader.load();

        Scene MainMenuViewScene = new Scene(mainMenuViewParent);

        PatientMenuController controller = loader.getController();
        controller.initData(patientMoved, com_data_client);
        //this line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(MainMenuViewScene);
        window.setTitle("WOLFFGRAM");
        window.getIcons().add(new Image("/wolff_patient/images/logo.png"));
        window.centerOnScreen();

        window.show();

        //press the X to close the main menu and release resources
        window.setOnCloseRequest(e -> PatientMenuController.releaseResources(com_data_client));
    }

    /**
     * This method searches for a patient in the server
     *
     * @return
     */
    @FXML
    private Patient searchPatient() {

        try {
            if (!com_data_client.isSocket_created()) {
                try {
                    Socket socket = new Socket(com_data_client.getIp_address(), 9000);
                    if (socket.isConnected()) {
                        System.out.println("Connection established with: " + com_data_client.getIp_address() + " by port: " + 9000);

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

                } catch (IOException e) {
                    System.err.println("No connection established with: " + com_data_client.getIp_address() + " by port: " + 9000);
                }

            }
            if (com_data_client.getSocket() != null) {
                //We send the order to server that we want to search for patients
                String order = "SEARCH_PATIENT";
                ObjectOutputStream objectOutputStream = com_data_client.getObjectOutputStream();
                objectOutputStream.writeObject(order);
                System.out.println("Order " + order + " sent to server");

                //We send the query with ID + password combination to the server
                objectOutputStream.writeObject((Object) userNameField.getText());
                objectOutputStream.writeObject((Object) Hashmaker.getSHA256(passwordField.getText()));
                System.out.println("Query sent");

                //We here need to receive from the server the patient found.
                clientThreadsServer = new ClientThreadsServer();
                clientThreadsServer.setCom_data_client(com_data_client);
                new Thread(clientThreadsServer).start();

                synchronized (clientThreadsServer) {
                    clientThreadsServer.wait(); //wait until patient logs in (if not, it returns null because not enough time to get it).
                }
                return clientThreadsServer.getPatient();
            }

        } catch (IOException ex) {
            System.out.println("Unable to write the object on the server.");
            Logger
                    .getLogger(LogInController.class
                            .getName()).log(Level.SEVERE, null, ex);

        } catch (InterruptedException ex) {
            Logger.getLogger(LogInController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Final");
        return null;

    }

    @FXML
    private void comDataMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ComDataView.fxml"));
        Parent comDataViewParent = loader.load();

        //Call the controller
        ComDataController controller = loader.getController();

        Scene ComDataViewScene = new Scene(comDataViewParent);
        Stage window = new Stage();

        window.setScene(ComDataViewScene);
        window.setTitle("WOLFFGRAM");
        window.getIcons().add(new Image("/wolff_patient/images/logo.png"));
        window.centerOnScreen();

        window.show();

        // When the X is press to closs
        window.setOnCloseRequest(e -> {
            try {
                controller.closeWindows();
            } catch (IOException ex) {
                Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        // Close the current window
        Stage myStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        myStage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        com_data_client = new Com_data_client();
    }

    void initData(Com_data_client com_data) {
        this.com_data_client = com_data;
    }

    void initData(String ipaddress, String bitalinoMac) {
        com_data_client.setIp_address(ipaddress);
        com_data_client.setBitalino_mac(bitalinoMac);
        System.out.println(ipaddress + "|" + bitalinoMac);
    }

}
