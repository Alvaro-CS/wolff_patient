/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package POJOS;

import java.util.Date;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author ALVARO
 */
public class Patient implements Runnable, Serializable {

    private static final long serialVersionUID = 1L;

    private String DNI;
    private String password;
    private String name;
    private String lastName;
    private ArrayList<Clinical_record> clinical_record_list;

//private Date birthdate;
//private enum gender{MALE,FEMALE,OTHER};
//private String address;//ARRAY PENSAR
//private int SSNumber;
//private int telf;
//private enum blood_group
    public Patient(String DNI, String password, String name, String lastName) {
        this.DNI = DNI;
        this.password = password;
        this.name = name;
        this.lastName = lastName;
        clinical_record_list= new ArrayList<>();
    }

    //getters
    public String getDNI() {
        return DNI;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public ArrayList<Clinical_record> getClinical_record_list() {
        return clinical_record_list;
    }

    public void setClinical_record_list(ArrayList<Clinical_record> clinical_record_list) {
        this.clinical_record_list = clinical_record_list;
    }

    @Override
    public String toString() {
        return "Patient{" + "DNI=" + DNI + ", password=" + password + ", name=" + name + ", lastName=" + lastName + '}';
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
