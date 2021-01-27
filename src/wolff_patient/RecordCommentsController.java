package wolff_patient;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;


public class RecordCommentsController implements Initializable {

    @FXML
    Label commentsLabel;
    String comments;
    
    /**
     * This method gets the comments to show them.
     *
     * @param comments
     */
    public void initData(String comments) {
        this.comments=comments;
        commentsLabel.setText(comments);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

}
