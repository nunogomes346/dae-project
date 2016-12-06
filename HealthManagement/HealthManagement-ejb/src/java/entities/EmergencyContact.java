/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author joaoc
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "getAllEmergencyContacts",
    query = "SELECT e FROM EmergencyContact e ORDER BY e.id")
})
public class EmergencyContact extends Material implements Serializable {

    private String name;
    private int telephoneNumber;
    
    public EmergencyContact(){
    }
    
    public EmergencyContact(String name, int telephoneNumber){
        super();
        this.name = name;
        this.telephoneNumber = telephoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(int telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }
    
    
    
}
