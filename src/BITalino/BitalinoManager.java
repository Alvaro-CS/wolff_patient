package BITalino;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BitalinoManager {

    private Frame[] frame;
    private Integer[] ecg_data;
    private boolean connected;
    private BITalino bitalino = null;
    private boolean stop;
    private static final int max_ecg_time = 86400; //24 h.

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
            connected = true;

        } catch (BITalinoException e) {
            Logger.getLogger(BitalinoManager.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("Could not connect to the bitalino, please try again");
        } 
    }

    public void startManualECG() { //TODO not overwrite
        try {
            stop = false;
            int[] channelsToAcquire = {1};
            bitalino.start(channelsToAcquire);
            int block_size = 100;
            ArrayList<Integer> ecg_data_list = new ArrayList<>();
            //ecg_data=new Integer[max_ecg_time*block_size];
            for (int j = 0; j < max_ecg_time; j++) { //infinite?
                try {

                    frame = bitalino.read(block_size);
                    System.out.println("size block: " + frame.length);

                    //Print the samples
                    for (int i = 0; i < frame.length; i++) {
                        ecg_data_list.add(frame[i].analog[0]);
                        //ecg_data[j*block_size+i]=frame[i].analog[0];
                        System.out.println((j * block_size + i) + " seq: " + frame[i].seq + " "
                                + frame[i].analog[0]);

                    }
                } catch (BITalinoException ex) {
                    Logger.getLogger(BitalinoManager.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (stop) { //exit from loop
                    ecg_data = ecg_data_list.toArray(new Integer[0]); //we transform the list to the array that will have the data
                    break;
                }
            }

            bitalino.stop();
        } catch (BITalinoException ex) {
            Logger.getLogger(BitalinoManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Throwable ex) {
            Logger.getLogger(BitalinoManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void startAutoECG(int seconds) {
        try {
            int[] channelsToAcquire = {1};
            bitalino.start(channelsToAcquire);
            int block_size = 100;
            ArrayList<Integer> ecg_data_list = new ArrayList<>();

            for (int j = 0; j < seconds; j++) {

                try {
                    //With sampling rate=100 and block_size=100, we record 1 second. We multiply be the number of seconds we want.
                    frame = bitalino.read(block_size);
                    //frame = bitalino.read(block_size*seconds);
                    ecg_data = new Integer[frame.length];

                    System.out.println(j+" seconds, size block: " + frame.length);

                    //Print the samples
                    for (int i = 0; i < frame.length; i++) {
                        ecg_data_list.add(frame[i].analog[0]);

                        //ecg_data[i] = frame[i].analog[0];
                        System.out.println((block_size + i) + " seq: " + frame[i].seq + " "
                                + frame[i].analog[0] + " "
                        );

                    }
                } //stop acquisition
                catch (BITalinoException ex) {
                    Logger.getLogger(BitalinoManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            ecg_data = ecg_data_list.toArray(new Integer[0]);
            System.out.println("Finished: "+ecg_data);
            bitalino.stop();
        } catch (BITalinoException ex) {
            Logger.getLogger(BitalinoManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Throwable ex) {
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

    public Integer[] getEcg_data() {
        return ecg_data;
    }

}
