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
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

@ManagedBean
@Named(value="administratorManager")
@SessionScoped
public class AdministratorManager implements Serializable {

    @EJB
    private AdministratorBean administratorBean; 
    
    @EJB
    private HealthcareProBean healthcareProBean;
    
    @Inject
    private UserManager userManager;
    
    private AdministratorDTO newAdministrator;
    private AdministratorDTO currentAdministrator;
    private HealthcareProDTO newHealthcarePro;
    private HealthcareProDTO currentHealthcarePro;
    private HttpAuthenticationFeature feature;
    
    private Client client;
    private final String baseUri = "http://localhost:8080/HealthManagement-war/webapi";
    private UIComponent component;
    private static final Logger LOGGER = Logger.getLogger("web.AdministratorManager");
    
    public AdministratorManager() {
        newAdministrator = new AdministratorDTO();
        newHealthcarePro = new HealthcareProDTO();
        client = ClientBuilder.newClient();
        feature = null;
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
    
    public String createAdministratorREST() { 
        feature = HttpAuthenticationFeature.basic(userManager.getUsername(), userManager.getPassword());
        client.register(feature);
        
        try {
            client.target(baseUri)
                    .path("/administrators/create")
                    .request(MediaType.APPLICATION_XML).post(Entity.xml(newAdministrator));
            
            newAdministrator.reset();
            
            return "admin_index?faces-redirect=true";
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", LOGGER);
        }
        return null;
    }
    
    public List<AdministratorDTO> getAllAdministratorsREST() {
        feature = HttpAuthenticationFeature.basic(userManager.getUsername(), userManager.getPassword());
        client.register(feature);
        
        List<AdministratorDTO> returnedAdministrators = null;
        try {
            returnedAdministrators = client.target(baseUri)
                .path("/administrators/all")
                .request(MediaType.APPLICATION_XML)
                .get(new GenericType<List<AdministratorDTO>>() {});
        } catch (Exception e){
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", LOGGER);
        }
        return returnedAdministrators;
    }
    
    public String updateAdministratorREST() {   
        feature = HttpAuthenticationFeature.basic(userManager.getUsername(), userManager.getPassword());
        client.register(feature);
        
        try {
           client.target(baseUri)
                    .path("/administrators/update")
                    .request(MediaType.APPLICATION_XML).put(Entity.xml(currentAdministrator));
            return "admin_index?faces-redirect=true";
           
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", LOGGER);
        }
        return "admin_administrator_update";
    }
    
    public void removeAdministratorREST(ActionEvent event) {
        feature = HttpAuthenticationFeature.basic(userManager.getUsername(), userManager.getPassword());
        client.register(feature);
        
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("deleteAdministratorId");
            String id = param.getValue().toString();
            
            client.target(baseUri)
                    .path("/administrators/delete/{username}")
                    .resolveTemplate("username", id)
                    .request().delete();

        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", LOGGER);
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
    
    public String createHealthcareProREST() { 
        feature = HttpAuthenticationFeature.basic(userManager.getUsername(), userManager.getPassword());
        client.register(feature);
        
        try {
            client.target(baseUri)
                    .path("/healthcarepros/create")
                    .request(MediaType.APPLICATION_XML).post(Entity.xml(newHealthcarePro));
            
            newHealthcarePro.reset();
            
            return "admin_index?faces-redirect=true";
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", LOGGER);
        }
        return null;
    }
    
    public List<HealthcareProDTO> getAllHealthcareProsREST() {
        feature = HttpAuthenticationFeature.basic(userManager.getUsername(), userManager.getPassword());
        client.register(feature);
        
        List<HealthcareProDTO> returnedHealthcarePros = null;
        try {
            returnedHealthcarePros = client.target(baseUri)
                .path("/healthcarepros/all")
                .request(MediaType.APPLICATION_XML)
                .get(new GenericType<List<HealthcareProDTO>>() {});
        } catch (Exception e){
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", LOGGER);
        }
        return returnedHealthcarePros;
    }
    
    public String updateHealthcareProREST() { 
        feature = HttpAuthenticationFeature.basic(userManager.getUsername(), userManager.getPassword());
        client.register(feature);
        
        try {
           client.target(baseUri)
                    .path("/healthcarepros/update")
                    .request(MediaType.APPLICATION_XML).put(Entity.xml(currentHealthcarePro));
            return "admin_index?faces-redirect=true";
           
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", LOGGER);
        }
        return "admin_healthcarepro_update";
    }
    
    public void removeHealthcareProREST(ActionEvent event) {
        feature = HttpAuthenticationFeature.basic(userManager.getUsername(), userManager.getPassword());
        client.register(feature);
        
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("deleteHealthcareProId");
            String id = param.getValue().toString();
            
            client.target(baseUri)
                    .path("/healthcarepros/delete/{username}")
                    .resolveTemplate("username", id)
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

    public UIComponent getComponent() {
        return component;
    }

    public void setComponent(UIComponent component) {
        this.component = component;
    }
}
