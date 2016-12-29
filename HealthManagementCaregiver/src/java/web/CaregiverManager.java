package web;

import static com.sun.xml.ws.security.addressing.impl.policy.Constants.logger;
import dtos.CaregiverDTO;
import dtos.EmergencyContactDTO;
import dtos.FaqDTO;
import dtos.MaterialDTO;
import dtos.NeedDTO;
import dtos.PatientDTO;
import dtos.TextDTO;
import dtos.TutorialDTO;
import dtos.VideoDTO;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

@ManagedBean
@Named(value="caregiverManager")
@SessionScoped
public class CaregiverManager implements Serializable {

    @Inject
    private UserManager userManager;
    

    private EmergencyContactDTO currentEmergencyContact;
    private FaqDTO currentFaq;
    private TutorialDTO currentTutorial;
    private TextDTO currentText;
    private VideoDTO currentVideo;
    
    private NeedDTO currentNeed;
    private PatientDTO currentPatient;
    private CaregiverDTO newCaregiver;
    private CaregiverDTO currentCaregiver;
    private HttpAuthenticationFeature feature;
    
    private Client client;
    private final String baseUri = "http://localhost:8080/HealthManagement-war/webapi";
    private UIComponent component;
    private static final Logger LOGGER = Logger.getLogger("web.CaregiverManager");
    
    public CaregiverManager() {
        newCaregiver = new CaregiverDTO();
        client = ClientBuilder.newClient();        
        feature = null;
    }
    
    @PostConstruct
    public void initClient() {
        feature = HttpAuthenticationFeature.basic(userManager.getUsername(), userManager.getPassword());
        client.register(feature);
    }
    
    // ***************************************
    // ************ CAREGIVER ****************
    // *************************************** 

    
    public List<PatientDTO> getCaregiversPatientsREST() {
        
        
        List<PatientDTO> returnedPatients = null;
        try {
            returnedPatients = client.target(baseUri)
                .path("/caregivers/{username}/patients")
                .resolveTemplate("username", userManager.getUsername())
                .request(MediaType.APPLICATION_XML)
                .get(new GenericType<List<PatientDTO>>() {});
        } catch (Exception e){
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", LOGGER);
        }
        return returnedPatients;
    }
    

    public List<NeedDTO> getCaregiverPatientsNeedsREST() {
        
        List<NeedDTO> returnedNeeds = null;
        try {
            returnedNeeds = client.target(baseUri)
                    .path("/patients/{id}/needs")
                    .resolveTemplate("id", currentPatient.getId())
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<NeedDTO>>() {});
       } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", LOGGER);
        }
        return returnedNeeds;
    }
    
    
    public List<EmergencyContactDTO> getEmergencyContactREST() {
        List<EmergencyContactDTO> returnedEmergencyContact = null;
        try {
            returnedEmergencyContact = client.target(baseUri)
                    .path("/needs/{id}/emergencyContact")
                    .resolveTemplate("id", currentNeed.getId())
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<EmergencyContactDTO>>() {});
       } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", LOGGER);
        }    
        return returnedEmergencyContact;
    }
    
    public List<TutorialDTO> getTutorialREST() {
        List<TutorialDTO> returnedTutorial = null;
        try {
            returnedTutorial = client.target(baseUri)
                    .path("/needs/{id}/tutorial")
                    .resolveTemplate("id", currentNeed.getId())
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<TutorialDTO>>() {});
       } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", LOGGER);
        }    
        return returnedTutorial;
    }
    
    public List<VideoDTO> getVideoREST() {
        List<VideoDTO> returnedVideo = null;
        try {
            returnedVideo = client.target(baseUri)
                    .path("/needs/{id}/video")
                    .resolveTemplate("id", currentNeed.getId())
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<VideoDTO>>() {});
       } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", LOGGER);
        }    
        return returnedVideo;
    }
    
    public List<TextDTO> getTextREST() {
        List<TextDTO> returnedText = null;
        try {
            returnedText = client.target(baseUri)
                    .path("/needs/{id}/text")
                    .resolveTemplate("id", currentNeed.getId())
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<TextDTO>>() {});
       } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", LOGGER);
        }    
        return returnedText;
    }
    
    public List<FaqDTO> getFaqREST() {
        List<FaqDTO> returnedFaq = null;
        try {
            returnedFaq = client.target(baseUri)
                    .path("/needs/{id}/faq")
                    .resolveTemplate("id", currentNeed.getId())
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<FaqDTO>>() {});
       } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", LOGGER);
        }    
        return returnedFaq;
    }
    
    
    
    
    
    
        
    // **********************************
    // ************ GETS&SETS ***********
    // **********************************
    public UserManager getUserManager() {
        return userManager;
    }

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    public PatientDTO getCurrentPatient() {
        return currentPatient;
    }

    public void setCurrentPatient(PatientDTO currentPatient) {
        this.currentPatient = currentPatient;
    }

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
    
    

    public UIComponent getComponent() {
        return component;
    }

    public void setComponent(UIComponent component) {
        this.component = component;
    }

    public NeedDTO getCurrentNeed() {
        return currentNeed;
    }

    public void setCurrentNeed(NeedDTO currentNeed) {
        this.currentNeed = currentNeed;
    }

    public EmergencyContactDTO getCurrentEmergencyContact() {
        return currentEmergencyContact;
    }

    public void setCurrentEmergencyContact(EmergencyContactDTO currentEmergencyContact) {
        this.currentEmergencyContact = currentEmergencyContact;
    }

    public FaqDTO getCurrentFaq() {
        return currentFaq;
    }

    public void setCurrentFaq(FaqDTO currentFaq) {
        this.currentFaq = currentFaq;
    }

    public TutorialDTO getCurrentTutorial() {
        return currentTutorial;
    }

    public void setCurrentTutorial(TutorialDTO currentTutorial) {
        this.currentTutorial = currentTutorial;
    }

    public TextDTO getCurrentText() {
        return currentText;
    }

    public void setCurrentText(TextDTO currentText) {
        this.currentText = currentText;
    }

    public VideoDTO getCurrentVideo() {
        return currentVideo;
    }

    public void setCurrentVideo(VideoDTO currentVideo) {
        this.currentVideo = currentVideo;
    }

    
    
    
}
