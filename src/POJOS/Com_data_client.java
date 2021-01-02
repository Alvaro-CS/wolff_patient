/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package POJOS;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 *
 * @author ALVARO
 */
public class Com_data_client {//If we want to encapsulate all data
    
    private String ip_address;
    private String bitalino_adress;
    private boolean socket_created;
    private Socket socket;
    
    private OutputStream outputStream;
    private ObjectOutputStream objectOutputStream;
    private InputStream inputStream;
    private ObjectInputStream objectInputStream;

    public Com_data_client() {
        this.ip_address="localhost";
        this.socket_created = false;
        this.socket = null;
        this.outputStream = null;
        this.objectOutputStream = null;
        this.inputStream = null;
        this.objectInputStream = null;
        this.bitalino_adress="98:D3:C1:FD:2F:EC";
    }

    public String getIp_address() {
        return ip_address;
    }

    public boolean isSocket_created() {
        return socket_created;
    }

    public Socket getSocket() {
        return socket;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public ObjectOutputStream getObjectOutputStream() {
        return objectOutputStream;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public ObjectInputStream getObjectInputStream() {
        return objectInputStream;
    }

    public void setIp_address(String ip_address) {
        this.ip_address = ip_address;
    }

    public void setSocket_created(boolean socket_created) {
        this.socket_created = socket_created;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void setObjectOutputStream(ObjectOutputStream objectOutputStream) {
        this.objectOutputStream = objectOutputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public void setObjectInputStream(ObjectInputStream objectInputStream) {
        this.objectInputStream = objectInputStream;
    }

    public String getBitalino_adress() {
        return bitalino_adress;
    }

    public void setBitalino_adress(String bitalino_adress) {
        this.bitalino_adress = bitalino_adress;
    }
    
    
    
}
