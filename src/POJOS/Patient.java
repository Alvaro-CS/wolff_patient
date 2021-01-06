/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package POJOS;

import java.util.Date;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Patient implements Runnable, Serializable {

    private static final long serialVersionUID = 1L;

    private String DNI;
    private String password;
    private String name;
    private String lastName;
    private String birthdate;

    private String address;
    private int SSNumber;
    private int telf;

    public enum Gender {
        MALE, FEMALE, OTHER
    };
    private Gender gender;
    
    private transient StringProperty DNI_prop;
    private transient StringProperty name_prop;
    private transient StringProperty lastName_prop;
    private transient StringProperty birthdate_prop;
    private transient StringProperty address_prop;
    private transient IntegerProperty SSNumber_prop;
    private transient IntegerProperty telf_prop;
    private transient StringProperty gender_prop;
    
    private ArrayList<Clinical_record> clinical_record_list;

    private final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

    public Patient(String DNI, String password, String name, String lastName) {
        this.DNI = DNI;
        this.password = password;
        this.name = name;
        this.lastName = lastName;
        clinical_record_list = new ArrayList<>();
    }

    public Patient(String DNI, String password, String name, String lastName,
            Gender gender, Date date, String address, int SSNumber, int telf) {
        this.DNI = DNI;
        this.password = password;
        this.name = name;
        this.lastName = lastName;
        this.gender = gender;
        this.birthdate = formatter.format(date);
        this.address = address;
        this.SSNumber = SSNumber;
        this.telf = telf;
        clinical_record_list = new ArrayList<>();
        setProperties();
    }
    
    //For TableView showing purposes
    private void setProperties() {
        this.DNI_prop = new SimpleStringProperty(DNI);
        this.name_prop = new SimpleStringProperty(name);
        this.lastName_prop = new SimpleStringProperty(lastName);
        this.birthdate_prop = new SimpleStringProperty(birthdate);
        this.address_prop = new SimpleStringProperty(address);
        this.gender_prop = new SimpleStringProperty(gender.toString());
        this.SSNumber_prop= new SimpleIntegerProperty(SSNumber);
        this.telf_prop= new SimpleIntegerProperty(telf);

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

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }



    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getSSNumber() {
        return SSNumber;
    }

    public void setSSNumber(int SSNumber) {
        this.SSNumber = SSNumber;
    }

    public int getTelf() {
        return telf;
    }

    public void setTelf(int telf) {
        this.telf = telf;
    }

    public StringProperty getDNI_prop() {
        return DNI_prop;
    }

    public void setDNI_prop(StringProperty DNI_prop) {
        this.DNI_prop = DNI_prop;
    }

    public StringProperty getName_prop() {
        return name_prop;
    }

    public void setName_prop(StringProperty name_prop) {
        this.name_prop = name_prop;
    }

    public StringProperty getLastName_prop() {
        return lastName_prop;
    }

    public void setLastName_prop(StringProperty lastName_prop) {
        this.lastName_prop = lastName_prop;
    }

    public StringProperty getBirthdate_prop() {
        return birthdate_prop;
    }

    public void setBirthdate_prop(StringProperty birthdate_prop) {
        this.birthdate_prop = birthdate_prop;
    }

    public StringProperty getAddress_prop() {
        return address_prop;
    }

    public void setAddress_prop(StringProperty address_prop) {
        this.address_prop = address_prop;
    }

    public IntegerProperty getSSNumber_prop() {
        return SSNumber_prop;
    }

    public void setSSNumber_prop(IntegerProperty SSNumber_prop) {
        this.SSNumber_prop = SSNumber_prop;
    }

    public IntegerProperty getTelf_prop() {
        return telf_prop;
    }

    public void setTelf_prop(IntegerProperty telf_prop) {
        this.telf_prop = telf_prop;
    }

    public StringProperty getGender_prop() {
        return gender_prop;
    }

    public void setGender_prop(StringProperty gender_prop) {
        this.gender_prop = gender_prop;
    }


    @Override
    public String toString() {
        return "Patient{" + "DNI=" + DNI + ", password=" + password + ", name=" + name + ", lastName=" + lastName + ", birthdate=" + birthdate + ", gender=" + gender + ", address=" + address + ", SSNumber=" + SSNumber + ", telf=" + telf + ", nº records="+clinical_record_list.size()+"}";
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
