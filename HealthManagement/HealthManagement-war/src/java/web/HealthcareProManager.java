package web;

import static com.sun.xml.ws.security.addressing.impl.policy.Constants.logger;
import dtos.CaregiverDTO;
import dtos.CounterDTO;
import dtos.EmergencyContactDTO;
import dtos.FaqDTO;
import dtos.MaterialDTO;
import dtos.NeedDTO;
import dtos.PatientDTO;
import dtos.TutorialDTO;
import dtos.TextDTO;
import dtos.VideoDTO;
import ejbs.CaregiverBean;
import ejbs.EmergencyContactBean;
import ejbs.FaqBean;
import ejbs.PatientBean;
import ejbs.TutorialBean;
import ejbs.TextBean;
import ejbs.VideoBean;
import exceptions.EntityDoesNotExistException;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.event.ActionEvent;

@ManagedBean
@Named(value="healthcareProManager")
@SessionScoped
public class HealthcareProManager implements Serializable{
    
    @EJB
    private CaregiverBean caregiverBean;
    
    @EJB
    private PatientBean patientBean;
    
    /* MATERIALS */
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
    
    private CaregiverDTO newCaregiver;
    private CaregiverDTO currentCaregiver;
    private NeedDTO currentNeed;
    
    private EmergencyContactDTO newEmergencyContact;
    private EmergencyContactDTO currentEmergencyContact;
    private FaqDTO newFaq;
    private FaqDTO currentFaq;
    private TutorialDTO newTutorial;
    private TutorialDTO currentTutorial;
    private TextDTO newText;
    private TextDTO currentText;
    private VideoDTO newVideo;
    private VideoDTO currentVideo;
    
    private List<CaregiverDTO> filteredCaregivers;
    private List<MaterialDTO> filteredMaterials;
    
    private Long needId;
    private int materialId;
    
    private UIComponent component;
    private static final Logger LOGGER = Logger.getLogger("web.HealthcareProManager");
    
    public HealthcareProManager() {
        newCaregiver = new CaregiverDTO();
        newEmergencyContact = new EmergencyContactDTO();
        newFaq = new FaqDTO();
        newTutorial = new TutorialDTO();
        newText = new TextDTO();
        newVideo = new VideoDTO();
    }
    
    // ***************************************
    // ************ CAREGIVER ****************
    // ***************************************
    public String createCaregiver() {
        try {
            caregiverBean.create(
                    newCaregiver.getUsername(),
                    newCaregiver.getPassword(),
                    newCaregiver.getName(),
                    newCaregiver.getMail());

            setFilteredCaregivers(null);
            
            newCaregiver.reset();

            return "healthcarePro_index?faces-redirect=true";
        } catch (Exception e) {
            LOGGER.warning("Error: problem in method createCaregiver");
        }

        return "healthcarePro_caregiver_create?faces-redirect=true";
    }
    
    public List<CaregiverDTO> getAllCaregivers() {
        try {
            return caregiverBean.getAll();
        } catch (Exception e) {
            LOGGER.warning("Error: problem in method getAllCaregivers");
        }

        return null;
    }
    
    public String updateCaregiver() {
        try {
            caregiverBean.update(
                    currentCaregiver.getUsername(),
                    currentCaregiver.getPassword(),
                    currentCaregiver.getName(), 
                    currentCaregiver.getMail());
            
            setFilteredCaregivers(null);

            return "healthcarePro_index?faces-redirect=true";
        } catch (Exception e) {
            LOGGER.warning("Error: problem in method updateCaregiver");
        }

        return "healthcarePro_caregiver_update?faces-redirect=true";
    }
    
    public void removeCaregiver(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("deleteCaregiverId");
            String id = param.getValue().toString();

            caregiverBean.remove(id);
        } catch (Exception e) {
            LOGGER.warning("Error: problem in method removeCaregiver");
        }
    }
    
    public List<NeedDTO> getCaregiverPatientsNeeds() {
        try {
            return caregiverBean.getCaregiverPatientsNeeds(currentCaregiver.getUsername());
        } catch (EntityDoesNotExistException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
        return null;
    }
    
    public List<MaterialDTO> getCaregiverMaterials() {
        try {
            return caregiverBean.getCaregiverMaterials(currentCaregiver.getUsername());
        } catch (EntityDoesNotExistException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
        return null;
    }
            
    public List<MaterialDTO> getCaregiverPatientsNeedsMaterial() {
        try {
            return caregiverBean.getCaregiverPatientsNeedsMaterial(currentCaregiver.getUsername(), currentNeed.getId());
        } catch (EntityDoesNotExistException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
        return null;
    }
    
    public void diassociateMaterialFromCaregiver(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("materialId");
            int id = Integer.parseInt(param.getValue().toString());
            
            caregiverBean.diassociateMaterialFromCaregiver(currentCaregiver.getUsername(), id);
        } catch (EntityDoesNotExistException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
    }
    
    public int getLoginCounter() {
        try {
            return caregiverBean.getLoginCounter(currentCaregiver.getUsername());
        } catch (EntityDoesNotExistException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
        
        return 0;
    }
    
    public List<CounterDTO> getCaregiverProceedingsPerformedCounter() {
        try {
            return caregiverBean.getCaregiverProceedingsPerformedCounter(currentCaregiver.getUsername());
        } catch (EntityDoesNotExistException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
        return null;
    }
    
    public String rateCaregiver() {
        try {
            caregiverBean.rate(
                    currentCaregiver.getUsername(),
                    currentCaregiver.getRate());
            
            setFilteredCaregivers(null);

            return "healthcarePro_index?faces-redirect=true";
        } catch (Exception e) {
            return "healthcarePro_caregiver_update?faces-redirect=true"; 
        }
    }   
    
    // ***************************************
    // ************** PATIENT ****************
    // ***************************************
    public void associatePatientToCaregiver(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("patientId");
            Long id = Long.parseLong(param.getValue().toString());
            patientBean.associatePatientToCaregiver(id, currentCaregiver.getUsername());
        } catch (EntityDoesNotExistException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
    }

    public void diassociatePatientFromCaregiver(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("patientId");
            Long id = Long.parseLong(param.getValue().toString());
            patientBean.diassociatePatientFromCaregiver(id, currentCaregiver.getUsername());
        } catch (EntityDoesNotExistException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
    }
    
    public List<PatientDTO> getCaregiverPatients() {
        try {
            return patientBean.getCaregiverPatients(currentCaregiver.getUsername());
        } catch (EntityDoesNotExistException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
        return null;
    }

    public List<PatientDTO> getCaregiverNotPatients() {
        try {
            return patientBean.getCaregiverNotPatients(currentCaregiver.getUsername(), patientBean.getAll());
        } catch (EntityDoesNotExistException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
        return null;
    }
    
    public void associateMaterial() {
        try {
            caregiverBean.associateMaterial(currentCaregiver.getUsername(), materialId, needId);
        } catch (EntityDoesNotExistException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
    }
    
    // ***************************************
    // ************ MATERIALS ****************
    // ***************************************
    public List<MaterialDTO> getAllMaterials() {
        try {
            List<MaterialDTO> materials = new LinkedList<MaterialDTO>();
            
            materials.addAll(emergencyContactBean.getAll());
            materials.addAll(faqBean.getAll());
            materials.addAll(textBean.getAll());
            materials.addAll(tutorialBean.getAll());
            materials.addAll(videoBean.getAll());
            
            return materials;
        } catch (Exception e) {
            LOGGER.warning("Error: problem in method getAllMaterials");
        }

        return null;
    }
    
    public String detailsView(MaterialDTO material) {
        switch(material.getType()) {
            case "EmergencyContact": 
                setCurrentEmergencyContact((EmergencyContactDTO) material);
                return "healthcarePro_emergencyContact_details?faces-redirect=true";
            case "Faq": 
                setCurrentFaq((FaqDTO) material);
                return "healthcarePro_faq_details?faces-redirect=true";
            case "Text": 
                setCurrentText((TextDTO) material);
                return "healthcarePro_text_details?faces-redirect=true";
            case "Tutorial": 
                setCurrentTutorial((TutorialDTO) material);
                return "healthcarePro_tutorial_details?faces-redirect=true";
            case "Video": 
                setCurrentVideo((VideoDTO) material);
                return "healthcarePro_video_details?faces-redirect=true";
        }
        
        return null;
    }
    
    public String updateView(MaterialDTO material) {
        switch(material.getType()) {
            case "EmergencyContact": 
                setCurrentEmergencyContact((EmergencyContactDTO) material);
                return "healthcarePro_emergencyContact_update?faces-redirect=true";
            case "Faq": 
                setCurrentFaq((FaqDTO) material);
                return "healthcarePro_faq_update?faces-redirect=true";
            case "Text": 
                setCurrentText((TextDTO) material);
                return "healthcarePro_text_update?faces-redirect=true";
            case "Tutorial": 
                setCurrentTutorial((TutorialDTO) material);
                return "healthcarePro_tutorial_update?faces-redirect=true";
            case "Video": 
                setCurrentVideo((VideoDTO) material);
                return "healthcarePro_video_update?faces-redirect=true";
        }
        
        return null;
    }
    
    public void removeMaterial(MaterialDTO material) {
        switch(material.getType()) {
            case "EmergencyContact": 
                removeEmergencyContact(material.getId());
                break;
            case "Faq": 
                removeFaq(material.getId());
                break;
            case "Text": 
                removeText(material.getId());
                break;
            case "Tutorial": 
                removeTutorial(material.getId());
                break;
            case "Video": 
                removeVideo(material.getId());
                break;
        }
    }
    
    // ***********************************************
    // ************ EMERGENCY CONTACT ****************
    // ***********************************************
    public String createEmergencyContact() {
        try {
            emergencyContactBean.create(
                    newEmergencyContact.getDescription(),
                    newEmergencyContact.getName(),
                    newEmergencyContact.getTelephoneNumber());

            setFilteredMaterials(null);
            
            newEmergencyContact.reset();

            return "healthcarePro_index?faces-redirect=true";
        } catch (Exception e) {
            LOGGER.warning("Error: problem in method createEmergencyContact");
        }

        return "healthcarePro_emergencyContact_create?faces-redirect=true";
    }

    public List<EmergencyContactDTO> getAllEmergencyContacts() {
        try {
            return emergencyContactBean.getAll();
        } catch (Exception e) {
            LOGGER.warning("Error: problem in method getAllEmergencyContacts");
        }

        return null;
    }

    public String updateEmergengyContact() {
        try {

            emergencyContactBean.update(
                    currentEmergencyContact.getId(),
                    currentEmergencyContact.getDescription(),
                    currentEmergencyContact.getName(),
                    currentEmergencyContact.getTelephoneNumber()
            );
            
            setFilteredMaterials(null);

            return "healthcarePro_index?faces-redirect=true";
        } catch (Exception e) {
            LOGGER.warning("Error: problem in method updateEmergencyContact");
        }

        return "healthcarePro_emergencyContact_update?faces-redirect=true";
    }

    public void removeEmergencyContact(int materialId) {
        try {
            emergencyContactBean.remove(materialId);
        } catch (Exception e) {
            LOGGER.warning("Error: problem in method removeEmergencyContact");
        }
    }

    // *********************************
    // ************ FAQ ****************
    // *********************************
    public String createFaq() {
        try {
            faqBean.create(
                    newFaq.getDescription(),
                    newFaq.getQuestion(),
                    newFaq.getAnswer()
            );
            
            setFilteredMaterials(null);

            newFaq.reset();

            return "healthcarePro_index?faces-redirect=true";
        } catch (Exception e) {
            LOGGER.warning("Error: problem in method createFAQ");
        }

        return "healthcarePro_faq_create?faces-redirect=true";
    }

    public List<FaqDTO> getAllFaqs() {
        try {
            return faqBean.getAll();
        } catch (Exception e) {
            LOGGER.warning("Error: problem in method getAllFaq");
        }

        return null;
    }

    public String updateFaq() {
        try {
            faqBean.update(
                    currentFaq.getId(),
                    currentFaq.getDescription(),
                    currentFaq.getQuestion(),
                    currentFaq.getAnswer());
            
            setFilteredMaterials(null);

            return "healthcarePro_index?faces-redirect=true";
        } catch (Exception e) {
            LOGGER.warning("Error: problem in method updateFaq");
        }

        return "healthcarePro_faq_update?faces-redirect=true";
    }

    public void removeFaq(int materialId) {
        try {
            faqBean.remove(materialId);
        } catch (Exception e) {
            LOGGER.warning("Error: problem in method removeFaq");
        }
    }

    // **************************************
    // ************ TUTORIAL ****************
    // **************************************
    public String createTutorial() {
        try {

            tutorialBean.create(newTutorial.getDescription(), newTutorial.getText());

            setFilteredMaterials(null);
            
            newTutorial.reset();

            return "healthcarePro_index?faces-redirect=true";
        } catch (Exception e) {
            LOGGER.warning("Error: problem in method createTutorial");
        }

        return "healthcarePro_tutorials_create?faces-redirect=true";
    }

    public List<TutorialDTO> getAllTutorials() {
        try {
            return tutorialBean.getAll();
        } catch (Exception e) {
            LOGGER.warning("Error: problem in method getAllTutorials");
        }

        return null;
    }

    public String updateTutorial() {
        try {

            tutorialBean.update(
                    currentTutorial.getId(),
                    currentTutorial.getDescription(),
                    currentTutorial.getText()
            );
            
            setFilteredMaterials(null);

            return "healthcarePro_index?faces-redirect=true";
        } catch (Exception e) {
            LOGGER.warning("Error: problem in method updateTutorial");
        }

        return "healthcarePro_tutorial_update?faces-redirect=true";
    }

    public void removeTutorial(int materialId) {
        try {
            tutorialBean.remove(materialId);
        } catch (Exception e) {
            LOGGER.warning("Error: problem in method removeTutorial");
        }
    }
    
    // **********************************
    // ************ TEXT ****************
    // **********************************
    public String createText() {
        try {
            
            textBean.create(
                    newText.getDescription(),
                    newText.getText()
            );
            
            setFilteredMaterials(null);

            newText.reset();

            return "healthcarePro_index?faces-redirect=true";
        } catch (Exception e) {
            LOGGER.warning("Error: problem in method createText");
        }

        return "healthcarePro_text_create?faces-redirect=true";
    }

    public List<TextDTO> getAllTexts() {
        try {
            return textBean.getAll();
        } catch (Exception e) {
            LOGGER.warning("Error: problem in method getAllTexts");
        }

        return null;
    }

    public String updateText() {
        try {
            textBean.update(
                    currentText.getId(),
                    currentText.getDescription(),
                    currentText.getText()
            );
            
            setFilteredMaterials(null);
            
            return "healthcarePro_index?faces-redirect=true";
        } catch (Exception e) {
            LOGGER.warning("Error: problem in method updateText");
        }

        return "healthcarePro_text_update?faces-redirect=true";
    }

    public void removeText(int materialId) {
        try {
            textBean.remove(materialId);
        } catch (Exception e) {
            LOGGER.warning("Error: problem in method removeText");
        }
    }

    // ***********************************
    // ************ VIDEO ****************
    // ***********************************
    public String createVideo() {
        try {
     
            videoBean.create(
                    newVideo.getDescription(),
                    newVideo.getUrl()
            );

            setFilteredMaterials(null);
            
            newVideo.reset();

            return "healthcarePro_index?faces-redirect=true";
        } catch (Exception e) {
            LOGGER.warning("Error: problem in method createVideo");
        }

        return "healthcarePro_video_create?faces-redirect=true";
    }

    public List<VideoDTO> getAllVideos() {
        try {
            return videoBean.getAll();
        } catch (Exception e) {
            LOGGER.warning("Error: problem in method getAllVideos");
        }

        return null;
    }

    public String updateVideo() {
        try {
            videoBean.update(
                    currentVideo.getId(), 
                    currentVideo.getDescription(),
                    currentVideo.getUrl()
            );
            
            setFilteredMaterials(null);

            return "healthcarePro_index?faces-redirect=true";
        } catch (Exception e) {
            LOGGER.warning("Error: problem in method updateVideo");
        }

        return "healthcarePro_video_update?faces-redirect=true";
    }

    public void removeVideo(int materialId) {
        try {
            videoBean.remove(materialId);
        } catch (Exception e) {
            LOGGER.warning("Error: problem in method removeVideo");
        }
    }
    
    // ***************************************************
    // ************** GETTERS AND SETTERS ****************
    // ***************************************************
    public CaregiverDTO getNewCaregiver() {
        return newCaregiver;
    }

    public void setNewCaregiver(CaregiverDTO newCaregiver) {
        this.newCaregiver = newCaregiver;
    }

    public CaregiverDTO getCurrentCaregiver() {
        return currentCaregiver;
    }

    public void setCurrentCaregiver(CaregiverDTO currentCaregiver) {
        this.currentCaregiver = currentCaregiver;
    }

    public NeedDTO getCurrentNeed() {
        return currentNeed;
    }

    public void setCurrentNeed(NeedDTO currentNeed) {
        this.currentNeed = currentNeed;
    }
    
    public EmergencyContactDTO getNewEmergencyContact() {
        return newEmergencyContact;
    }

    public void setNewEmergencyContact(EmergencyContactDTO newEmergencyContact) {
        this.newEmergencyContact = newEmergencyContact;
    }

    public EmergencyContactDTO getCurrentEmergencyContact() {
        return currentEmergencyContact;
    }

    public void setCurrentEmergencyContact(EmergencyContactDTO currentEmergencyContact) {
        this.currentEmergencyContact = currentEmergencyContact;
    }

    public FaqDTO getNewFaq() {
        return newFaq;
    }

    public void setNewFaq(FaqDTO newFaq) {
        this.newFaq = newFaq;
    }

    public FaqDTO getCurrentFaq() {
        return currentFaq;
    }

    public void setCurrentFaq(FaqDTO currentFaq) {
        this.currentFaq = currentFaq;
    }

    public TutorialDTO getNewTutorial() {
        return newTutorial;
    }

    public void setNewTutorial(TutorialDTO newTutorial) {
        this.newTutorial = newTutorial;
    }

    public TutorialDTO getCurrentTutorial() {
        return currentTutorial;
    }

    public void setCurrentTutorial(TutorialDTO currentTutorial) {
        this.currentTutorial = currentTutorial;
    }

    public TextDTO getNewText() {
        return newText;
    }

    public void setNewText(TextDTO newText) {
        this.newText = newText;
    }

    public TextDTO getCurrentText() {
        return currentText;
    }

    public void setCurrentText(TextDTO currentText) {
        this.currentText = currentText;
    }

    public VideoDTO getNewVideo() {
        return newVideo;
    }

    public void setNewVideo(VideoDTO newVideo) {
        this.newVideo = newVideo;
    }

    public VideoDTO getCurrentVideo() {
        return currentVideo;
    }

    public void setCurrentVideo(VideoDTO currentVideo) {
        this.currentVideo = currentVideo;
    }

    public List<CaregiverDTO> getFilteredCaregivers() {
        return filteredCaregivers;
    }

    public void setFilteredCaregivers(List<CaregiverDTO> filteredCaregivers) {
        this.filteredCaregivers = filteredCaregivers;
    }

    public List<MaterialDTO> getFilteredMaterials() {
        return filteredMaterials;
    }

    public void setFilteredMaterials(List<MaterialDTO> filteredMaterials) {
        this.filteredMaterials = filteredMaterials;
    }
    
    public UIComponent getComponent() {
        return component;
    }

    public void setComponent(UIComponent component) {
        this.component = component;
    }

    public Long getNeedId() {
        return needId;
    }

    public void setNeedId(Long needId) {
        this.needId = needId;
    }

    public int getMaterialId() {
        return materialId;
    }

    public void setMaterialId(int materialId) {
        this.materialId = materialId;
    }
}
