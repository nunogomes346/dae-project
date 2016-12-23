/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author joaoc
 */
public class CaregiverEnrolledException extends Exception {

    /**
     * Creates a new instance of <code>CaregiverEnrolledException</code> without
     * detail message.
     */
    public CaregiverEnrolledException() {
    }

    /**
     * Constructs an instance of <code>CaregiverEnrolledException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public CaregiverEnrolledException(String msg) {
        super(msg);
    }
}
