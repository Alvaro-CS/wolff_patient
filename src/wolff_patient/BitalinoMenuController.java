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
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class BitalinoMenuController implements Initializable {

    BitalinoManager bitalinoManager = null;//Remove after clean

    /**
     *
     * @param event
     */
    @FXML
    public void connectBitalino(ActionEvent event) {
        bitalinoManager = new BitalinoManager("98:D3:C1:FD:2F:EC"); //El user tiene que meterlo
    }

    /**
     *
     * @param event
     */
    @FXML
    public void disconnectBitalino(ActionEvent event) {
        bitalinoManager.disconnect();
    }

    /**
     *
     * @param event
     */
    @FXML

    public void readECG(ActionEvent event) {
        bitalinoManager.startManualECG();
        showECG(event);
    }
    @FXML
    Pane paneChart;

    /**
     * This method shows a line chart on-screen  with the ECG that has been 
     * received. It adjusts max and min values of the chart. Line chart is shown 
     * in a pane.
     * @param event The event that triggers the ECG to appear
     * 
     */
    @FXML
    
    public void showECG(ActionEvent event){
        XYChart.Series series = new XYChart.Series();
      //  series.setName("ECG data");
        //populating the series with data
        Frame[] frame=bitalinoManager.getFrame();
        int min=Integer.MAX_VALUE;
        int max=0;
        for(int i=0;i<frame.length;i++){
        series.getData().add(new XYChart.Data(i,frame[i].analog[0]));
        if(min>frame[i].analog[0])min=frame[i].analog[0];
        if(max<frame[i].analog[0])max=frame[i].analog[0];
        }
        
        paneChart.getChildren().clear();
        
        final NumberAxis xAxis = new NumberAxis(0,frame.length,1);
        final NumberAxis yAxis = new NumberAxis(min-5,max+5,0.1);//lower, upper, tick
        LineChart<Number,Number> lineChart= new LineChart<>(xAxis,yAxis);
        
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
     *This method creates a socket  for sending an ECG to a server. It gets the
     * ECG data from "bitalinoManager" object, gets the useful information, that
     * is located in analog[0] and writes the int[] object with all ECG values.
     */
    @FXML
    public void sendECG(){
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
            Frame[] frame=bitalinoManager.getFrame();
                int[]ecg_data= new int[frame.length];
             for (int i=0;i<frame.length;i++) {
                 ecg_data[i]=frame[i].analog[0];
             }
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


    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
