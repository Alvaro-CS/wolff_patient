/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wolff_patient;

import java.util.Date;
import java.io.Serializable;

/**
 *
 * @author ALVARO
 */
public class Patient implements Runnable, Serializable{
    
private String DNI;
private String password;
private String name;
private String lastName;

//private Date birthdate;
//private enum gender{MALE,FEMALE,OTHER};
//private String address;//ARRAY PENSAR
//private int SSNumber;
//private int telf;

    public Patient(String DNI, String password, String name, String lastName) {
        this.DNI = DNI;
        this.password = password;
        this.name = name;
        this.lastName = lastName;
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

    @Override
    public String toString() {
        return "Patient{" + "DNI=" + DNI + ", password=" + password + ", name=" + name + ", lastName=" + lastName + '}';
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
