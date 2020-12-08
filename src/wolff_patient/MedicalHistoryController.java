package wolff_patient;

import POJOS.Clinical_record;
import POJOS.Patient;
import java.net.URL;
import java.util.ArrayList;
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
    private TableColumn<Clinical_record, Integer> idColumn;

    @FXML
    private TableColumn<Clinical_record, Date> dateColumn;

    @FXML
    private TableColumn<Clinical_record, Boolean> palpitationsColumn;

    @FXML
    private TableColumn<Clinical_record, Boolean> dizzinessColumn;

    @FXML
    private TableColumn<Clinical_record, Boolean> fatigueColumn;

    @FXML
    private TableColumn<Clinical_record, Boolean> anxietyColumn;

    @FXML
    private TableColumn<Clinical_record, Boolean> chest_painColumn;

    @FXML
    private TableColumn<Clinical_record, Boolean> difficulty_breathingColumn;

    @FXML
    private TableColumn<Clinical_record, Boolean> faintingColumn;

    @FXML
    private TableColumn<Clinical_record, Boolean> gray_blue_skinColumn;

    @FXML
    private TableColumn<Clinical_record, Boolean> irritabilityColumn;

    @FXML
    private TableColumn<Clinical_record, Boolean> rapid_breathingColumn;

    @FXML
    private TableColumn<Clinical_record, Boolean> poor_eatingColumn;

    @FXML
    private TableColumn<Clinical_record, Integer[]> ecgColumn;

    @FXML
    private TableColumn<Clinical_record, String> extra_infoColumn;

    private ObservableList<Clinical_record> list;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        palpitationsColumn.setCellValueFactory(new PropertyValueFactory<>("palpitations"));
        dizzinessColumn.setCellValueFactory(new PropertyValueFactory<>("dizziness"));
        fatigueColumn.setCellValueFactory(new PropertyValueFactory<>("fatigue"));
        anxietyColumn.setCellValueFactory(new PropertyValueFactory<>("anxiety"));
        chest_painColumn.setCellValueFactory(new PropertyValueFactory<>("chest_pain"));
        difficulty_breathingColumn.setCellValueFactory(new PropertyValueFactory<>("difficulty_breathing"));
        faintingColumn.setCellValueFactory(new PropertyValueFactory<>("fainting"));
        gray_blue_skinColumn.setCellValueFactory(new PropertyValueFactory<>("gray_blue_skin"));
        irritabilityColumn.setCellValueFactory(new PropertyValueFactory<>("irritability"));
        rapid_breathingColumn.setCellValueFactory(new PropertyValueFactory<>("rapid_breathing"));
        poor_eatingColumn.setCellValueFactory(new PropertyValueFactory<>("poor_eating"));
        ecgColumn.setCellValueFactory(new PropertyValueFactory<>("ecg"));
        extra_infoColumn.setCellValueFactory(new PropertyValueFactory<>("extra_info"));
        loadClinical_records();
    }

    public void loadClinical_records() {
        list = FXCollections.observableArrayList();
        //TODO load data with real patient form login
        Clinical_record record1 = new Clinical_record(0, new Date(), true, true, true, true, true, true, true, true, true, true, true, null, "hello");
        Clinical_record record2 = new Clinical_record(1, new Date(), true, true, true, true, true, true, true, true, true, true, true, null, "hello");
        Clinical_record record3 = new Clinical_record(2, new Date(), true, true, true, true, true, true, true, true, true, true, true, null, "hello");
        ArrayList<Clinical_record> records = new ArrayList<>();

        records.add(record1);
        records.add(record2);
        records.add(record3);

        Patient p = new Patient("09873782L", "Contraseña", "Pedro", "Alonso");
        p.setClinical_record_list(records);
        //From here, common when Patient works
        
        ArrayList<Clinical_record> recordsP = p.getClinical_record_list();
        list.addAll(recordsP);
        table.setItems(list);

    }

    public ObservableList<Clinical_record> getList() {
        return list;
    }

}
