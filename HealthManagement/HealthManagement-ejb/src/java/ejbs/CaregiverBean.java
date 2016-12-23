package ejbs;

import dtos.CaregiverDTO;
import dtos.PatientDTO;
import entities.Caregiver;
import entities.Patient;
import exceptions.CaregiverEnrolledException;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityDoesNotExistException;
import exceptions.MyConstraintViolationException;
import exceptions.PatientNotInPatientsException;
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
            List<Caregiver> caregiver = (List<Caregiver>) em.createNamedQuery("getAllCaregivers").getResultList();
            
            return caregiversToDTOs(caregiver); 
        } catch(EJBException e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public CaregiverDTO getCaregiver(String username) {
        try {
            Caregiver caregiver = em.find(Caregiver.class, username);
            
            return caregiverToDTO(caregiver);  
        } catch(EJBException e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    
    public void enrollCaregiver(String usernameCaregiver, String usernamePatient) 
            throws EntityDoesNotExistException, PatientNotInPatientsException, CaregiverEnrolledException{
        try {

            Caregiver caregiver = em.find(Caregiver.class, usernameCaregiver);
            if (caregiver == null) {
                throw new EntityDoesNotExistException("There is no caregiver with that username.");
            }

            Patient patient = em.find(Patient.class, usernamePatient);
            if (patient == null) {
                throw new EntityDoesNotExistException("There is no patient with that code.");
            }

            if (!caregiver.getPatients().contains(patient)) {
                throw new PatientNotInPatientsException("Patients list has no such patient.");
            }

            if (patient.getCaregiver() != null) {
                throw new CaregiverEnrolledException("Patient has already enrolled with other caregiver.");
            }

            patient.addCariver(caregiver);
            caregiver.addPatient(patient);

        } catch (EntityDoesNotExistException | PatientNotInPatientsException | CaregiverEnrolledException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public void unrollCaregiver(String usernameCaregiver, String usernamePatient) 
            throws EntityDoesNotExistException, PatientNotInPatientsException {
        try {
            Patient p = em.find(Patient.class, usernamePatient);
            if(p == null){
                throw new EntityDoesNotExistException("There is no patient with that username.");
            }            
            
            Caregiver c = em.find(Caregiver.class, usernameCaregiver);
            if(c == null){
                throw new EntityDoesNotExistException("There is no caregiver with that username.");
            }
            
            if(p.getCaregiver() != c){
                throw new PatientNotInPatientsException();
            }            
            
            p.removeCaregiver(c);
            c.removePatient(p);

        } catch (EntityDoesNotExistException | PatientNotInPatientsException e) {
            throw e;             
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public List<PatientDTO> getEnrolledPatients(String username) throws EntityDoesNotExistException{
        try {
            Caregiver caregiver = em.find(Caregiver.class, username);
            if( caregiver == null){
                throw new EntityDoesNotExistException("There is no caregiver with that username.");
            }            
            List<Patient> patients = (List<Patient>) caregiver.getPatients();
            return patientsToDTOs(patients);
        } catch (EntityDoesNotExistException e) {
            throw e;             
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public List<PatientDTO> getUnrolledPatients(String username) throws EntityDoesNotExistException{
        try {
            Caregiver caregiver = em.find(Caregiver.class, username);
            if( caregiver == null){
                throw new EntityDoesNotExistException("There is no caregiver with that username.");
            }            
            List<Patient> p = (List<Patient>) em.createNamedQuery("getAllPatients")
                    .setParameter("username", caregiver.getUsername())
                    .getResultList();
            List<Patient> enrolled = em.find(Caregiver.class, username).getPatients();
            p.removeAll(enrolled);
            return patientsToDTOs(p);
        } catch (EntityDoesNotExistException e) {
            throw e;             
        } catch (Exception e) {
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
        for (Caregiver a : caregivers) {
            dtos.add(caregiverToDTO(a));
        }
        return dtos;
    }
    
    List<PatientDTO> patientsToDTOs(List<Patient> patients){
        List<PatientDTO> dtos = new ArrayList<>();
        for (Patient a : patients) {
            dtos.add(patientToDTO(a));
        }
        return dtos;
    }
    
    PatientDTO patientToDTO(Patient p) {
        return new PatientDTO(
                p.getName(),
                p.getMail(),
                p.getCaregiver().getUsername());
    }
}
