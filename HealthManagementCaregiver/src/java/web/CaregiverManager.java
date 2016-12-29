package web;

import dtos.CareProcedureDTO;
import dtos.CaregiverDTO;
import dtos.PatientDTO;
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
@Named(value = "caregiverManager")
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

    public void removeCareProcedureREST(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("deleteCaregiverId");
            String id = param.getValue().toString();

            client.target(baseUri)
                    .path("/care_procedures/delete/{id}") //.path("/administrators/delete/{username}")
                    .resolveTemplate("id", id)
                    .request().delete();

        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", LOGGER);
        }
    }

    public List<CareProcedureDTO> getCareProceduresFromPatientsREST() {

        List<CareProcedureDTO> returnedCareProcedures = null;
        try {
            returnedCareProcedures = client.target(baseUri)
                    .path("/patients/{id}/careProcedures")
                    .resolveTemplate("id", currentPatient.getId()) //
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<CareProcedureDTO>>() {
                    });
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", LOGGER);
        }
        return returnedCareProcedures;
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
                    .get(new GenericType<List<PatientDTO>>() {
                    });
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", LOGGER);
        }
        return returnedPatients;
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

    /*
    Consumir Rest
     */
}
