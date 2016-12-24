package entities;

import entities.UserGroup.GROUP;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQueries({
    @NamedQuery(name = "getAllCaregivers",
    query = "SELECT c FROM Caregiver c ORDER BY c.name")
})
public class Caregiver extends User implements Serializable {
   
   @OneToMany(mappedBy = "caregiver")
   private List<Patient> patients;
    
   public Caregiver() {  
       this.patients = new LinkedList<>();
    }
    
    public Caregiver(String username, String password, String name, String mail) {
        super(username, password, name, mail, GROUP.Caregiver);
        this.patients = new LinkedList<>();
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }
    
    public void addPatient(Patient p){
        this.patients.add(p);
    }
    
    public void removePatient(Patient p){
        this.patients.remove(p);
    }
}
