/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wolff_patient;

import POJOS.Com_data_client;
import POJOS.Patient;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ALVARO
 */
public class ClientThreadsServer implements Runnable {

    private Com_data_client com_data_client;
    private Patient patient; //Patient that is going to be got from the server when login
    //private boolean patient_logged;

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
            switch (instruction) {
                case "RECEIVE_PATIENT": { //For login
                    System.out.println(instruction + " option running");

                    tmp = objectInputStream.readObject();//we receive the patient
                    patient = (Patient) tmp;
                    System.out.println("Patient received: " + patient.getDNI());
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

/*
    private static void releaseResourcesClient(ObjectInputStream objectInputStream, Socket socket) {
        try {
            objectInputStream.close();
        } catch (IOException ex) {
            Logger.getLogger(ClientThreadsServer.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ClientThreadsServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/

public Patient getPatient() {
        return patient;
    }

   /*public boolean isPatient_logged() {
        return patient_logged;
    }*/

    public void setCom_data_client(Com_data_client com_data_client) {
        this.com_data_client = com_data_client;
    }
    
}
