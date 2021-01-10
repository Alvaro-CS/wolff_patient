/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wolff_patient;

import POJOS.Com_data_client;
import POJOS.Patient;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 *
 * @author ALVARO
 */
public class ClientThreadsServer implements Runnable {

    private Com_data_client com_data_client;
    private Patient patient; //Patient that is going to be got from the server when login
    

    /**
     * Empty (default) constructor.
     */
    public ClientThreadsServer() {
    }

    @Override
    public synchronized void run() {
        try {

            ObjectInputStream objectInputStream = com_data_client.getObjectInputStream();
            Object tmp;
            System.out.println("Before order");
            //Instruction received
            String instruction;
            tmp = objectInputStream.readObject();//we receive the instruction
            instruction = (String) tmp;

            System.out.println("Order received");
            switch (instruction) {//Although we have only 1 option, designed for programming more options if needed.
                case "RECEIVE_PATIENT": { //For login
                    System.out.println(instruction + " option running");

                    tmp = objectInputStream.readObject();//we receive the patient
                    patient = (Patient) tmp;
                    if (patient != null) {
                        System.out.println("Patient received: " + patient.getDNI());
                    } else {
                        System.out.println("Null patient: " + patient);
                    }
                    notify(); //we awake thread to get data
                    break;
                }
                default: {
                    System.out.println("Error");
                    break;
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Client closed");
        }
    }

    public Patient getPatient() {
        return patient;
    }

    public void setCom_data_client(Com_data_client com_data_client) {
        this.com_data_client = com_data_client;
    }

}
