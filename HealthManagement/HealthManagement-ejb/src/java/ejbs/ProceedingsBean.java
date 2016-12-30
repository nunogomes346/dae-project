package ejbs;

import dtos.ProceedingDTO;
import entities.Caregiver;
import entities.Material;
import entities.Patient;
import entities.Proceeding;
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
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

@Stateless
@Path("/proceedings")
public class ProceedingsBean {
    @PersistenceContext
    EntityManager em;
    
    public void create(int materialID, Long patientID, String caregiverUsernameID, String date)
            throws EntityDoesNotExistException, MyConstraintViolationException {
        try {
            Material material = em.find(Material.class, materialID);
            if (material == null) {
                throw new EntityDoesNotExistException("There is no material with that id.");
            }
            
            Patient patient = em.find(Patient.class, patientID);
            if (patient == null) {
                throw new EntityDoesNotExistException("There is no patient with that id.");
            }
            
            Caregiver caregiver = em.find(Caregiver.class, caregiverUsernameID);
            if (caregiver == null) {
                throw new EntityDoesNotExistException("There is no caregiver with that username.");
            }
            
            Proceeding proceeding  = new Proceeding(material, patient, caregiver, date);
            
            em.persist(proceeding);
        } catch (EntityDoesNotExistException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));            
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public void update(Long proceedingID, Long materialID)
            throws EntityDoesNotExistException, MyConstraintViolationException {
        try {
            Proceeding proceeding = em.find(Proceeding.class, proceedingID);
            if (proceeding == null) {
                throw new EntityDoesNotExistException("There is no proceeding with that id.");
            }
            
            Material material = em.find(Material.class, materialID);
            if (material == null) {
                throw new EntityDoesNotExistException("There is no material with that id.");
            }
            
            proceeding.setMaterial(material);
            
            em.persist(proceeding);
        } catch (EntityDoesNotExistException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));            
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public List<ProceedingDTO> getAll() {
        try {
            List<Proceeding> proceedings = (List<Proceeding>) em.createNamedQuery("getAllProceeding").getResultList();
            
            return proceedingsToDTOs(proceedings); 
        } catch(EJBException e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public ProceedingDTO getProceeding(Long id) throws EntityDoesNotExistException {
        try {
            Proceeding proceeding = em.find(Proceeding.class, id);
            if (proceeding == null) {
                throw new EntityDoesNotExistException("There is no prooceding with that id.");
            }
            
            return proceedingToDTO(proceeding);
        } catch (EntityDoesNotExistException e) {
            throw e;
        } catch (EJBException e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public void remove(Long id) throws EntityDoesNotExistException {
        try {
            Proceeding proceeding = em.find(Proceeding.class, id);
            if (proceeding == null) {
                throw new EntityDoesNotExistException("There is no procedure with that id.");
            }
            
            proceeding.getCaregiver().removeProceeding(proceeding);
            proceeding.getMaterial().removeProceeding(proceeding);
            proceeding.getPatient().removeProceeding(proceeding);
            
            em.remove(proceeding);
        } catch (EntityDoesNotExistException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @DELETE
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("{proceedingId}")
    public void removeProceedingREST(@PathParam("proceedingId") Long proceedingId) throws EntityDoesNotExistException {
        try {
            remove(proceedingId);
        } catch (EntityDoesNotExistException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    //Build DTOs
    public ProceedingDTO proceedingToDTO(Proceeding proceeding) {
        return new ProceedingDTO(
                proceeding.getId(),
                proceeding.getMaterial().getId(),
                proceeding.getMaterial().getDescription(),
                proceeding.getPatient().getId(),
                proceeding.getPatient().getName(),
                proceeding.getCaregiver().getUsername(),
                proceeding.getProceedingDate()
        );
    }
    public List<ProceedingDTO> proceedingsToDTOs(List<Proceeding> proceedings) {
        List<ProceedingDTO> dtos = new ArrayList<>();
        for (Proceeding p : proceedings) {
            dtos.add(proceedingToDTO(p));
        }
        return dtos;
    }
}
