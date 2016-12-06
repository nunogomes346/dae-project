package ejbs;

import entities.Caregiver;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class CaregiverBean {
    
    @PersistenceContext
    EntityManager em;
    
    public void create(String username, String password, String name, String mail) {
        try {
            Caregiver caregiver = new Caregiver(username, password, name, mail);

            em.persist(caregiver);
        } catch(EJBException e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public void remove(String username) {
        try {
            Caregiver caregiver = em.find(Caregiver.class, username);
            
            em.remove(caregiver);
        } catch(EJBException e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public void update(String username, String password, String name, String mail) {
        try {
            Caregiver caregiver = em.find(Caregiver.class, username);
            if (caregiver == null) {
                return;
            }
            
            caregiver.setPassword(password);
            caregiver.setName(name);
            caregiver.setMail(mail);
            
            em.merge(caregiver);
        } catch(EJBException e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public List<Caregiver> getAll() {
        try {
            List<Caregiver> caregiver = (List<Caregiver>) em.createNamedQuery("getAllCaregivers").getResultList();
            
            return caregiver;  
        } catch(EJBException e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public Caregiver getCaregiver(String username) {
        try {
            Caregiver caregiver = em.find(Caregiver.class, username);
            
            return caregiver;  
        } catch(EJBException e) {
            throw new EJBException(e.getMessage());
        }
    }
}
