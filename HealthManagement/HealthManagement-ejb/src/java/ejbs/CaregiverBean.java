package ejbs;

import dtos.CaregiverDTO;
import dtos.EmergencyContactDTO;
import dtos.FaqDTO;
import dtos.MaterialDTO;
import dtos.NeedDTO;
import dtos.PatientDTO;
import dtos.TextDTO;
import dtos.TutorialDTO;
import dtos.VideoDTO;
import entities.Caregiver;
import entities.EmergencyContact;
import entities.FAQ;
import entities.Material;
import entities.Need;
import entities.Patient;
import entities.Proceeding;
import entities.Text;
import entities.Tutorial;
import entities.Video;
import exceptions.EntityAlreadyExistsException;
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
@Path("/caregivers")
public class CaregiverBean {
    
    @PersistenceContext
    EntityManager em;
    
    @EJB
    private PatientBean patientBean;
    
    @EJB
    private NeedBean needBean;
    
    @EJB
    private EmergencyContactBean emergencyContactBean;
    @EJB
    private FaqBean faqBean;
    @EJB
    private TutorialBean tutorialBean;
    @EJB
    private TextBean textBean;
    @EJB
    private VideoBean videoBean;
    
    public void create(String username, String password, String name, String mail) 
            throws EntityAlreadyExistsException, MyConstraintViolationException {
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
    
    public List<CaregiverDTO> getAll() {
        try {
            List<Caregiver> caregivers = (List<Caregiver>) em.createNamedQuery("getAllCaregivers").getResultList();
            
            return caregiversToDTOs(caregivers); 
        } catch(EJBException e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public CaregiverDTO getCaregiver(String username) throws EntityAlreadyExistsException {
        try {
            Caregiver caregiver = em.find(Caregiver.class, username);
            if (em.find(Caregiver.class, username) != null) {
                throw new EntityAlreadyExistsException("A Caregiver with that username already exists.");
            }
            
            return caregiverToDTO(caregiver);  
        } catch (EntityAlreadyExistsException e) {
            throw e;
        } catch(EJBException e) {
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
    
    public void remove(String username) throws EntityDoesNotExistException {
        try {
            Caregiver caregiver = em.find(Caregiver.class, username);
            if (caregiver == null) {
                throw new EntityDoesNotExistException("There is no caregiver with that username.");
            }
            
            for (Patient patient : caregiver.getPatients()) {
                patient.setCaregiver(null);
            }
            
            for (Material material : caregiver.getMaterials()) {
                material.removeCaregiver(caregiver);
            }
            
            for (Proceeding proceeding : caregiver.getProceedings()) {
                proceeding.setCaregiver(null);
            }
            
            em.remove(caregiver);
        } catch (EntityDoesNotExistException e) {
            throw e;
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
            
            return needBean.needsToDTOs(caregiverPatientsNeeds); 
        } catch (EntityDoesNotExistException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));            
        } catch(EJBException e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public List<MaterialDTO> getCaregiverMaterials(String username) 
            throws EntityDoesNotExistException, MyConstraintViolationException {
        try {
            Caregiver caregiver = em.find(Caregiver.class, username);
            if (caregiver == null) {
                throw new EntityDoesNotExistException("There is no caregiver with that username.");
            }
            
            List<EmergencyContactDTO> emergencyContacts = new LinkedList<EmergencyContactDTO>();
            List<FaqDTO> faqs = new LinkedList<FaqDTO>();
            List<TextDTO> texts = new LinkedList<TextDTO>();
            List<TutorialDTO> tutorials = new LinkedList<TutorialDTO>();
            List<VideoDTO> videos = new LinkedList<VideoDTO>();
            
            for (Material material : caregiver.getMaterials()) {
                switch (material.getGroup().getGroupName()) {
                    case EmergencyContact:
                        emergencyContacts.add(emergencyContactBean.emergencyContactToDTO((EmergencyContact) material));
                        break;
                    case Faq:
                        faqs.add(faqBean.faqToDTO((FAQ) material));
                        break;
                    case Text:
                        texts.add(textBean.textToDTO((Text) material));
                        break;
                    case Tutorial:
                        tutorials.add(tutorialBean.tutorialToDTO((Tutorial) material));
                        break;
                    case Video:
                        videos.add(videoBean.videoToDTO((Video) material));
                        break;
                }
            }
            
            List<MaterialDTO> materials = new LinkedList<MaterialDTO>();
            materials.addAll(emergencyContacts);
            materials.addAll(faqs);
            materials.addAll(texts);
            materials.addAll(tutorials);
            materials.addAll(videos);
            
            return materials;
        } catch (EntityDoesNotExistException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));            
        } catch(EJBException e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public List<MaterialDTO> getCaregiverPatientsNeedsMaterial(String username, Long needId) 
            throws EntityDoesNotExistException, MyConstraintViolationException {
        try {
            Caregiver caregiver = em.find(Caregiver.class, username);
            if (caregiver == null) {
                throw new EntityDoesNotExistException("There is no caregiver with that username.");
            }
            
            Need need = em.find(Need.class, needId);
            if (need == null) {
                throw new EntityDoesNotExistException("There is no need with that id.");
            }
            
            List<EmergencyContactDTO> emergencyContacts = new LinkedList<EmergencyContactDTO>();
            List<FaqDTO> faqs = new LinkedList<FaqDTO>();
            List<TextDTO> texts = new LinkedList<TextDTO>();
            List<TutorialDTO> tutorials = new LinkedList<TutorialDTO>();
            List<VideoDTO> videos = new LinkedList<VideoDTO>();
            
            for (Material material : caregiver.getMaterials()) {
                for(Material needMaterial : need.getMaterials()) {
                    if(material == needMaterial) {
                        switch (material.getGroup().getGroupName()) {
                            case EmergencyContact:
                                emergencyContacts.add(emergencyContactBean.emergencyContactToDTO((EmergencyContact) material));
                                break;
                            case Faq:
                                faqs.add(faqBean.faqToDTO((FAQ) material));
                                break;
                            case Text:
                                texts.add(textBean.textToDTO((Text) material));
                                break;
                            case Tutorial:
                                tutorials.add(tutorialBean.tutorialToDTO((Tutorial) material));
                                break;
                            case Video:
                                videos.add(videoBean.videoToDTO((Video) material));
                                break;
                        }
                        
                        break;
                    }
                }
            }
            
            List<MaterialDTO> materials = new LinkedList<MaterialDTO>();
            materials.addAll(emergencyContacts);
            materials.addAll(faqs);
            materials.addAll(texts);
            materials.addAll(tutorials);
            materials.addAll(videos);
            
            return materials;
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
            boolean canInsert = true;
            
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
            
            for (Material caregiverMaterial : caregiver.getMaterials()) {
                if(caregiverMaterial == material) {
                    canInsert = false;
                    break;
                }
                canInsert = true;
            }
            
            if(canInsert) {
                caregiver.addMaterial(material);
                material.addCaregiver(caregiver);
            }
            
            for (Material needMaterial : need.getMaterials()) {
                if(needMaterial == material) {
                    canInsert = false;
                    break;
                }
                canInsert = true;
            }
            
            if(canInsert) {
                material.addNeed(need);
                need.addMaterial(material);
            }
        } catch (EntityDoesNotExistException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));            
        } catch(EJBException e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public void diassociateMaterialFromCaregiver(String username, int materialId) 
            throws EntityDoesNotExistException, MyConstraintViolationException {
        try {
            Caregiver caregiver = em.find(Caregiver.class, username);
            if (caregiver == null) {
                throw new EntityDoesNotExistException("There is no caregiver with that username.");
            }   
            
            Material material = em.find(Material.class, materialId);
            if (caregiver == null) {
                throw new EntityDoesNotExistException("There is no material with that id.");
            }  
            
            material.removeCaregiver(caregiver);
            caregiver.removeMaterial(material);

        } catch (EntityDoesNotExistException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));            
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
            
            return patientBean.patientsToDTOs(patients);
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
}
