package ejbs;

import dtos.PatientDTO;
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
public class PatientBean {
    
    @PersistenceContext
    EntityManager em;
    
    public void create(String name,  String mail) 
            throws MyConstraintViolationException{
        try {
            Patient patient = new Patient(name, mail );

            em.persist(patient);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public void remove(int id)  throws EntityDoesNotExistException {
        try {
            Patient patient = em.find(Patient.class, id);
            
            if (patient == null) {
                throw new EntityDoesNotExistException("There is no patient with that name.");
            }
            
            em.remove(patient);
        } catch (EntityDoesNotExistException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public void update(int id, String name, String mail)
             throws EntityDoesNotExistException, MyConstraintViolationException {
        try {
            Patient patient = em.find(Patient.class, id);
            if (patient == null) {
                 throw new EntityDoesNotExistException("There is no patient with that name.");
            }
            
            patient.setName(name);
            patient.setMail(mail);
            
            em.merge(patient);
        } catch (EntityDoesNotExistException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));            
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public List<PatientDTO> getAll() {
        try {
            List<Patient> patient = (List<Patient>) em.createNamedQuery("getAllPatients").getResultList();
            
            return patientsToDTOs(patient); 
        } catch(EJBException e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public PatientDTO getPatient(int id) {
        try {
            Patient patient = em.find(Patient.class, id);
            
            return patientToDTO(patient);  
        } catch(EJBException e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    
    //Build DTOs
    PatientDTO patientToDTO(Patient patient) {
        return new PatientDTO(
                patient.getName(),
                patient.getMail(),
                patient.getCaregiver().getUsername());
    }
    
    
    List<PatientDTO> patientsToDTOs(List<Patient> patients) {
        List<PatientDTO> dtos = new ArrayList<>();
        for (Patient p : patients) {
            dtos.add(patientToDTO(p));
        }
        return dtos;
    }
}
