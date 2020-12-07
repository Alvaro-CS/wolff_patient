
package wolff_patient;

import POJOS.Clinical_record;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


public class MedicalHistoryController implements Initializable {

    @FXML
    private TableView<Clinical_record> table;

    @FXML
    private TableColumn<Clinical_record, Integer> id;

    @FXML
    private TableColumn<Clinical_record, Date> date;

    @FXML
    private TableColumn<Clinical_record, Boolean> palpitations;

    @FXML
    private TableColumn<Clinical_record, Boolean> dizziness;

    @FXML
    private TableColumn<Clinical_record, Boolean> fatigue;

    @FXML
    private TableColumn<Clinical_record, Boolean> anxiety;

    @FXML
    private TableColumn<Clinical_record, Boolean> chest_pain;

    @FXML
    private TableColumn<Clinical_record, Boolean> diff_breathing;

    @FXML
    private TableColumn<Clinical_record, Boolean> fainting;

    @FXML
    private TableColumn<Clinical_record, Boolean> gray_blue_skin;

    @FXML
    private TableColumn<Clinical_record, Boolean> irritability;

    @FXML
    private TableColumn<Clinical_record, Boolean> rapid_breathing;

    @FXML
    private TableColumn<Clinical_record, Boolean> poor_eating;

    @FXML
    private TableColumn<Clinical_record, Integer> ecg;

    @FXML
    private TableColumn<Clinical_record, String> extra_info;
    
    
ObservableList<Clinical_record> list = FXCollections.observableArrayList(
        new Clinical_record(0, new Date(), true, true, true, true, true, true, true, true, true, true, true, new int[3], "hello"),
        new Clinical_record(1, new Date(), true, true, true, true, true, true, true, true, true, true, true,new int[2], "hello"),
        new Clinical_record(2, new Date(), true, true, true, true, true, true, true, true, true, true, true, new int[3], "hello")
);
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        id.setCellValueFactory(new PropertyValueFactory<Clinical_record, Integer> ("id"));
        date.setCellValueFactory(new PropertyValueFactory<Clinical_record, Date> ("date"));
        palpitations.setCellValueFactory(new PropertyValueFactory<Clinical_record, Boolean> ("palpitations"));
        dizziness.setCellValueFactory(new PropertyValueFactory<Clinical_record, Boolean> ("dizziness"));
        fatigue.setCellValueFactory(new PropertyValueFactory<Clinical_record, Boolean> ("fatigue"));
        anxiety.setCellValueFactory(new PropertyValueFactory<Clinical_record, Boolean> ("anxiety"));
        chest_pain.setCellValueFactory(new PropertyValueFactory<Clinical_record, Boolean> ("chest_pain"));
        diff_breathing.setCellValueFactory(new PropertyValueFactory<Clinical_record, Boolean> ("diff_breathing"));
        fainting.setCellValueFactory(new PropertyValueFactory<Clinical_record, Boolean> ("fainting"));
        gray_blue_skin.setCellValueFactory(new PropertyValueFactory<Clinical_record, Boolean> ("gray_blue_skin"));
        irritability.setCellValueFactory(new PropertyValueFactory<Clinical_record, Boolean> ("irritability"));
        rapid_breathing.setCellValueFactory(new PropertyValueFactory<Clinical_record, Boolean> ("rapid_breathing"));
        poor_eating.setCellValueFactory(new PropertyValueFactory<Clinical_record, Boolean> ("poor_eating"));
        ecg.setCellValueFactory(new PropertyValueFactory<Clinical_record, Integer> ("ecg"));
        extra_info.setCellValueFactory(new PropertyValueFactory<Clinical_record, String> ("extra_info"));

        table.setItems(list);
    }
   

}

