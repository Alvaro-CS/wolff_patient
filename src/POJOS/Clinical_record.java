/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package POJOS;

import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Clinical_record {

    private IntegerProperty id;

    private Date date;
    private final SimpleDateFormat formatter;
    private final StringProperty dateString; //For UI showing purposes

    private BooleanProperty palpitations;
    private BooleanProperty dizziness;
    private BooleanProperty fatigue;
    private BooleanProperty anxiety;
    private BooleanProperty chest_pain;
    private BooleanProperty difficulty_breathing;
    private BooleanProperty fainting;

    private Integer[] ECG;
    private StringProperty extra_info;

    public Clinical_record(Integer id, Boolean palpitations, Boolean dizziness, Boolean fatigue,
             Boolean anxiety, Boolean chest_pain, Boolean difficulty_breathing,
            Boolean fainting, Integer[] ECG,
            String extra_info) {
        this.id = new SimpleIntegerProperty(id);
        this.date = new Date();
        formatter = new SimpleDateFormat("dd-MM-yyyy\nHH:mm:ss");
        this.dateString = new SimpleStringProperty(formatter.format(date));
        this.palpitations = new SimpleBooleanProperty(palpitations);
        this.dizziness = new SimpleBooleanProperty(dizziness);
        this.fatigue = new SimpleBooleanProperty(fatigue);
        this.anxiety = new SimpleBooleanProperty(anxiety);
        this.chest_pain = new SimpleBooleanProperty(chest_pain);
        this.difficulty_breathing = new SimpleBooleanProperty(difficulty_breathing);
        this.fainting = new SimpleBooleanProperty(fainting);
        this.ECG = ECG;
        this.extra_info = new SimpleStringProperty(extra_info);
    }

    public IntegerProperty IdProperty() {
        return id;
    }

    public Integer getId() {
        return id.get();
    }

    public StringProperty DateStringProperty() {
        return dateString;
    }

    public String getDateString() {
        return dateString.get();
    }

    public Date getDate() {
        return date;
    }

    public BooleanProperty PalpitationsProperty() {
        return palpitations;
    }

    public Boolean getPalpitations() {
        return palpitations.get();
    }

    public BooleanProperty DizzinessProperty() {
        return dizziness;
    }

    public Boolean getDizziness() {
        return dizziness.get();
    }

    public BooleanProperty FatigueProperty() {
        return fatigue;
    }

    public Boolean getFatigue() {
        return fatigue.get();
    }

    public BooleanProperty AnxietyProperty() {
        return anxiety;
    }

    public Boolean getAnxiety() {
        return anxiety.get();
    }

    public BooleanProperty Chest_painProperty() {
        return chest_pain;
    }

    public Boolean getChest_pain() {
        return chest_pain.get();
    }

    public BooleanProperty Difficulty_breathingProperty() {
        return difficulty_breathing;
    }

    public Boolean getDifficulty_breathing() {
        return difficulty_breathing.get();
    }

    public BooleanProperty FaintingProperty() {
        return fainting;
    }

    public Boolean getFainting() {
        return fainting.get();
    }

    //Not shown in table
    public Integer[] getECG() {
        return ECG;
    }

    public StringProperty Extra_infoProperty() {
        return extra_info;
    }

    public String getExtra_info() {
        return extra_info.get();
    }

//CHANGE SETS IF NEEDED, WITH APPROPIATE CLASS
    public void setId(IntegerProperty id) {
        this.id = id;
    }

    public void setDate(Object date) {
        this.date = this.date;
    }

    public void setPalpitations(BooleanProperty palpitations) {
        this.palpitations = palpitations;
    }

    public void setDizziness(BooleanProperty dizziness) {
        this.dizziness = dizziness;
    }

    public void setFatigue(BooleanProperty fatigue) {
        this.fatigue = fatigue;
    }

    public void setAnxiety(BooleanProperty anxiety) {
        this.anxiety = anxiety;
    }

    public void setChest_pain(BooleanProperty chest_pain) {
        this.chest_pain = chest_pain;
    }

    public void setDifficulty_breathing(BooleanProperty difficulty_breathing) {
        this.difficulty_breathing = difficulty_breathing;
    }

    public void setFainting(BooleanProperty fainting) {
        this.fainting = fainting;
    }

    public void setECG(Integer[] ECG) {
        this.ECG = ECG;
    }

    public void setExtra_info(StringProperty extra_info) {
        this.extra_info = extra_info;
    }

}
