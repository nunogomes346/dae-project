package ejbs;

import dtos.EmergencyContactDTO;
import dtos.FaqDTO;
import dtos.MaterialDTO;
import dtos.NeedDTO;
import dtos.TextDTO;
import dtos.TutorialDTO;
import dtos.VideoDTO;
import entities.EmergencyContact;
import entities.FAQ;
import entities.Material;
import entities.MaterialGroup;
import entities.MaterialGroup.GROUP;
import entities.Need;
import entities.Patient;
import entities.Text;
import entities.Tutorial;
import entities.Video;
import exceptions.EntityDoesNotExistException;
import exceptions.MyConstraintViolationException;
import exceptions.Utils;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.EJB;
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
@Path("/needs")
public class NeedBean {

    @PersistenceContext
    EntityManager em;
    
    @EJB
    private FaqBean faqBean;
    @EJB
    private TutorialBean tutorialBean;
    @EJB
    private TextBean textBean;
    @EJB
    private EmergencyContactBean emergencyContactBean;
    @EJB
    private VideoBean videoBean;
    
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
    
    public NeedDTO getNeed(Long id) throws EntityDoesNotExistException {
        try {
            Need need = em.find(Need.class, id);
            if (need == null) {
                throw new EntityDoesNotExistException("There is no Need with that id.");
            }
            
            return needToDTO(need);
        } catch (EntityDoesNotExistException e) {
            throw e;
        } catch (EJBException e) {
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
            
            for (Material material : need.getMaterials()) {
                material.removeNeed(need);
            }
            
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
    
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("{id}/emergencyContact")
    public List<EmergencyContactDTO> getEmergencyContactREST (@PathParam("id") Long id)  throws EntityDoesNotExistException {
        try{
            System.out.print(id);
            Need need = em.find(Need.class, id);
            
            if(need == null){
                throw new EntityDoesNotExistException("There is no Need with that id.");
            }
            
            List<Material> materiais = need.getMaterials();
            List<EmergencyContact> emergencyContact = new ArrayList<>();
            for(Material m : materiais){
                if(m.getGroup().getGroupName() == GROUP.EmergencyContact){
                    emergencyContact.add((EmergencyContact) m);
                }
            }  
            return emergencyContactBean.emergencyContactsToDTOs(emergencyContact);
            
        }catch (EntityDoesNotExistException e) {
            throw e;             
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("{id}/video")
    public List<VideoDTO> getVideoREST (@PathParam("id") Long id)  throws EntityDoesNotExistException {
        try{
            
            Need need = em.find(Need.class, id);
            if(need == null){
                throw new EntityDoesNotExistException("There is no Need with that id.");
            }
            
            List<Material> materiais = need.getMaterials();
            List<Video> video = new ArrayList<>();
            for(Material m : materiais){
                if(m.getGroup().getGroupName() == GROUP.Video){
                    video.add((Video) m);
                }
            }  
            return videoBean.videosToDTOs(video);
            
        }catch (EntityDoesNotExistException e) {
            throw e;             
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("{id}/text")
    public List<TextDTO> getTextREST (@PathParam("id") Long id)  throws EntityDoesNotExistException {
        try{
            
            Need need = em.find(Need.class, id);
            if(need == null){
                throw new EntityDoesNotExistException("There is no Need with that id.");
            }
            
            List<Material> materiais = need.getMaterials();
            List<Text> text = new ArrayList<>();
            for(Material m : materiais){
                if(m.getGroup().getGroupName() == GROUP.Text){
                    text.add((Text) m);
                }
            }  
            return textBean.textsToDTOs(text);
            
        }catch (EntityDoesNotExistException e) {
            throw e;             
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("{id}/tutorial")
    public List<TutorialDTO> getTutorialREST (@PathParam("id") Long id)  throws EntityDoesNotExistException {
        try{
            
            Need need = em.find(Need.class, id);
            if(need == null){
                throw new EntityDoesNotExistException("There is no Need with that id.");
            }
            
            List<Material> materiais = need.getMaterials();
            List<Tutorial> tutorial = new ArrayList<>();
            for(Material m : materiais){
                if(m.getGroup().getGroupName() == GROUP.Tutorial){
                    tutorial.add((Tutorial) m);
                }
            }  
            return tutorialBean.tutorialsToDTOs(tutorial);
            
        }catch (EntityDoesNotExistException e) {
            throw e;             
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("{id}/faq")
    public List<FaqDTO> getFaqREST (@PathParam("id") Long id)  throws EntityDoesNotExistException {
        try{
            
            Need need = em.find(Need.class, id);
            if(need == null){
                throw new EntityDoesNotExistException("There is no Need with that id.");
            }
            
            List<Material> materiais = need.getMaterials();
            List<FAQ> faq = new ArrayList<>();
            for(Material m : materiais){
                if(m.getGroup().getGroupName() == GROUP.Faq){
                    faq.add((FAQ) m);
                }
            }  
            return faqBean.faqsToDTOs(faq);
            
        }catch (EntityDoesNotExistException e) {
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
