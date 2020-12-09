/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wolff_patient;

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

    ServerSocket serverSocket;
    private Patient patient; //Patient that is going to be got from the server when login
    //private boolean patient_logged;
    /**
     * Empty (default) constructor.
     */
    public ClientThreadsServer() {
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(9001); //TODO dynamic ports?
            try {
                System.out.println("Before accepting");
                Socket socket = serverSocket.accept();
                System.out.println("Client connected");
                InputStream inputStream;
                ObjectInputStream objectInputStream = null;

                try {
                    inputStream = socket.getInputStream();
                    objectInputStream = new ObjectInputStream(inputStream);
                    Object tmp;
                    System.out.println("Before order");
                    //Instruction received
                    String instruction;
                    tmp = objectInputStream.readObject();//we receive the instruction
                    instruction = (String) tmp;

                    System.out.println("Order received");
                    switch (instruction) {
                        case "RECEIVE_PATIENT": {
                            System.out.println(instruction + " option running");

                            tmp = objectInputStream.readObject();//we receive the patient
                            patient = (Patient) tmp;

                            System.out.println("Patient received: " + patient.getDNI());
                          //  patient_logged=true;
                            //TODO do something with the patient, take it.
                            break;
                        }
                        default: {
                            System.out.println("Error");
                            break;
                        }
                    }

                } catch (IOException | ClassNotFoundException e) {
                    System.out.println("Client closed");
                } finally {
                    releaseResourcesClient(objectInputStream, socket);
                    
                }

            } catch (IOException ex) {
                Logger.getLogger(ClientThreadsServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex) {
            Logger.getLogger(ClientThreadsServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

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
    }

    public Patient getPatient() {
        return patient;
    }

   /*public boolean isPatient_logged() {
        return patient_logged;
    }*/
    
}
