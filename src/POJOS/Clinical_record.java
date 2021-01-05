/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package POJOS;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Clinical_record implements Serializable {


    //For showing in table
    private transient IntegerProperty id_prop;
    private transient BooleanProperty palpitations_prop;
    private transient BooleanProperty dizziness_prop;
    private transient BooleanProperty fatigue_prop;
    private transient BooleanProperty anxiety_prop;
    private transient BooleanProperty chest_pain_prop;
    private transient BooleanProperty difficulty_breathing_prop;
    private transient BooleanProperty fainting_prop;
    private transient StringProperty extra_info_prop;
    private transient StringProperty dateString_prop; 

    private final SimpleDateFormat formatter= new SimpleDateFormat("dd-MM-yyyy\nHH:mm:ss");
    private Integer id;
    private final String dateString;
    private Boolean palpitations;
    private Boolean dizziness;
    private Boolean fatigue;
    private Boolean anxiety;
    private Boolean chest_pain;
    private Boolean difficulty_breathing;
    private Boolean fainting;
    private String extra_info;

    private Integer[] ECG;
    private static final long serialVersionUID = 2L;

    public Clinical_record(Integer id, Boolean palpitations, Boolean dizziness, Boolean fatigue,
            Boolean anxiety, Boolean chest_pain, Boolean difficulty_breathing,
            Boolean fainting, Integer[] ECG,
            String extra_info) {
        this.id = id;
        Date date = new Date();
        dateString=formatter.format(date);
        this.palpitations = palpitations;
        this.dizziness = dizziness;
        this.fatigue = fatigue;
        this.anxiety = anxiety;
        this.chest_pain = chest_pain;
        this.difficulty_breathing = difficulty_breathing;
        this.fainting = fainting;
        this.ECG = ECG;
        this.extra_info = extra_info;
        setProperties();
    }

    private void setProperties() {
        this.id_prop = new SimpleIntegerProperty(id);
        this.palpitations_prop = new SimpleBooleanProperty(palpitations);
        this.dizziness_prop = new SimpleBooleanProperty(dizziness);
        this.fatigue_prop = new SimpleBooleanProperty(fatigue);
        this.anxiety_prop = new SimpleBooleanProperty(anxiety);
        this.chest_pain_prop = new SimpleBooleanProperty(chest_pain);
        this.difficulty_breathing_prop = new SimpleBooleanProperty(difficulty_breathing);
        this.fainting_prop = new SimpleBooleanProperty(fainting);
        this.extra_info_prop = new SimpleStringProperty(extra_info);
        this.dateString_prop = new SimpleStringProperty(dateString);
    }

    public IntegerProperty IdProperty() {
        return id_prop;
    }

    public StringProperty DateStringProperty() {
        return dateString_prop;
    }    public BooleanProperty PalpitationsProperty() {
        return palpitations_prop;
    }    public BooleanProperty DizzinessProperty() {
        return dizziness_prop;
    }    public BooleanProperty FatigueProperty() {
        return fatigue_prop;
    }    public BooleanProperty AnxietyProperty() {
        return anxiety_prop;
    }  public BooleanProperty Chest_painProperty() {
        return chest_pain_prop;
    }   public BooleanProperty Difficulty_breathingProperty() {
        return difficulty_breathing_prop;
    }    public BooleanProperty FaintingProperty() {
        return fainting_prop;
    }    public StringProperty Extra_infoProperty() {
        return extra_info_prop;
    }
    public BooleanProperty Palpitations_prop() {
        return palpitations_prop;
    }

    public BooleanProperty getDizziness_prop() {
        return dizziness_prop;
    }

    public BooleanProperty getFatigue_prop() {
        return fatigue_prop;
    }

    public BooleanProperty getChest_pain_prop() {
        return chest_pain_prop;
    }

    public BooleanProperty getDifficulty_breathing_prop() {
        return difficulty_breathing_prop;
    }

    public BooleanProperty getFainting_prop() {
        return fainting_prop;
    }

//Setters
    public void setId_prop(IntegerProperty id_prop) {
        this.id_prop = id_prop;
    }

    public void setPalpitations_prop(BooleanProperty palpitations_prop) {
        this.palpitations_prop = palpitations_prop;
    }

    public void setDizziness_prop(BooleanProperty dizziness_prop) {
        this.dizziness_prop = dizziness_prop;
    }

    public void setFatigue_prop(BooleanProperty fatigue_prop) {
        this.fatigue_prop = fatigue_prop;
    }

    public void setAnxiety_prop(BooleanProperty anxiety_prop) {
        this.anxiety_prop = anxiety_prop;
    }

    public void setChest_pain_prop(BooleanProperty chest_pain_prop) {
        this.chest_pain_prop = chest_pain_prop;
    }

    public void setDifficulty_breathing_prop(BooleanProperty difficulty_breathing_prop) {
        this.difficulty_breathing_prop = difficulty_breathing_prop;
    }

    public void setFainting_prop(BooleanProperty fainting_prop) {
        this.fainting_prop = fainting_prop;
    }

    public void setExtra_info_prop(StringProperty extra_info_prop) {
        this.extra_info_prop = extra_info_prop;
    }

    public Integer getId() {
        return id;
    }


    public String getDateString() {
        return dateString;
   }




    public Boolean getPalpitations() {
        return palpitations;
    }



    public Boolean getDizziness() {
        return dizziness;
    }



    public Boolean getFatigue() {
        return fatigue;
    }



    public Boolean getAnxiety() {
        return anxiety;
    }

  

    public Boolean getChest_pain() {
        return chest_pain;
    }

 

    public Boolean getDifficulty_breathing() {
        return difficulty_breathing;
    }



    public Boolean getFainting() {
        return fainting;
    }



    
    //Not shown in table
    public Integer[] getECG() {
        return ECG;
    }



    public String getExtra_info() {
        return extra_info;
    }

    public void setECG(Integer[] ECG) {
        this.ECG = ECG;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPalpitations(Boolean palpitations) {
        this.palpitations = palpitations;
    }

    public void setDizziness(Boolean dizziness) {
        this.dizziness = dizziness;
    }

    public void setFatigue(Boolean fatigue) {
        this.fatigue = fatigue;
    }

    public void setAnxiety(Boolean anxiety) {
        this.anxiety = anxiety;
    }

    public void setChest_pain(Boolean chest_pain) {
        this.chest_pain = chest_pain;
    }

    public void setDifficulty_breathing(Boolean difficulty_breathing) {
        this.difficulty_breathing = difficulty_breathing;
    }

    public void setFainting(Boolean fainting) {
        this.fainting = fainting;
    }

    public void setExtra_info(String extra_info) {
        this.extra_info = extra_info;
    }
}
