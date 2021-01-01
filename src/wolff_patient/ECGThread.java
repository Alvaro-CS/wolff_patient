/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wolff_patient;

import BITalino.BitalinoManager;
import BITalino.Frame;

/**
 *
 * @author ALVARO
 */
public class ECGThread implements Runnable{
    private final BitalinoManager bitalinoManager;
    private final String mode;
    private int seconds;
    private Integer[] ecg_data;

    public ECGThread(BitalinoManager bitalinoManager,String mode,int seconds){ //For getting reference to bitalinoManager (with MAC etc) 
    this.bitalinoManager=bitalinoManager;
    this.mode=mode;
    this.seconds=seconds;
    }
    public ECGThread(BitalinoManager bitalinoManager,String mode){ //For getting reference to bitalinoManager (with MAC etc) 
    this.bitalinoManager=bitalinoManager;
    this.mode=mode;
    }
    
    @Override
    public void run() {
        if(mode.equals("MANUAL")){
       bitalinoManager.startManualECG();
       ecg_data=bitalinoManager.getEcg_data();
        }
        else if (mode.equals("AUTO")){
            
        bitalinoManager.startAutoECG(seconds);
        ecg_data=bitalinoManager.getEcg_data();
        
            System.out.println("ECG after method "+ecg_data);

        }
       // saveECG();
    }
    public void saveECG() {
        //We get the data (Frame class) from Bitalino, get the useful info (int) and send it.
        Frame[] frame = bitalinoManager.getFrame();
        ecg_data = new Integer[frame.length];
        for (int i = 0; i < frame.length; i++) {
            ecg_data[i] = frame[i].analog[0];
        }
    }

    public Integer[] getEcg_data() {
        return ecg_data;
    }
    
    
}
