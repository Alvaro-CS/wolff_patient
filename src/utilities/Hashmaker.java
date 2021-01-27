/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import wolff_patient.RegistrationController;


public class Hashmaker {
    
    // Creates a SHA256 using user's password as input, generates hash with MessageDigest class.
    // MessageDigest Class privdes cryptographic hashfunctuon to findhash value of a text.
    // The algorithms are initialized in static method called getInstance().After selecting the algorithm it calculates the 
    // digest value and return the results in byte array,
    public static String getSHA256(String input) {
        String hash = null;

        try {

            //MEssageDigest pide el resultado en bytes del mensaje y devuelve el resultado en bytes.
            //Indicamos la funcion Hash que queremos que lo procese
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            digest.reset();
            digest.update(input.getBytes("utf8"));

            // BigInteger class conveerts the resultant byte array into its sign-magnitude representation, and then
            // this is converted into hex format
            BigInteger resumenNumero = new BigInteger(1, digest.digest());
            hash = String.format("%064x", resumenNumero);

        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            Logger.getLogger(RegistrationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return hash;
    }
}
