package web;

import dtos.AdministratorDTO;
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
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

@ManagedBean
@Named(value="administratorManager")
@SessionScoped
public class AdministratorManager implements Serializable {

    @Inject
    private UserManager userManager;
    
    private AdministratorDTO newAdministrator;
    private AdministratorDTO currentAdministrator;
    private HttpAuthenticationFeature feature;
    
    private Client client;
    private final String baseUri = "http://localhost:8080/HealthManagement-war/webapi";
    private UIComponent component;
    private static final Logger LOGGER = Logger.getLogger("web.AdministratorManager");
    
    public AdministratorManager() {
        newAdministrator = new AdministratorDTO();
        client = ClientBuilder.newClient();        
        feature = null;
    }
    
    @PostConstruct
    public void initClient() {
        feature = HttpAuthenticationFeature.basic(userManager.getUsername(), userManager.getPassword());
        client.register(feature);
    }
    
    // ***************************************
    // ************ ADMINISTRATOR ************
    // ***************************************    
    public String createAdministratorREST() { 
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
        try {
            client.target(baseUri)
                    .path("/administrators/update")
                    .request(MediaType.APPLICATION_XML).put(Entity.xml(currentAdministrator));
            
            if(currentAdministrator.getUsername().compareTo(userManager.getUsername()) == 0) {
                return userManager.logout();
            }
            
            return "admin_index?faces-redirect=true";
           
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", LOGGER);
        }
        return "admin_administrator_update";
    }
    
    public void removeAdministratorREST(ActionEvent event) {
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
