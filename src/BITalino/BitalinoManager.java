package BITalino;

import java.util.logging.Level;
import java.util.logging.Logger;

public class BitalinoManager {

    private Frame[] frame;
    private Integer[] ecg_data;
    private boolean connected;
    private BITalino bitalino = null;
    private boolean stop;

    //Create an object BitalinoManager, with the MAC Addres specified.
    public BitalinoManager(String macAddress) {
        connected = false;
        try {
            bitalino = new BITalino();

            //Sampling rate, should be 10, 100 or 1000
            int SamplingRate = 100;
            System.out.println("Connecting with " + macAddress);
            bitalino.open(macAddress, SamplingRate); //TODO como manejar si falla esto
            System.out.println("Bitalino connected.");
            int[] channelsToAcquire = {1};
            bitalino.start(channelsToAcquire);
            connected = true;

        } catch (Exception e) {
            Logger.getLogger(BitalinoManager.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("could not connect to the bitalino, please try again");
        } catch (Throwable e) {
            Logger.getLogger(BitalinoManager.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void startManualECG() { //TODO not overwrite
        try {
            stop=false;
            for (int j = 0; j < Integer.MAX_VALUE; j++) { //infinite?
                try {
                    int block_size = 100;
                    frame = bitalino.read(block_size);
                    System.out.println("size block: " + frame.length);

                    //Print the samples
                    for (int i = 0; i < frame.length; i++) {
                        
                        System.out.println((j * block_size + i) + " seq: " + frame[i].seq + " "
                                + frame[i].analog[0] + " "
                        );

                    }
                } catch (BITalinoException ex) {
                    Logger.getLogger(BitalinoManager.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (stop) { //exit from loop
                    break;
                }
            }

            bitalino.stop();
        } catch (BITalinoException ex) {
            Logger.getLogger(BitalinoManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    public void startAutoECG(int seconds) {
        try {
            //Read in total x times
            
                try {
                    //With sampling rate=100 and block_size=100, we record 1 second. We multiply be the number of seconds we want.
                    int block_size = 100;
                    frame = bitalino.read(block_size*seconds);
                    System.out.println("size block: " + frame.length);

                    //Print the samples
                    for (int i = 0; i < frame.length; i++) {
                        System.out.println((block_size + i) + " seq: " + frame[i].seq + " "
                                + frame[i].analog[0] + " "
                        );

                    }
                } //stop acquisition
                catch (BITalinoException ex) {
                    Logger.getLogger(BitalinoManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            

            bitalino.stop();
        } catch (BITalinoException ex) {
            Logger.getLogger(BitalinoManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void disconnect() {
        try {
            bitalino.close();
            System.out.println("Bitalino disconnected.");
        } catch (BITalinoException ex) {
            Logger.getLogger(BitalinoManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public Frame[] getFrame() {
        return frame;
    }

    public void setFrame(Frame[] frame) {
        this.frame = frame;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    public boolean isStop() {
        return stop;
    }

}
