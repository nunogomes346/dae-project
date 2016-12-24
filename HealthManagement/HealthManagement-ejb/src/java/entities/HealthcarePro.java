package entities;

import entities.UserGroup.GROUP;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
    @NamedQuery(name = "getAllHealthcarePros",
    query = "SELECT h FROM HealthcarePro h ORDER BY h.name")
})
public class HealthcarePro extends User implements Serializable {

    public HealthcarePro() { 
    }
    
    public HealthcarePro(String username, String password, String name, String mail) {
        super(username, password, name, mail, GROUP.HealthcarePro);
    }
    
}
