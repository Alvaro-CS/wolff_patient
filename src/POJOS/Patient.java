/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package POJOS;

import java.util.Date;
import java.io.Serializable;
import java.util.ArrayList;

public class Patient implements Runnable, Serializable {

    private static final long serialVersionUID = 1L;

    private String DNI;
    private String password;
    private String name;
    private String lastName;
    private ArrayList<Clinical_record> clinical_record_list;

    private Date birthdate;

    public enum Gender {
        MALE, FEMALE, OTHER
    };
    private Gender gender;
    private String address;//ARRAY PENSAR
    private int SSNumber;
    private int telf;

    public Patient(String DNI, String password, String name, String lastName) {
        this.DNI = DNI;
        this.password = password;
        this.name = name;
        this.lastName = lastName;
        clinical_record_list = new ArrayList<>();
    }

    public Patient(String DNI, String password, String name, String lastName,
            Gender gender, Date birthdate, String address, int SSNumber, int telf) {
        this.DNI = DNI;
        this.password = password;
        this.name = name;
        this.lastName = lastName;
        this.gender = gender;
        this.birthdate = birthdate;
        this.address = address;
        this.SSNumber = SSNumber;
        this.telf = telf;
        clinical_record_list = new ArrayList<>();

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

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
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

//    @Override
//    public String toString() {
//        return "Patient{" + "DNI=" + DNI + ", password=" + password + ", name=" + name + ", lastName=" + lastName + '}';
//    }

    @Override
    public String toString() {
        return "Patient{" + "DNI=" + DNI + ", password=" + password + ", name=" + name + ", lastName=" + lastName + ", birthdate=" + birthdate + ", gender=" + gender + ", address=" + address + ", SSNumber=" + SSNumber + ", telf=" + telf + '}';
    }
    

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
