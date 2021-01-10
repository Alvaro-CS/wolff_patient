package wolff_patient;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Pane;


public class ECGShowController implements Initializable {

    @FXML
    Pane hBox;
    Integer [] ecg_data;
    
    /**
     * This method gets the ECG to show it
     *
     * @param ecg_data
     */
    public void initData(Integer[] ecg_data) {
        this.ecg_data=ecg_data;
        showECG();
    }
    
    /**
     * This method shows a line chart on-screen with the ECG that has been
     * received. It adjusts max and min values of the chart. Line chart is shown
 in a hBox.
     *
     *
     */
    @FXML
    private void showECG() {
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

        hBox.getChildren().clear();

        final NumberAxis xAxis = new NumberAxis(0, ecg_data.length, 1);
        final NumberAxis yAxis = new NumberAxis(min - 5, max + 5, 0.1);//lower, upper, tick
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);

        lineChart.getXAxis().setLabel("Time (cs)");
        lineChart.getYAxis().setLabel("Amplitude");
        lineChart.setPrefWidth(910);
        
        //creating the chart
        lineChart.setTitle("ECG");
        //Removing the symbols of the line chart
        lineChart.setCreateSymbols(false);
        //defining a series
        lineChart.getData().add(series);
        hBox.getChildren().add(lineChart);
        System.out.println("Shown");
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

}
