package web;

import dtos.AdministratorDTO;
import dtos.EmergencyContactDTO;
import dtos.FaqDTO;
import dtos.HealthcareProDTO;
import dtos.ProcedureDTO;
import dtos.TextDTO;
import dtos.VideoDTO;
import ejbs.AdministratorBean;
import ejbs.EmergencyContactBean;
import ejbs.FaqBean;
import ejbs.HealthcareProBean;
import ejbs.ProcedureBean;
import ejbs.TextBean;
import ejbs.VideoBean;
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
@Named(value = "administratorManager")
@SessionScoped
public class AdministratorManager implements Serializable {

    @EJB
    private AdministratorBean administratorBean;

    @EJB
    private HealthcareProBean healthcareProBean;

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

    private AdministratorDTO newAdministrator;
    private AdministratorDTO currentAdministrator;
    private HealthcareProDTO newHealthcarePro;
    private HealthcareProDTO currentHealthcarePro;

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

    public UIComponent getComponent() {
        return component;
    }

    public void setComponent(UIComponent component) {
        this.component = component;
    }

    /*
        Materials
     */
    public String createEmergencyContact() {
        try {
            emergencyContactBean.create(
                    newEmergencyContact.getName(),
                    newEmergencyContact.getTelephoneNumber());

            newEmergencyContact.reset();

            return "admin_index?faces-redirect=true";
        } catch (Exception e) {
            LOGGER.warning("Error: problem in method createEmergencyContact");
        }

        return "admin_administrator_create?faces-redirect=true";
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

            return "admin_index?faces-redirect=true";
        } catch (Exception e) {
            LOGGER.warning("Error: problem in method updateEmergencyContact");
        }

        return "admin_administrator_update?faces-redirect=true";
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

    /* Faq */
    public String createFaq() {
        try {
            faqBean.create(
                    newFaq.getAnswer(),
                    newFaq.getAnswer()
            );

            newFaq.reset();

            return "admin_index?faces-redirect=true";
        } catch (Exception e) {
            LOGGER.warning("Error: problem in method createFAQ");
        }

        return "admin_administrator_create?faces-redirect=true";
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

            return "admin_index?faces-redirect=true";
        } catch (Exception e) {
            LOGGER.warning("Error: problem in method updateFaq");
        }

        return "admin_administrator_update?faces-redirect=true";
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

    public String procedureCreate() {
        try {

            procedureBean.create(newProcedure.getText());

            newProcedure.reset();

            return "admin_index?faces-redirect=true";
        } catch (Exception e) {
            LOGGER.warning("Error: problem in method createProcedure");
        }

        return "admin_administrator_create?faces-redirect=true";
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

            return "admin_index?faces-redirect=true";
        } catch (Exception e) {
            LOGGER.warning("Error: problem in method updateProcedure");
        }

        return "admin_administrator_update?faces-redirect=true";
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
    
    /* Text */
    public String createText() {
        try {
            
            textBean.create(
                    newText.getText()
            );

            newText.reset();

            return "admin_index?faces-redirect=true";
        } catch (Exception e) {
            LOGGER.warning("Error: problem in method createText");
        }

        return "admin_administrator_create?faces-redirect=true";
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
            
            return "admin_index?faces-redirect=true";
        } catch (Exception e) {
            LOGGER.warning("Error: problem in method updateText");
        }

        return "admin_administrator_update?faces-redirect=true";
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

    /* Video */
    public String createVideo() {
        try {
     
            videoBean.create(
                    newVideo.getUrl()
            );

            newVideo.reset();

            return "admin_index?faces-redirect=true";
        } catch (Exception e) {
            LOGGER.warning("Error: problem in method createVideo");
        }

        return "admin_administrator_create?faces-redirect=true";
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

            return "admin_index?faces-redirect=true";
        } catch (Exception e) {
            LOGGER.warning("Error: problem in method updateVideo");
        }

        return "admin_administrator_update?faces-redirect=true";
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

}
