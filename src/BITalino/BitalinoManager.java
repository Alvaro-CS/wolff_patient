package BITalino;

import java.util.logging.Level;
import java.util.logging.Logger;

public class BitalinoManager {

    public Frame[] frame;
    private boolean connected;
    private BITalino bitalino = null;
    private boolean stop;

    //Create an object BitalinoManager, with the MAC Addres specified.
    public BitalinoManager(String macAddress) {
        connected = false;
        try {
            bitalino = new BITalino();

            //Sampling rate, should be 10, 100 or 1000
            int SamplingRate = 1000;
            System.out.println("Connecting with " + macAddress);
            bitalino.open(macAddress, SamplingRate);
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

    public void startManualECG() { //TODO Infinite, until user presses "STOP"
        try {
            stop=false;
            //Read in total 200 times
            for (int j = 0; j < Integer.MAX_VALUE; j++) { //infinite?
                try {
                    //Each time read a block of 10 samples
                    int block_size = 50;
                    frame = bitalino.read(block_size);
                    System.out.println("size block: " + frame.length);

                    //Print the samples
                    for (int i = 0; i < frame.length; i++) {
                        System.out.println((j * block_size + i) + " seq: " + frame[i].seq + " "
                                + frame[i].analog[0] + " "
                                + frame[i].analog[1] + " "
                        //+ frame[i].analog[2] + " "
                        //+ frame[i].analog[3] + " "
                        //+ frame[i].analog[4] + " "
                        //+ frame[i].analog[5]
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
            for (int j = 0; j < seconds; j++) {//TODO SECONDS
                try {
                    //Each time read a block of 10 samples
                    int block_size = 10;
                    frame = bitalino.read(block_size);
                    System.out.println("J" + j);
                    System.out.println("size block: " + frame.length);

                    //Print the samples
                    for (int i = 0; i < frame.length; i++) {
                        System.out.println((j * block_size + i) + " seq: " + frame[i].seq + " "
                                + frame[i].analog[0] + " "
                                + frame[i].analog[1] + " "
                        //+ frame[i].analog[2] + " "
                        //+ frame[i].analog[3] + " "
                        //+ frame[i].analog[4] + " "
                        //+ frame[i].analog[5]
                        );

                    }
                } //stop acquisition
                catch (BITalinoException ex) {
                    Logger.getLogger(BitalinoManager.class.getName()).log(Level.SEVERE, null, ex);
                }
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
