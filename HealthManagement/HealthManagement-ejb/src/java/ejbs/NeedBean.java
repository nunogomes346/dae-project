package ejbs;

import dtos.NeedDTO;
import entities.Material;
import entities.Need;
import entities.Patient;
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
public class NeedBean {

    @PersistenceContext
    EntityManager em;
    
    public void create(String description) throws MyConstraintViolationException{
        try {
            Need need = new Need(description);

            em.persist(need);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public List<NeedDTO> getAll() {
        try {
            List<Need> needs = (List<Need>) em.createNamedQuery("getAllNeeds").getResultList();
            
            return needsToDTOs(needs); 
        } catch(EJBException e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public void update(Long id, String description) 
            throws EntityDoesNotExistException, MyConstraintViolationException {
        try {
            Need need = em.find(Need.class, id);
            if (need == null) {
                throw new EntityDoesNotExistException("There is no need with that id.");
            }

            need.setDescription(description);

            em.merge(need);
        } catch (EntityDoesNotExistException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));            
        } catch (EJBException e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public void remove(Long id) throws EntityDoesNotExistException {
        try {
            Need need = em.find(Need.class, id);
            if (need == null) {
                throw new EntityDoesNotExistException("There is no need with that id.");
            }
            
            for (Patient patient : need.getPatients()) {
                patient.removeNeed(need);
            }
            
            need.setPatients(null);
            
            for (Material material : need.getMaterials()) {
                material.removeNeed(need);
            }
            
            need.setMaterials(null);
            
            em.remove(need);
        } catch (EntityDoesNotExistException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public void associateNeedToPatient(Long needId, Long patientId) 
            throws EntityDoesNotExistException {
        try {
            Need need = em.find(Need.class, needId);
            if (need == null) {
                throw new EntityDoesNotExistException("There is no need with that id.");
            }
            
            Patient patient = em.find(Patient.class, patientId);
            if (patient == null) {
                throw new EntityDoesNotExistException("There is no patient with that code.");
            }

            need.addPatient(patient);
            patient.addNeed(need);

        } catch (EntityDoesNotExistException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public void diassociateNeedFromPatient(Long needId, Long patientId) 
            throws EntityDoesNotExistException {
        try {
            Need need = em.find(Need.class, needId);
            if (need == null) {
                throw new EntityDoesNotExistException("There is no need with that id.");
            }
            
            Patient patient = em.find(Patient.class, patientId);
            if (patient == null) {
                throw new EntityDoesNotExistException("There is no patient with that code.");
            }        
            
            need.removePatient(patient);
            patient.removeNeed(need);

        } catch (EntityDoesNotExistException e) {
            throw e;             
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public void associateMaterialToNeed(int materialId, Long needId) 
            throws EntityDoesNotExistException {
        try {
            Need need = em.find(Need.class, needId);
            if (need == null) {
                throw new EntityDoesNotExistException("There is no need with that id.");
            }
            
            Material material = em.find(Material.class, materialId);
            if (material == null) {
                throw new EntityDoesNotExistException("There is no material with that id.");
            }

            need.addMaterial(material);
            material.addNeed(need);

        } catch (EntityDoesNotExistException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public void diassociateMaterialFromNeed(int materialId, Long needId) 
            throws EntityDoesNotExistException {
        try {
            Need need = em.find(Need.class, needId);
            if (need == null) {
                throw new EntityDoesNotExistException("There is no need with that id.");
            }
            
            Material material = em.find(Material.class, materialId);
            if (material == null) {
                throw new EntityDoesNotExistException("There is no material with that id.");
            }       
            
            need.removeMaterial(material);
            material.removeNeed(need);

        } catch (EntityDoesNotExistException e) {
            throw e;             
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    //Build DTOs
    public NeedDTO needToDTO(Need need) {
        return new NeedDTO(
                need.getId(),
                need.getDescription());
    }
    
    public List<NeedDTO> needsToDTOs(List<Need> needs) {
        List<NeedDTO> dtos = new ArrayList<>();
        for (Need n : needs) {
            dtos.add(needToDTO(n));
        }
        return dtos;
    }
}
