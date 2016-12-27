package ejbs;

import dtos.CaregiverDTO;
import entities.Caregiver;
import entities.Patient;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityDoesNotExistException;
import exceptions.MyConstraintViolationException;
import exceptions.Utils;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;

@Stateless
public class CaregiverBean {
    
    @PersistenceContext
    EntityManager em;
    
    public void create(String username, String password, String name, String mail) 
            throws EntityAlreadyExistsException, MyConstraintViolationException{
        try {
            if (em.find(Caregiver.class, username) != null) {
                throw new EntityAlreadyExistsException("A Caregiver with that username already exists.");
            }
            
            Caregiver caregiver = new Caregiver(username, password, name, mail);

            em.persist(caregiver);
        } catch (EntityAlreadyExistsException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public void remove(String username) throws EntityDoesNotExistException {
        try {
            Caregiver caregiver = em.find(Caregiver.class, username);
            
            if (caregiver == null) {
                throw new EntityDoesNotExistException("There is no caregiver with that username.");
            }
            
            for (Patient patient : caregiver.getPatients()) {
                patient.setCaregiver(null);
            }
            
            em.remove(caregiver);
        } catch (EntityDoesNotExistException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public void update(String username, String password, String name, String mail) 
            throws EntityDoesNotExistException, MyConstraintViolationException {
        try {
            Caregiver caregiver = em.find(Caregiver.class, username);
            if (caregiver == null) {
                throw new EntityDoesNotExistException("There is no caregiver with that username.");
            }
            
            caregiver.setPassword(password);
            caregiver.setName(name);
            caregiver.setMail(mail);
            
            em.merge(caregiver);
        } catch (EntityDoesNotExistException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));            
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public List<CaregiverDTO> getAll() {
        try {
            List<Caregiver> caregivers = (List<Caregiver>) em.createNamedQuery("getAllCaregivers").getResultList();
            
            return caregiversToDTOs(caregivers); 
        } catch(EJBException e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    //public List<CaregiverDTO> getAllByCaregiver(id do caregiver) {}
    
    public CaregiverDTO getCaregiver(String username) {
        try {
            Caregiver caregiver = em.find(Caregiver.class, username);
            
            return caregiverToDTO(caregiver);  
        } catch(EJBException e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    //Build DTOs
    CaregiverDTO caregiverToDTO(Caregiver caregiver) {
        return new CaregiverDTO(
                caregiver.getUsername(),
                null,
                caregiver.getName(),
                caregiver.getMail());
    }
    
    
    List<CaregiverDTO> caregiversToDTOs(List<Caregiver> caregivers) {
        List<CaregiverDTO> dtos = new ArrayList<>();
        for (Caregiver c : caregivers) {
            dtos.add(caregiverToDTO(c));
        }
        return dtos;
    }
}
