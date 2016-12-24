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
public class PatientNotInPatientsException extends Exception {

    /**
     * Creates a new instance of <code>PatientNotInPatientsException</code>
     * without detail message.
     */
    public PatientNotInPatientsException() {
    }

    /**
     * Constructs an instance of <code>PatientNotInPatientsException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public PatientNotInPatientsException(String msg) {
        super(msg);
    }
}
