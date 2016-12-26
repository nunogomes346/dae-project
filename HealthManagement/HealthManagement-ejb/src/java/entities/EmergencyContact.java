package entities;

import entities.MaterialGroup.GROUP;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
    @NamedQuery(name = "getAllEmergencyContacts",
    query = "SELECT e FROM EmergencyContact e ORDER BY e.id")
})
public class EmergencyContact extends Material implements Serializable {

    private String name;
    private String telephoneNumber;
    
    public EmergencyContact(){
    }
    
    public EmergencyContact(String name, String telephoneNumber){
        super(GROUP.EmergencyContact);
        this.name = name;
        this.telephoneNumber = telephoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }
    
    
    
}
