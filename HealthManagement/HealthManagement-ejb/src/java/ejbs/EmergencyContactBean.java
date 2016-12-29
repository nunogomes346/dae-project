package ejbs;

import dtos.EmergencyContactDTO;
import entities.EmergencyContact;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class EmergencyContactBean {

    @PersistenceContext
    EntityManager em;

    /* Mesmo parametros */
    public void create(String description, String name, String telephoneNumber) {
        try {
            EmergencyContact emergencyContact = new EmergencyContact(description, name, telephoneNumber);

            em.persist(emergencyContact);
        } catch (EJBException e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void update(int id, String description, String name, String telephoneNumber) {
        try {
            EmergencyContact emergencyContact = em.find(EmergencyContact.class, id);
            if (emergencyContact == null) {
                return;
            }
            
            emergencyContact.setDescription(description);
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

    //Build DTOs
    public EmergencyContactDTO emergencyContactToDTO(EmergencyContact emergencyContact) {
        return new EmergencyContactDTO(
                emergencyContact.getId(),
                emergencyContact.getDescription(),
                emergencyContact.getName(),
                emergencyContact.getTelephoneNumber()
        );
    }

    public List<EmergencyContactDTO> emergencyContactsToDTOs(List<EmergencyContact> emergencyContacts) {
        List<EmergencyContactDTO> dtos = new ArrayList<>();
        for (EmergencyContact ec : emergencyContacts) {
            dtos.add(emergencyContactToDTO(ec));
        }
        return dtos;
    }

}
