package entities;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@Entity
@NamedQueries({
    @NamedQuery(name = "getAllPatients",
            query = "SELECT p FROM Patient p ORDER BY p.name")
})
public class Patient implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String mail;

    @ManyToOne
    @JoinColumn(name = "CAREGIVER_USERNAME")
    private Caregiver caregiver;

    @OneToMany(mappedBy = "registeredProcedure")
    private List<RegisteredProcedure> registeredProcedures;

    public Patient() {
        this.registeredProcedures = new LinkedList<>();
    }

    public Patient(String name, String mail) {
        this.name = name;
        this.mail = mail;
        this.caregiver = null;
        this.registeredProcedures = new LinkedList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Caregiver getCaregiver() {
        return caregiver;
    }

    public void setCaregiver(Caregiver caregiver) {
        this.caregiver = caregiver;
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

    public void removeRegisteredProcedure(RegisteredProcedure r) {
        this.registeredProcedures.remove(r);
    }

}
