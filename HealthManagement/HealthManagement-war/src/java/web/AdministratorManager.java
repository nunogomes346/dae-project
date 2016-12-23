package web;

import dtos.AdministratorDTO;
import dtos.HealthcareProDTO;
import ejbs.AdministratorBean;
import ejbs.HealthcareProBean;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.event.ActionEvent;

@ManagedBean
@Named(value="administratorManager")
@SessionScoped
public class AdministratorManager implements Serializable {

    @EJB
    private AdministratorBean administratorBean; 
    
    @EJB
    private HealthcareProBean healthcareProBean;
    
    private AdministratorDTO newAdministrator;
    private AdministratorDTO currentAdministrator;
    private HealthcareProDTO newHealthcarePro;
    private HealthcareProDTO currentHealthcarePro;
    
    private List<AdministratorDTO> filteredAdmins;
    private List<HealthcareProDTO> filteredHealthcarePros;
    
    private UIComponent component;
    private static final Logger LOGGER = Logger.getLogger("web.AdministratorManager");
    
    public AdministratorManager() {
        newAdministrator = new AdministratorDTO();
        newHealthcarePro = new HealthcareProDTO();
    }

    // ***************************************
    // ************ ADMINISTRATOR ************
    // ***************************************
    public String createAdministrator() {
        try {
            administratorBean.create(
                    newAdministrator.getUsername(),
                    newAdministrator.getPassword(),
                    newAdministrator.getName(),
                    newAdministrator.getMail());

            newAdministrator.reset();

            return "admin_index?faces-redirect=true";
        } catch (Exception e) {
            LOGGER.warning("Error: problem in method createAdministrator");
        }

        return "admin_administrator_create?faces-redirect=true";
    }
   
    
    public List<AdministratorDTO> getAllAdministrators() {
        try {
            return administratorBean.getAll();
        } catch (Exception e) {
            LOGGER.warning("Error: problem in method getAllAdministrators");
        }

        return null;
    }
    
    public String updateAdministrator() {
        try {
            administratorBean.update(
                    currentAdministrator.getUsername(),
                    currentAdministrator.getPassword(),
                    currentAdministrator.getName(), 
                    currentAdministrator.getMail());

            return "admin_index?faces-redirect=true";
        } catch (Exception e) {
            LOGGER.warning("Error: problem in method updateAdministrator");
        }

        return "admin_administrator_update?faces-redirect=true";
    }
    
    public void removeAdministrator(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("deleteAdministratorId");
            String id = param.getValue().toString();

            administratorBean.remove(id);
        } catch (Exception e) {
            LOGGER.warning("Error: problem in method removeAdministrator");
        }
    }

    // ***************************************
    // ************ HEALTHCAREPRO ************
    // ***************************************
    public String createHealthcarePro() {
        try {
            healthcareProBean.create(
                    newHealthcarePro.getUsername(),
                    newHealthcarePro.getPassword(),
                    newHealthcarePro.getName(),
                    newHealthcarePro.getMail());

            newHealthcarePro.reset();

            return "admin_index?faces-redirect=true";
        } catch (Exception e) {
            LOGGER.warning("Error: problem in method createHealthcarePro");
        }

        return "admin_healthcarepro_create?faces-redirect=true";
    }
   
    
    public List<HealthcareProDTO> getAllHealthcarePros() {
        try {
            
            return healthcareProBean.getAll();
        } catch (Exception e) {
            LOGGER.warning("Error: problem in method getAllHealthcarePros");
        }

        return null;
    }
    
    public String updateHealthcarePro() {
        try {
            healthcareProBean.update(
                    currentHealthcarePro.getUsername(),
                    currentHealthcarePro.getPassword(),
                    currentHealthcarePro.getName(), 
                    currentHealthcarePro.getMail());

            return "admin_index?faces-redirect=true";
        } catch (Exception e) {
            LOGGER.warning("Error: problem in method updateHealthcarePro");
        }

        return "admin_healthcarepro_update?faces-redirect=true";
    }
    
    public void removeHealthcarePro(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("deleteHealthcareProId");
            String id = param.getValue().toString();

            healthcareProBean.remove(id);
        } catch (Exception e) {
            LOGGER.warning("Error: problem in method removeHealthcarePro");
        }
    }  
    
    // **********************************
    // ************ GETS&SETS *******
    // **********************************    
    public AdministratorDTO getNewAdministrator() {
        return newAdministrator;
    }

    public void setNewAdministrator(AdministratorDTO newAdministrator) {
        this.newAdministrator = newAdministrator;
    }
    
    public AdministratorDTO getCurrentAdministrator() {
        return currentAdministrator;
    }

    public void setCurrentAdministrator(AdministratorDTO currentAdministrator) {
        this.currentAdministrator = currentAdministrator;
    }

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
    
    public List<AdministratorDTO> getFilteredAdmins() {
        return filteredAdmins;
    }
        
    public void setFilteredAdmins(List<AdministratorDTO> filteredAdmins) {    
        this.filteredAdmins = filteredAdmins;
    }

    public List<HealthcareProDTO> getFilteredHealthcarePros() {
        return filteredHealthcarePros;
    }

    public void setFilteredHealthcarePros(List<HealthcareProDTO> filteredHealthcarePros) {
        this.filteredHealthcarePros = filteredHealthcarePros;
    }

    public UIComponent getComponent() {
        return component;
    }

    public void setComponent(UIComponent component) {
        this.component = component;
    }
}
