package wolff_patient;

import POJOS.Clinical_record;
import POJOS.Patient;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class MedicalHistoryController implements Initializable {
    private Patient patientMoved;

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
    
    @FXML
    private Label nameLabel;
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
    }

    public void loadClinical_records() {
        list = FXCollections.observableArrayList();

        Clinical_record record1 = new Clinical_record(0, new Date(), false, true, true, true, false, true, true, true, true, true, true, null, "hello I'm 1");
        Clinical_record record2 = new Clinical_record(1, new Date(), true, false, true, false, true, true, true, true, false, true, true, null, " I'm 2");
        Clinical_record record3 = new Clinical_record(2, new Date(), true, true, false, true, true, true, false, true, true, true, true, null, "hello3");
        ArrayList<Clinical_record> records = new ArrayList<>();

        records.add(record1);
        records.add(record2);
        records.add(record3);
        patientMoved.setClinical_record_list(records);
        
        ArrayList<Clinical_record> recordsP = patientMoved.getClinical_record_list();
        list.addAll(recordsP);
        table.setItems(list);

    }
    /**
     * Thid method gets the patient got from the login to show the data.
     * @param patient
     */
    public void initData(Patient patient) {
        
        this.patientMoved = patient;  
        nameLabel.setText("Patient's name:\n " + patientMoved.getName());
        loadClinical_records();


    }
    public ObservableList<Clinical_record> getList() {
        return list;
    }
    public void backToMenu(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("PatientMenuView.fxml"));
        Parent patientMenuViewParent = loader.load();
        Scene MainMenuViewScene = new Scene(patientMenuViewParent);
        PatientMenuController controller = loader.getController();
        controller.initData(patientMoved);
        //this line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(MainMenuViewScene);
        window.centerOnScreen();

        window.show();
    }

}
