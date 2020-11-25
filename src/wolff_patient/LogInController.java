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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class LogInController implements Initializable {

    BitalinoManager bitalinoManager = null;//Remove after clean
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
    public void connectBitalino(ActionEvent event) {
        bitalinoManager = new BitalinoManager("98:D3:C1:FD:2F:EC"); //El user tiene que meterlo
    }
     @FXML
    public void disconnectBitalino(ActionEvent event) {
        bitalinoManager.disconnect();
    }
    @FXML

    public void readECG(ActionEvent event) {
        bitalinoManager.startManualECG();
        showECG(event);
    }
    @FXML
    Pane paneChart;
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
