package entities;

import entities.UserGroup.GROUP;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
    @NamedQuery(name = "getAllPatients",
    query = "SELECT p FROM Patient p ORDER BY p.name")
})
public class Patient extends User implements Serializable {

    public Patient() { 
        
    }
    
    public Patient(String username, String password, String name, String mail) {
        super(username, password, name, mail, GROUP.Patient);
    }

}
