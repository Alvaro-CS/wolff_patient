/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wolff_patient;

import BITalino.BitalinoManager;

/**
 *
 * @author ALVARO
 */
public class ManualECGThread implements Runnable{
    BitalinoManager bitalinoManager;
    
    public ManualECGThread(BitalinoManager bitalinoManager){ //For getting reference to bitalinoManager (with MAC etc) 
    this.bitalinoManager=bitalinoManager;
    }
    @Override
    public synchronized void run() {
       bitalinoManager.startManualECG();
        notify();
    }
    
}
