/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import wolff_patient.ECGShowController;

/**
 *
 * @author ALVARO
 */
public class ECGplot {
    Integer [] ecg_data;
    
    public ECGplot(Integer[] ecg_data){
    this.ecg_data=ecg_data;
    }
    
     public void openECGWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/wolff_patient/ECGShowView.fxml"));

        Stage stage = new Stage();
        stage.setTitle("Your ECG");
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);

        ECGShowController controller = loader.getController();
        controller.initData(ecg_data);

        stage.show();
    }
}
