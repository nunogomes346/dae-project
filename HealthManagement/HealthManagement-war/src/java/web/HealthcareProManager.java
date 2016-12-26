package web;

import static com.sun.xml.ws.security.addressing.impl.policy.Constants.logger;
import dtos.CaregiverDTO;
import dtos.EmergencyContactDTO;
import dtos.FaqDTO;
import dtos.PatientDTO;
import dtos.ProcedureDTO;
import dtos.TextDTO;
import dtos.VideoDTO;
import ejbs.CaregiverBean;
import ejbs.EmergencyContactBean;
import ejbs.FaqBean;
import ejbs.PatientBean;
import ejbs.ProcedureBean;
import ejbs.TextBean;
import ejbs.VideoBean;
import exceptions.EntityDoesNotExistException;
import java.io.Serializable;
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
    private ProcedureBean procedureBean;
    @EJB
    private TextBean textBean;
    @EJB
    private VideoBean videoBean;
    
    private CaregiverDTO newCaregiver;
    private CaregiverDTO currentCaregiver;
    
    private EmergencyContactDTO newEmergencyContact;
    private EmergencyContactDTO currentEmergencyContact;
    private FaqDTO newFaq;
    private FaqDTO currentFaq;
    private ProcedureDTO newProcedure;
    private ProcedureDTO currentProcedure;
    private TextDTO newText;
    private TextDTO currentText;
    private VideoDTO newVideo;
    private VideoDTO currentVideo;
    
    private List<CaregiverDTO> filteredCaregivers;
    private List<EmergencyContactDTO> filteredEmergencyContacts;
    private List<FaqDTO> filteredFaqs;
    private List<ProcedureDTO> filteredProcedures;
    private List<TextDTO> filteredTexts;
    private List<VideoDTO> filteredVideos;
    
    private UIComponent component;
    private static final Logger LOGGER = Logger.getLogger("web.HealthcareProManager");
    
    public HealthcareProManager() {
        newCaregiver = new CaregiverDTO();
        newEmergencyContact = new EmergencyContactDTO();
        newFaq = new FaqDTO();
        newProcedure = new ProcedureDTO();
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
    
    // ***********************************************
    // ************ EMERGENCY CONTACT ****************
    // ***********************************************
    public String createEmergencyContact() {
        try {
            emergencyContactBean.create(
                    newEmergencyContact.getName(),
                    newEmergencyContact.getTelephoneNumber());

            setFilteredEmergencyContacts(null);
            
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
                    currentEmergencyContact.getName(),
                    currentEmergencyContact.getTelephoneNumber()
            );
            
            setFilteredEmergencyContacts(null);

            return "healthcarePro_index?faces-redirect=true";
        } catch (Exception e) {
            LOGGER.warning("Error: problem in method updateEmergencyContact");
        }

        return "healthcarePro_emergencyContact_update?faces-redirect=true";
    }

    public void removeEmergencyContact(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("deleteEmergencyContactId");
            int id = Integer.parseInt(param.getValue().toString());

            emergencyContactBean.remove(id);
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
                    newFaq.getAnswer(),
                    newFaq.getAnswer()
            );
            
            setFilteredFaqs(null);

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
                    currentFaq.getQuestion(),
                    currentFaq.getAnswer());
            
            setFilteredFaqs(null);

            return "healthcarePro_index?faces-redirect=true";
        } catch (Exception e) {
            LOGGER.warning("Error: problem in method updateFaq");
        }

        return "healthcarePro_faq_update?faces-redirect=true";
    }

    public void removeFaq(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("deleteFaqId");
            int id = Integer.parseInt(param.getValue().toString());

            faqBean.remove(id);
        } catch (Exception e) {
            LOGGER.warning("Error: problem in method removeFaq");
        }
    }

    // ***************************************
    // ************ PROCEDURE ****************
    // ***************************************
    public String createProcedure() {
        try {

            procedureBean.create(newProcedure.getText());

            setFilteredProcedures(null);
            
            newProcedure.reset();

            return "healthcarePro_index?faces-redirect=true";
        } catch (Exception e) {
            LOGGER.warning("Error: problem in method createProcedure");
        }

        return "healthcarePro_procedures_create?faces-redirect=true";
    }

    public List<ProcedureDTO> getAllProcedures() {
        try {
            return procedureBean.getAll();
        } catch (Exception e) {
            LOGGER.warning("Error: problem in method getAllProcedures");
        }

        return null;
    }

    public String updateProcedure() {
        try {

            procedureBean.update(
                    currentProcedure.getId(),
                    currentProcedure.getText()
            );
            
            setFilteredProcedures(null);

            return "healthcarePro_index?faces-redirect=true";
        } catch (Exception e) {
            LOGGER.warning("Error: problem in method updateProcedure");
        }

        return "healthcarePro_procedures_update?faces-redirect=true";
    }

    public void removeProcedure(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("deleteProcedureId");
            int id = Integer.parseInt(param.getValue().toString());

            procedureBean.remove(id);
        } catch (Exception e) {
            LOGGER.warning("Error: problem in method removeProcedure");
        }
    }
    
    // **********************************
    // ************ TEXT ****************
    // **********************************
    public String createText() {
        try {
            
            textBean.create(
                    newText.getText()
            );
            
            setFilteredTexts(null);

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
                    currentText.getText()
            );
            
            setFilteredTexts(null);
            
            return "healthcarePro_index?faces-redirect=true";
        } catch (Exception e) {
            LOGGER.warning("Error: problem in method updateText");
        }

        return "healthcarePro_text_update?faces-redirect=true";
    }

    public void removeText(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("deleteTextId");
            int id = Integer.parseInt(param.getValue().toString());

            textBean.remove(id);
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
                    newVideo.getUrl()
            );

            setFilteredVideos(null);
            
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
                    currentVideo.getUrl()
            );
            
            setFilteredVideos(null);

            return "healthcarePro_index?faces-redirect=true";
        } catch (Exception e) {
            LOGGER.warning("Error: problem in method updateVideo");
        }

        return "healthcarePro_video_update?faces-redirect=true";
    }

    public void removeVideo(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("deleteVideoId");
            int id = Integer.parseInt(param.getValue().toString());

            videoBean.remove(id);
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

    public ProcedureDTO getNewProcedure() {
        return newProcedure;
    }

    public void setNewProcedure(ProcedureDTO newProcedure) {
        this.newProcedure = newProcedure;
    }

    public ProcedureDTO getCurrentProcedure() {
        return currentProcedure;
    }

    public void setCurrentProcedure(ProcedureDTO currentProcedure) {
        this.currentProcedure = currentProcedure;
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
    
    public List<EmergencyContactDTO> getFilteredEmergencyContacts() {
        return filteredEmergencyContacts;
    }

    public void setFilteredEmergencyContacts(List<EmergencyContactDTO> filteredEmergencyContacts) {
        this.filteredEmergencyContacts = filteredEmergencyContacts;
    }

    public List<FaqDTO> getFilteredFaqs() {
        return filteredFaqs;
    }

    public void setFilteredFaqs(List<FaqDTO> filteredFaqs) {
        this.filteredFaqs = filteredFaqs;
    }

    public List<ProcedureDTO> getFilteredProcedures() {
        return filteredProcedures;
    }

    public void setFilteredProcedures(List<ProcedureDTO> filteredProcedures) {
        this.filteredProcedures = filteredProcedures;
    }

    public List<TextDTO> getFilteredTexts() {
        return filteredTexts;
    }

    public void setFilteredTexts(List<TextDTO> filteredTexts) {
        this.filteredTexts = filteredTexts;
    }

    public List<VideoDTO> getFilteredVideos() {
        return filteredVideos;
    }

    public void setFilteredVideos(List<VideoDTO> filteredVideos) {
        this.filteredVideos = filteredVideos;
    }
    
    public UIComponent getComponent() {
        return component;
    }

    public void setComponent(UIComponent component) {
        this.component = component;
    }
    
}
