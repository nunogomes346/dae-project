
package ejbs;

import entities.HealthcarePro;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class HealthcareProBean {

    @PersistenceContext
    EntityManager em;
    
    public void create(String username, String password, String name, String mail) {
        try {
            HealthcarePro healthcarepro = new HealthcarePro(username, password, name, mail);

            em.persist(healthcarepro);
        } catch(EJBException e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public void remove(String username) {
        try {
            HealthcarePro healthcarepro = em.find(HealthcarePro.class, username);
            
            em.remove(healthcarepro);
        } catch(EJBException e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public void update(String username, String password, String name, String mail) {
        try {
            HealthcarePro healthcarepro = em.find(HealthcarePro.class, username);
            if (healthcarepro == null) {
                return;
            }
            
            healthcarepro.setPassword(password);
            healthcarepro.setName(name);
            healthcarepro.setMail(mail);
            
            em.merge(healthcarepro);
        } catch(EJBException e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public List<HealthcarePro> getAll() {
        try {
            List<HealthcarePro> healthcarepro = (List<HealthcarePro>) em.createNamedQuery("getAllHealthcarePros").getResultList();
            
            return healthcarepro;  
        } catch(EJBException e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public HealthcarePro getHealthcarePro(String username) {
        try {
            HealthcarePro healthcarepro = em.find(HealthcarePro.class, username);
            
            return healthcarepro;  
        } catch(EJBException e) {
            throw new EJBException(e.getMessage());
        }
    }
}
