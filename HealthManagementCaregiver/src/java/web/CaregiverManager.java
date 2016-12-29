package web;

import dtos.CaregiverDTO;
import dtos.PatientDTO;
import dtos.ProceedingDTO;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

@ManagedBean
@Named(value="caregiverManager")
@SessionScoped
public class CaregiverManager implements Serializable {

    @Inject
    private UserManager userManager;
    
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
    // ************ CAREGIVER ************
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
    
    public List<ProceedingDTO> getPatientsProceedingsREST() {
        List<ProceedingDTO> returnedProceeding = null;
        try {
            returnedProceeding = client.target(baseUri)
                .path("/patients/{patientId}/proceedings")
                .resolveTemplate("patientId", currentPatient.getId())
                .request(MediaType.APPLICATION_XML)
                .get(new GenericType<List<ProceedingDTO>>() {});
        } catch (Exception e){
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", LOGGER);
        }
        return returnedProceeding;
    }
    
    public void removeProceedingREST(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("deleteProceedingId");
            Long proceedingId = Long.parseLong(param.getValue().toString());
            
            client.target(baseUri)
                    .path("/proceedings/{proceedingId}")
                    .resolveTemplate("proceedingId", proceedingId)
                    .request().delete();

        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", LOGGER);
        }
    }

    
    // **********************************
    // ************ GETS&SETS *******
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
}
