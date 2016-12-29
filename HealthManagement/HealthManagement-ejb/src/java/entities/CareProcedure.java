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
    @NamedQuery(name = "getAllCareProcedures",
            query = "SELECT n FROM CareProcedure n ORDER BY n.id ")
    }
)
public class CareProcedure implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

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

    private String dateProcedure;

    public CareProcedure() {
    }

    public CareProcedure(Material material, Patient patient, Caregiver caregiver, String date) {
        this.material = material;
        this.patient = patient;
        this.caregiver = caregiver;
        this.dateProcedure = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getDateProcedure() {
        return dateProcedure;
    }

    public void setDateProcedure(String dateProcedure) {
        this.dateProcedure = dateProcedure;
    }

}
