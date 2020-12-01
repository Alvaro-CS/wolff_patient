/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package POJOS;

import java.util.Date;

/**
 *
 * @author ALVARO
 */
public class Clinical_record {
    private Date date;
    private boolean palpitations;
    private boolean dizziness;
    private boolean fatigue;
    private boolean anxiety;
    private boolean chest_pain;
    private boolean difficulty_breathing;
    private boolean fainting;
    //Infants
    private boolean gray_blue_skin;
    private boolean irritability;
    private boolean rapid_breathing;
    private boolean poor_eating;
    
    private int[] ECG;
    private String extra_info;

    public Clinical_record(Date date, boolean palpitations, boolean dizziness, 
            boolean fatigue, boolean anxiety, boolean chest_pain, boolean difficulty_breathing,
            boolean fainting, boolean gray_blue_skin, boolean irritability, 
            boolean rapid_breathing, boolean poor_eating, int[] ECG, 
            String extra_info) {
        this.date = new Date(System.currentTimeMillis());
        this.palpitations = palpitations;
        this.dizziness = dizziness;
        this.fatigue = fatigue;
        this.anxiety = anxiety;
        this.chest_pain = chest_pain;
        this.difficulty_breathing = difficulty_breathing;
        this.fainting = fainting;
        this.gray_blue_skin = gray_blue_skin;
        this.irritability = irritability;
        this.rapid_breathing = rapid_breathing;
        this.poor_eating = poor_eating;
        this.ECG = ECG;
        this.extra_info = extra_info;
    }
    
    
    
}
