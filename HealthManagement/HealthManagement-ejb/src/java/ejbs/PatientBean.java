package ejbs;

import entities.Patient;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class PatientBean {
    
    @PersistenceContext
    EntityManager em;
    
    public void create(String username, String password, String name, String mail) {
        try {
            Patient patient = new Patient(username, password, name, mail);

            em.persist(patient);
        } catch(EJBException e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public void remove(String username) {
        try {
            Patient patient = em.find(Patient.class, username);
            
            em.remove(patient);
        } catch(EJBException e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public void update(String username, String password, String name, String mail) {
        try {
            Patient patient = em.find(Patient.class, username);
            if (patient == null) {
                return;
            }
            
            patient.setPassword(password);
            patient.setName(name);
            patient.setMail(mail);
            
            em.merge(patient);
        } catch(EJBException e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public List<Patient> getAll() {
        try {
            List<Patient> patient = (List<Patient>) em.createNamedQuery("getAllPatients").getResultList();
            
            return patient;  
        } catch(EJBException e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public Patient getPatient(String username) {
        try {
            Patient patient = em.find(Patient.class, username);
            
            return patient;  
        } catch(EJBException e) {
            throw new EJBException(e.getMessage());
        }
    }
}
