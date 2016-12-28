package ejbs;

import dtos.CaregiverDTO;
import dtos.NeedDTO;
import dtos.PatientDTO;
import entities.Caregiver;
import entities.Material;
import entities.Need;
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
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Stateless
@Path("/caregivers")
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
    
    public void rate(String username, String rate) 
            throws EntityDoesNotExistException, MyConstraintViolationException {
        try {
            Caregiver caregiver = em.find(Caregiver.class, username);
            if (caregiver == null) {
                throw new EntityDoesNotExistException("There is no caregiver with that username.");
            }
            
            caregiver.setRate(rate);
            
            em.merge(caregiver);
        } catch (EntityDoesNotExistException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));            
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public List<NeedDTO> getCaregiverPatientsNeeds(String username)
            throws EntityDoesNotExistException, MyConstraintViolationException {
        try {
            List<Need> caregiverPatientsNeeds = new ArrayList<Need>();
            
            Caregiver caregiver = em.find(Caregiver.class, username);
            if (caregiver == null) {
                throw new EntityDoesNotExistException("There is no caregiver with that username.");
            }
            
            for (Patient patient : caregiver.getPatients()) {
                for (Need need : patient.getNeeds()) {
                    if(!caregiverPatientsNeeds.contains(need)) {
                        caregiverPatientsNeeds.add(need);
                    }
                }
            }
            
            return needsToDTOs(caregiverPatientsNeeds); 
        } catch (EntityDoesNotExistException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));            
        } catch(EJBException e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public void associateMaterial(String username, int materialId, Long needId)
            throws EntityDoesNotExistException, MyConstraintViolationException {
        try {
            Caregiver caregiver = em.find(Caregiver.class, username);
            if (caregiver == null) {
                throw new EntityDoesNotExistException("There is no caregiver with that username.");
            }
            
            Material material = em.find(Material.class, materialId);
            if (material == null) {
                throw new EntityDoesNotExistException("There is no material with that id.");
            }
            
            Need need = em.find(Need.class, needId);
            if (need == null) {
                throw new EntityDoesNotExistException("There is no need with that id.");
            }
            
            caregiver.addMaterial(material);
            material.addCaregiver(caregiver);
            
            material.addNeed(need);
            need.addMaterial(material);
        } catch (EntityDoesNotExistException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));            
        } catch(EJBException e) {
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
    
    public CaregiverDTO getCaregiver(String username) {
        try {
            Caregiver caregiver = em.find(Caregiver.class, username);
            
            return caregiverToDTO(caregiver);  
        } catch(EJBException e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    
    
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("{username}/patients")
    public List<PatientDTO> getCaregiverPatientsREST(@PathParam("username") String username) throws EntityDoesNotExistException{
        try {
            Caregiver caregiver = em.find(Caregiver.class, username);
            if(caregiver == null){
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
    
    //Build DTOs
    CaregiverDTO caregiverToDTO(Caregiver caregiver) {
        return new CaregiverDTO(
                caregiver.getUsername(),
                null,
                caregiver.getName(),
                caregiver.getMail(),
                caregiver.getRate());
    }
    
    List<CaregiverDTO> caregiversToDTOs(List<Caregiver> caregivers) {
        List<CaregiverDTO> dtos = new ArrayList<>();
        for (Caregiver c : caregivers) {
            dtos.add(caregiverToDTO(c));
        }
        return dtos;
    }
    
    PatientDTO patientToDTO(Patient patient) {
        return new PatientDTO(
                patient.getId(),
                patient.getName(),
                patient.getMail(),
                (patient.getCaregiver() == null) ? null : patient.getCaregiver().getUsername());
    }
    
    List<PatientDTO> patientsToDTOs(List<Patient> patients) {
        List<PatientDTO> dtos = new ArrayList<>();
        for (Patient p : patients) {
            dtos.add(patientToDTO(p));
        }
        return dtos;
    }
    
    NeedDTO needToDTO(Need need) {
        return new NeedDTO(
                need.getId(),
                need.getDescription());
    }
    
    List<NeedDTO> needsToDTOs(List<Need> needs) {
        List<NeedDTO> dtos = new ArrayList<>();
        for (Need n : needs) {
            dtos.add(needToDTO(n));
        }
        return dtos;
    }
}
