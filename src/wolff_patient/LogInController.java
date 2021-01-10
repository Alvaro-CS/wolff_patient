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
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(registrationViewScene);
            window.centerOnScreen();

            window.setTitle("WOLFFGRAM");
            window.getIcons().add(new Image("/wolff_patient/images/logo.png"));

            window.show();
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
        window.centerOnScreen();
        window.setTitle("WOLFFGRAM");
        window.getIcons().add(new Image("/wolff_patient/images/logo.png"));

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
    @FXML
    private Patient searchPatient() {

        try {
            if (!com_data_client.isSocket_created()) {
                try {
                    Socket socket = new Socket(com_data_client.getIp_address(), 9000);
                    if (socket.isConnected()) {
                        System.out.println("Conexión establecida con la dirección: " + com_data_client.getIp_address() + " a través del puerto: " + 9000);

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

                } catch (Exception e) {
                    System.err.println("No se pudo establecer conexión con: " + com_data_client.getIp_address() + " a travez del puerto: " + 9000);
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
    @FXML
    private void comDataMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ComDataView.fxml"));
        Parent comDataViewParent = loader.load();

        Scene ComDataViewScene = new Scene(comDataViewParent);

        //   PatientMenuController controller = loader.getController();
        // controller.initData(patientMoved,com_data_client);
        //this line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("WOLFFGRAM");
        window.getIcons().add(new Image("/wolff_patient/images/logo.png"));

        window.setScene(ComDataViewScene);
        window.centerOnScreen();

        window.show();
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
