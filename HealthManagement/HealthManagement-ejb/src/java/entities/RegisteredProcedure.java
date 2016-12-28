package entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
    @NamedQuery(name = "getAllRegisteredProcedures",
            query = "SELECT n FROM RegisteredProcedure n ORDER BY n.id ")
})
public class RegisteredProcedure implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id; // it was long before being int

    @ManyToOne
    @JoinColumn(name = "MATERIAL_ID")
    private Material material;
    
    // patient
    @ManyToOne
    @JoinColumn(name = "PATIENT_ID")
    private Patient patient;
    
    // caregiver
    @ManyToOne
    @JoinColumn(name = "CAREGIVER_ID")
    private Caregiver caregiver;
    
    private String date;
    
    public RegisteredProcedure() {}
    
    public RegisteredProcedure(Material material, Patient patient, Caregiver caregiver, String date){
        this.material = material;
        this.patient = patient;
        this.caregiver = caregiver;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Caregiver getCaregiver() {
        return caregiver;
    }

    public void setCaregiver(Caregiver caregiver) {
        this.caregiver = caregiver;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
    
    
}
