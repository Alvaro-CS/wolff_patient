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

import BITalino.BitalinoManager;
import POJOS.Com_data_client;
import POJOS.Patient;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class BitalinoMenuController implements Initializable {

    private Com_data_client com_data_client;
    private Patient patientMoved;
    BitalinoManager bitalinoManager = null;//Remove after clean
    private Integer[] ecg_data;
    @FXML
    private TextField secondsField;
    @FXML
    private Label messageLabel;
    @FXML
    private Pane paneChart;

    /**
     * This method gets the patient got from the login to show the data and com
     * data.
     *
     * @param patient
     * @param com_data_client
     */
    public void initData(Patient patient, Com_data_client com_data_client) {
        this.com_data_client = com_data_client;
        this.patientMoved = patient;
    }

    /**
     * Same as initData, but called from Manual with actual ECG
     *
     * @param patient
     * @param com_data_client
     * @param bitalinoManager
     * @param ecg_data
     */
    public void initDataManual(Patient patient, Com_data_client com_data_client, BitalinoManager bitalinoManager, Integer[] ecg_data) {
        this.com_data_client = com_data_client;
        this.patientMoved = patient;
        this.bitalinoManager = bitalinoManager;
        this.ecg_data = ecg_data;
        if (ecg_data != null) {
            showECG();
        }
    }

    /**
     *
     * @param event
     */
    public void connectBitalino(ActionEvent event) {
        if (bitalinoManager == null) {
            bitalinoManager = new BitalinoManager("98:D3:C1:FD:2F:EC"); //El user tiene que meterlo
            if (bitalinoManager != null) {
                messageLabel.setTextFill(Color.GREEN);
                messageLabel.setText("Bitalino connected.");
            } else {
                messageLabel.setTextFill(Color.RED);
                messageLabel.setText("Bitalino connection failed.");

            }

        } else if (bitalinoManager.isConnected()) {
            messageLabel.setTextFill(Color.RED);
            messageLabel.setText("Bitalino already connected. Disconnect it to connect a different device.");
        } else { //pURPOSE OF THIS?
            bitalinoManager = new BitalinoManager("98:D3:C1:FD:2F:EC"); //El user tiene que meterlo
            messageLabel.setTextFill(Color.GREEN);

            messageLabel.setText("Bitalino connected.");
        }
    }

    /**
     *
     * @param event
     */
    public void disconnectBitalino(ActionEvent event) {
        if (bitalinoManager == null) {
            messageLabel.setTextFill(Color.RED);
            messageLabel.setText("You need to connect to a Bitalino device first");
        } else {
            bitalinoManager.disconnect();
            messageLabel.setText("Bitalino disconnected.");
        }

    }

    @FXML
    void openManualECGoptions(ActionEvent event) throws IOException {
        if (bitalinoManager != null) {

            FXMLLoader loader = new FXMLLoader();

            loader.setLocation(getClass().getResource("ManualECG.fxml"));

            Parent ManualECGViewParent = loader.load();
            Scene ManualECGViewScene = new Scene(ManualECGViewParent);

            ManualECGController controller = loader.getController();
            controller.initData(patientMoved, bitalinoManager, com_data_client);

            //this line gets the Stage information
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(ManualECGViewScene);
            window.centerOnScreen();

            window.show();
        } else {
            messageLabel.setTextFill(Color.RED);
            messageLabel.setText("You need to connect to a Bitalino device first");
        }

    }

    /**
     *
     * @param event
     * @throws java.lang.InterruptedException
     */
    public void readECGAuto(ActionEvent event) throws InterruptedException {
        Integer seconds;
        ECGThread autoECGThread;
        if (bitalinoManager != null) {
            try {
                seconds = Integer.parseInt(secondsField.getText()); //If it is not Integer, will throw Exception.

                if (seconds > 0) {
                    messageLabel.setText("Recording, please don't move..."); //Why this is not shown
                    messageLabel.setTextFill(Color.CADETBLUE);

                    autoECGThread = new ECGThread(bitalinoManager, "AUTO", seconds);
                    Thread tAuto=new Thread(autoECGThread);
                    tAuto.start();
                    tAuto.join();
                   
                    //Thread.sleep((seconds + 2) * 1000);

                    ecg_data = autoECGThread.getEcg_data();
                    System.out.println("Before show " + ecg_data);
                    messageLabel.setText("ECG recorded!");
                    messageLabel.setTextFill(Color.SEAGREEN);
                    showECG();

                } else {
                    messageLabel.setTextFill(Color.RED);
                    messageLabel.setText("The number of seconds must be bigger \nthan 0");

                }
            } catch (NumberFormatException e) {
                messageLabel.setTextFill(Color.RED);
                messageLabel.setText("You need to enter a NUMBER of seconds");
            }
        } else {
            messageLabel.setTextFill(Color.RED);
            messageLabel.setText("You need to connect to a Bitalino device first");
        }

    }

    /**
     * This method shows a line chart on-screen with the ECG that has been
     * received. It adjusts max and min values of the chart. Line chart is shown
     * in a pane.
     *
     *
     */
    public void showECG() {
        System.out.println("Dentro show " + ecg_data);
        XYChart.Series series = new XYChart.Series();
        //  series.setName("ECG data");
        //populating the series with data
        int min = Integer.MAX_VALUE;
        int max = 0;
        for (int i = 0; i < ecg_data.length; i++) {
            System.out.println(i);
            series.getData().add(new XYChart.Data(i, ecg_data[i]));
            if (min > ecg_data[i]) {
                min = ecg_data[i];
            }
            if (max < ecg_data[i]) {
                max = ecg_data[i];
            }
        }

        paneChart.getChildren().clear();

        final NumberAxis xAxis = new NumberAxis(0, ecg_data.length, 1);
        final NumberAxis yAxis = new NumberAxis(min - 5, max + 5, 0.1);//lower, upper, tick
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);

        lineChart.getXAxis().setLabel("Time");
        lineChart.getYAxis().setLabel("Amplitude");

        //creating the chart
        lineChart.setTitle("ECG");
        //Removing the symbols of the line chart
        lineChart.setCreateSymbols(false);
        //defining a series
        lineChart.getData().add(series);
        paneChart.getChildren().add(lineChart);
        System.out.println("Shown");
    }

    /**
     * This method creates a socket for sending an ECG to a server. It gets the
     * ECG data from "bitalinoManager" object, gets the useful information, that
     * is located in analog[0] and writes the int[] object with all ECG values.
     */
    /*  public void sendECG() {
        OutputStream outputStream = null;
        ObjectOutputStream objectOutputStream = null;
        System.out.println("Envia");
        Socket socket = null;
        try {
            socket = new Socket("localhost", 9000);
            outputStream = socket.getOutputStream();
        } catch (IOException ex) {
            System.out.println("It was not possible to connect to the server.");
            System.exit(-1);
            Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            objectOutputStream = new ObjectOutputStream(outputStream);
            //We get the data (Frame class) from Bitalino, get the useful info (int) and send it.

            objectOutputStream.writeObject(ecg_data);

        } catch (IOException ex) {
            System.out.println("Unable to write the object on the server.");
            Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            releaseResources(outputStream, socket);

        }
    }
     */
    @FXML
    void backToRecord(ActionEvent event) throws IOException {
        if (bitalinoManager != null && bitalinoManager.isConnected()) {
            disconnectBitalino(event); //We disconnect bitalino if we go back
        }
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getResource("NewMedicalHistoryView.fxml"));

        Parent newmedicalHistoryViewParent = loader.load();
        Scene NewMedicalHistoryViewScene = new Scene(newmedicalHistoryViewParent);

        NewMedicalHistoryController controller = loader.getController();
        controller.initData(patientMoved, com_data_client);

        //this line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(NewMedicalHistoryViewScene);
        window.centerOnScreen();

        window.show();
    }

    //Similar to go back, but here we return the ecg. We could reuse the previous method, but done for clarity for the user
    @FXML
    void saveECG(ActionEvent event) throws IOException {

        if (ecg_data != null) {
            if (bitalinoManager != null && bitalinoManager.isConnected()) {
                disconnectBitalino(event); //We disconnect bitalino if we go back
            }
            FXMLLoader loader = new FXMLLoader();

            loader.setLocation(getClass().getResource("NewMedicalHistoryView.fxml"));

            Parent newmedicalHistoryViewParent = loader.load();
            Scene NewMedicalHistoryViewScene = new Scene(newmedicalHistoryViewParent);

            NewMedicalHistoryController controller = loader.getController();
            controller.initDataECG(patientMoved, com_data_client, ecg_data);

            //this line gets the Stage information
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(NewMedicalHistoryViewScene);
            window.centerOnScreen();

            window.show();
        } else {
            messageLabel.setTextFill(Color.RED);
            messageLabel.setText("No ECG recorded");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

}
