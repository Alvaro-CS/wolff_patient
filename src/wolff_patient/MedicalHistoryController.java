package wolff_patient;

import POJOS.Clinical_record;
import POJOS.Com_data_client;
import POJOS.Patient;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
    
    private Com_data_client com_data_client;
    private Patient patientMoved;

    @FXML
    private TableView<Clinical_record> table;

    @FXML
    private TableColumn<Clinical_record, Integer> idColumn;

    @FXML
    private TableColumn<Clinical_record, String> dateColumn;

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
    private TableColumn<Clinical_record, Integer[]> ecgColumn;

    @FXML
    private TableColumn<Clinical_record, String> extra_infoColumn;

    private ObservableList<Clinical_record> list;

    @FXML
    private Label nameLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Here we connect the columns with the atributes
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateString"));
        palpitationsColumn.setCellValueFactory(new PropertyValueFactory<>("palpitations"));
        dizzinessColumn.setCellValueFactory(new PropertyValueFactory<>("dizziness"));
        fatigueColumn.setCellValueFactory(new PropertyValueFactory<>("fatigue"));
        anxietyColumn.setCellValueFactory(new PropertyValueFactory<>("anxiety"));
        chest_painColumn.setCellValueFactory(new PropertyValueFactory<>("chest_pain"));
        difficulty_breathingColumn.setCellValueFactory(new PropertyValueFactory<>("difficulty_breathing"));
        faintingColumn.setCellValueFactory(new PropertyValueFactory<>("fainting"));
        ecgColumn.setCellValueFactory(new PropertyValueFactory<>("ecg"));
        extra_infoColumn.setCellValueFactory(new PropertyValueFactory<>("extra_info"));
    }

    public void loadClinical_records() {
        list = FXCollections.observableArrayList();

        ArrayList<Clinical_record> recordsP = patientMoved.getClinical_record_list();
        list.addAll(recordsP);
        table.setItems(list);

    }

    /**
     * Thid method gets the patient got from the login to show the data.
     *
     * @param patient
     */
    public void initData(Patient patient,Com_data_client com_data_client) {
        this.com_data_client=com_data_client;
        this.patientMoved = patient;
        nameLabel.setText("Patient's name:\n " + patientMoved.getName());
        loadClinical_records();

    }

    public ObservableList<Clinical_record> getList() {
        return list;
    }
    
    /**
     * This method takes the user back to the main menu
     *
     * @param event
     * @throws IOException
     */
    @FXML
    public void backToMenu(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("PatientMenuView.fxml"));
        Parent patientMenuViewParent = loader.load();
        Scene MainMenuViewScene = new Scene(patientMenuViewParent);
        PatientMenuController controller = loader.getController();
        controller.initData(patientMoved,com_data_client);
        //this line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(MainMenuViewScene);
        window.centerOnScreen();

        window.show();
    }

    /**
     * This method loads the scene which lets the user add a new clinical record
     * to its profile.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    public void addRecord(ActionEvent event) throws IOException {
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

}
