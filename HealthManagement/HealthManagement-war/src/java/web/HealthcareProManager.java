package web;

import static com.sun.xml.ws.security.addressing.impl.policy.Constants.logger;
import dtos.CaregiverDTO;
import dtos.PatientDTO;
import ejbs.CaregiverBean;
import ejbs.PatientBean;
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
    
    private CaregiverDTO newCaregiver;
    private CaregiverDTO currentCaregiver;
    
    private UIComponent component;
    private static final Logger LOGGER = Logger.getLogger("web.HealthcareProManager");
    
    public HealthcareProManager() {
        newCaregiver = new CaregiverDTO();
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
    
    public UIComponent getComponent() {
        return component;
    }

    public void setComponent(UIComponent component) {
        this.component = component;
    }
    
}
