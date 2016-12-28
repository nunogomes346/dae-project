package entities;

import entities.UserGroup.GROUP;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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

    @OneToMany(mappedBy = "registeredProcedure")
    private List<RegisteredProcedure> registeredProcedures;
   
    @ManyToMany
    @JoinTable(name = "CAREGIVERS_MATERIALS", 
            joinColumns = @JoinColumn(name = "CAREGIVER_USERNAME", referencedColumnName = "USERNAME"),
            inverseJoinColumns = @JoinColumn(name = "MATERIAL_ID", referencedColumnName = "ID"))
    private List<Material> materials;
    
    private String rate;
    
    public Caregiver() {  
       this.patients = new LinkedList<Patient>();
       this.materials = new LinkedList<Material>();
       this.registeredProcedures = new LinkedList<>();
    }
    
    public Caregiver(String username, String password, String name, String mail) {
        super(username, password, name, mail, GROUP.Caregiver);
        this.patients = new LinkedList<Patient>();
        this.materials = new LinkedList<Material>();
        this.registeredProcedures = new LinkedList<>();
        this.rate = "No rate";
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

    public void addPatient(Patient p) {
        this.patients.add(p);
    }

    public void removePatient(Patient p) {
        this.patients.remove(p);
    }

    public List<Material> getMaterials() {
        return materials;
    }

    public void setMaterials(List<Material> materials) {
        this.materials = materials;
    }
    
    public void addMaterial(Material material){
        this.materials.add(material);
    }
    
    public void removeMaterial(Material material){
        this.materials.remove(material);
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public List<RegisteredProcedure> getRegisteredProcedures() {
        return registeredProcedures;
    }

    public void setRegisteredProcedures(List<RegisteredProcedure> registeredProcedures) {
        this.registeredProcedures = registeredProcedures;
    }

    public void addRegisteredProcedures(RegisteredProcedure r) {
        this.registeredProcedures.add(r);
    }

    public void removeRegisteredProcedure (RegisteredProcedure r) {
        this.registeredProcedures.remove(r);
    }
}
