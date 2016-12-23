/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import static com.sun.xml.ws.security.addressing.impl.policy.Constants.logger;
import dtos.CaregiverDTO;
import dtos.HealthcareProDTO;
import dtos.PatientDTO;
import ejbs.CaregiverBean;
import ejbs.HealthcareProBean;
import ejbs.PatientBean;
import exceptions.EntityDoesNotExistException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.event.ActionEvent;

/**
 *
 * @author joaoc
 */
@ManagedBean
@Named(value="healthcareProManager")
@SessionScoped
public class HealthcareProManager implements Serializable{

    /**
     * Creates a new instance of HealthcareProManager
     */
    
    @EJB
    private HealthcareProBean healthcareProBean;
    
    @EJB
    private CaregiverBean caregiverBean;
    
    @EJB
    private PatientBean patientBean;
    
    private HealthcareProDTO newHealthcarePro;
    private HealthcareProDTO currentHealthcarePro;
    private CaregiverDTO newCaregiver;
    private CaregiverDTO currentCaregiver;
    private PatientDTO newPatient;
    private PatientDTO currentPatient;
    
    private UIComponent component;
    private static final Logger LOGGER = Logger.getLogger("web.HealthcareProManager");
    
    public HealthcareProManager() {
        newHealthcarePro = new HealthcareProDTO();
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
    
    public void enrollCaregiver(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("caregiverUsername");
            String username = param.getValue().toString();
            caregiverBean.enrollCaregiver(username, currentPatient.getName());
        } catch (EntityDoesNotExistException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
    }

    public void unrollCaregiver(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("caregiverUsername");
            String username = param.getValue().toString();
            caregiverBean.unrollCaregiver(username, currentPatient.getName());
        } catch (EntityDoesNotExistException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
    }
    
    public List<PatientDTO> getEnrolledPatients() {
        try {
            return caregiverBean.getEnrolledPatients(currentPatient.getName());
        } catch (EntityDoesNotExistException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
        return null;
    }

    public List<PatientDTO> getUnrolledPatients() {
        try {
            return caregiverBean.getUnrolledPatients(currentPatient.getName());
        } catch (EntityDoesNotExistException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
        return null;
    }
    
    /////////////// GETTERS & SETTERS ///////////////// 

    public HealthcareProDTO getNewHealthcarePro() {
        return newHealthcarePro;
    }

    public void setNewHealthcarePro(HealthcareProDTO newHealthcarePro) {
        this.newHealthcarePro = newHealthcarePro;
    }

    public HealthcareProDTO getCurrentHealthcarePro() {
        return currentHealthcarePro;
    }

    public void setCurrentHealthcarePro(HealthcareProDTO currentHealthcarePro) {
        this.currentHealthcarePro = currentHealthcarePro;
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

    public PatientDTO getNewPatient() {
        return newPatient;
    }

    public void setNewPatient(PatientDTO newPatient) {
        this.newPatient = newPatient;
    }

    public PatientDTO getCurrentPatient() {
        return currentPatient;
    }

    public void setCurrentPatient(PatientDTO currentPatient) {
        this.currentPatient = currentPatient;
    }
    
    public UIComponent getComponent() {
        return component;
    }

    public void setComponent(UIComponent component) {
        this.component = component;
    }
    
}
