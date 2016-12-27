package ejbs;

import dtos.EmergencyContactDTO;
import dtos.VideoDTO;
import entities.EmergencyContact;
import entities.Video;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Stateless
public class EmergencyContactBean {

    @PersistenceContext
    EntityManager em;

    /* Mesmo parametros */
    public void create(String name, String telephoneNumber) {
        try {
            EmergencyContact emergencyContact = new EmergencyContact(name, telephoneNumber);

            em.persist(emergencyContact);
        } catch (EJBException e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void update(int id, String name, String telephoneNumber) {
        try {
            EmergencyContact emergencyContact = em.find(EmergencyContact.class, id);
            if (emergencyContact == null) {
                return;
            }
            emergencyContact.setName(name);
            emergencyContact.setTelephoneNumber(telephoneNumber);

            em.merge(emergencyContact);
        } catch (EJBException e) {
            throw new EJBException(e.getMessage());
        }
    }

    public EmergencyContact getEmergencyContact(int id) {
        try {
            EmergencyContact emergencyContact = em.find(EmergencyContact.class, id);

            return emergencyContact;
        } catch (EJBException e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void remove(int id) {
        try {
            EmergencyContact emergencyContact = em.find(EmergencyContact.class, id);

            em.remove(emergencyContact);
        } catch (EJBException e) {
            throw new EJBException(e.getMessage());
        }
    }

    public List<EmergencyContactDTO> getAll() {
        try {
            List<EmergencyContact> emergencyContacts = (List<EmergencyContact>) em.createNamedQuery("getAllEmergencyContacts").getResultList();

            return emergencyContactsToDTOs(emergencyContacts);
        } catch (EJBException e) {
            throw new EJBException(e.getMessage());
        }
    }

    @GET
    @RolesAllowed({"Caregiver"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("all")
    public List<EmergencyContactDTO> getAllREST() {
        try {
            List<EmergencyContact> emergencyContacts = (List<EmergencyContact>) em.createNamedQuery("getAllEmergencyContacts").getResultList();

            return emergencyContactsToDTOs(emergencyContacts);
        } catch (EJBException e) {
            throw new EJBException(e.getMessage());
        }
    }

    //Build DTOs
    EmergencyContactDTO emergencyContactToDTO(EmergencyContact emergencyContact) {
        return new EmergencyContactDTO(
                emergencyContact.getId(),
                emergencyContact.getName(),
                emergencyContact.getTelephoneNumber()
        );
    }

    List<EmergencyContactDTO> emergencyContactsToDTOs(List<EmergencyContact> emergencyContacts) {
        List<EmergencyContactDTO> dtos = new ArrayList<>();
        for (EmergencyContact ec : emergencyContacts) {
            dtos.add(emergencyContactToDTO(ec));
        }
        return dtos;
    }

}
