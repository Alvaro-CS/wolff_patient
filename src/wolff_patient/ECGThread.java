package wolff_patient;

import BITalino.BitalinoManager;


public class ECGThread implements Runnable {

    private final BitalinoManager bitalinoManager;
    private final String mode;
    private int seconds;
    private Integer[] ecg_data;

    public ECGThread(BitalinoManager bitalinoManager, String mode, int seconds) { //For getting reference to bitalinoManager (with MAC etc) 
        this.bitalinoManager = bitalinoManager;
        this.mode = mode;
        this.seconds = seconds;
    }

    public ECGThread(BitalinoManager bitalinoManager, String mode) { //For getting reference to bitalinoManager (with MAC etc) 
        this.bitalinoManager = bitalinoManager;
        this.mode = mode;
    }

    /**
     * This method runs the thread with the 2 possible ECG recording options
     *
     */
    @Override
    public void run() {
        if (mode.equals("MANUAL")) {
            bitalinoManager.startManualECG();
            ecg_data = bitalinoManager.getEcg_data();
        } else if (mode.equals("AUTO")) {
            bitalinoManager.startAutoECG(seconds);
            ecg_data = bitalinoManager.getEcg_data();

        }
    }

    public Integer[] getEcg_data() {
        return ecg_data;
    }

}
