package entities;

import entities.UserGroup.GROUP;
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
    @NamedQuery(name = "getAllPatients",
    query = "SELECT p FROM Patient p ORDER BY p.name")
})
public class Patient implements Serializable {
    
     @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String name; 
    private String mail;
    
    @ManyToOne
    @JoinColumn(name = "CAREGIVER_USERNAME")
    private Caregiver caregiver;

    public Patient() { 
        
    }
    
    public Patient(String name, String mail) {
        this.name = name;
        this.mail = mail;
        this.caregiver = null;
    }

    public Caregiver getCaregiver() {
        return caregiver;
    }

    public void setCaregiver(Caregiver caregiver) {
        this.caregiver = caregiver;
    }
    
    public void addCariver(Caregiver c) {
        this.caregiver = c;
    }
    
    public void removeCaregiver(Caregiver caregiver) {
        setCaregiver(null);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    
}
