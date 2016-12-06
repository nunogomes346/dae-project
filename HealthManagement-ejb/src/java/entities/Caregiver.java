package entities;

import entities.UserGroup.GROUP;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
    @NamedQuery(name = "getAllCaregivers",
    query = "SELECT c FROM Caregiver c ORDER BY c.name")
})
public class Caregiver extends User implements Serializable {

   public Caregiver() {  
    }
    
    public Caregiver(String username, String password, String name, String mail) {
        super(username, password, name, mail, GROUP.Caregiver);
    }
    
}
