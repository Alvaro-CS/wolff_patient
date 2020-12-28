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
import BITalino.Frame;
import POJOS.Com_data_client;
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
import javafx.scene.layout.Pane;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private Label autoLabel;
    @FXML
    private Pane paneChart;

    /**
     * This method gets the patient got from the login to show the data.
     *
     * @param patient
     * @param com_data_client
     */
    public void initData(Patient patient,Com_data_client com_data_client) {
        this.com_data_client=com_data_client;
        this.patientMoved = patient;

    }

    @FXML
    void openManualECGoptions(ActionEvent event) throws IOException {
        if (bitalinoManager != null) {

            FXMLLoader loader = new FXMLLoader();

            loader.setLocation(getClass().getResource("ManualECG.fxml"));

            Parent ManualECGViewParent = loader.load();
            Scene ManualECGViewScene = new Scene(ManualECGViewParent);

            ManualECGController controller = loader.getController();
            controller.initData(patientMoved,bitalinoManager,com_data_client);

            //this line gets the Stage information
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(ManualECGViewScene);
            window.centerOnScreen();

            window.show();
        } else {
            autoLabel.setTextFill(Color.RED);
            autoLabel.setText("You need to connect to a Bitalino device first");
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
                autoLabel.setText("Bitalino connected.");
            } else {
                autoLabel.setText("Bitalino connection failed.");

            }

        } else if (bitalinoManager.isConnected()) {
            autoLabel.setText("Bitalino already connected. Disconnect it to connect a different device.");
        } else {
            bitalinoManager = new BitalinoManager("98:D3:C1:FD:2F:EC"); //El user tiene que meterlo
            autoLabel.setText("Bitalino connected.");
        }
    }

    /**
     *
     * @param event
     */
    public void disconnectBitalino(ActionEvent event) {
        bitalinoManager.disconnect();
        autoLabel.setText("Bitalino disconnected.");

    }

    /**
     *
     * @param event
     */
    public void readECGAuto(ActionEvent event) throws InterruptedException {
        Integer seconds;
        if (bitalinoManager != null) {
            try {
                seconds = Integer.parseInt(secondsField.getText()); //If it is not Integer, will throw Exception.

                if (seconds > 0) {
                    autoLabel.setText("Recording, please don't move...");
                    autoLabel.setTextFill(Color.CADETBLUE);
                    Thread.sleep(500);
                    bitalinoManager.startAutoECG(seconds);
                    autoLabel.setText("ECG recorded!");
                    autoLabel.setTextFill(Color.SEAGREEN);
                    getECG();

                } else {
                    autoLabel.setTextFill(Color.RED);
                    autoLabel.setText("The number of seconds must be bigger \nthan 0");

                }
            } catch (NumberFormatException e) {
                autoLabel.setTextFill(Color.RED);
                autoLabel.setText("You need to enter a NUMBER of seconds");
            }
        } else {
            autoLabel.setTextFill(Color.RED);
            autoLabel.setText("You need to connect to a Bitalino device first");
        }
        showECG(event);
    }

    public void getECG() {
        //We get the data (Frame class) from Bitalino, get the useful info (int) and send it.
        Frame[] frame = bitalinoManager.getFrame();
        ecg_data = new Integer[frame.length];
        for (int i = 0; i < frame.length; i++) {
            ecg_data[i] = frame[i].analog[0];
        }
    }

    /**
     * This method shows a line chart on-screen with the ECG that has been
     * received. It adjusts max and min values of the chart. Line chart is shown
     * in a pane.
     *
     * @param event The event that triggers the ECG to appear
     *
     */
    public void showECG(ActionEvent event) {
        XYChart.Series series = new XYChart.Series();
        //  series.setName("ECG data");
        //populating the series with data
        int min = Integer.MAX_VALUE;
        int max = 0;
        for (int i = 0; i < ecg_data.length; i++) {
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
    public void sendECG() {
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
        controller.initData(patientMoved,com_data_client);

        //this line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(NewMedicalHistoryViewScene);
        window.centerOnScreen();

        window.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
