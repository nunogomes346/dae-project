/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import entities.Caregiver;
import entities.EmergencyContact;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author sphinx
 */
@Stateless
public class EmergencyContactBean {

    @PersistenceContext
    EntityManager em;

    /* Mesmo parametros */
    public void create(String name, int telephoneNumber) {
        try {
            EmergencyContact emergencyContact = new EmergencyContact(name, telephoneNumber);

            em.persist(emergencyContact);
        } catch(EJBException e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void update(int id, String name, int telephoneNumber) {
        try {
            EmergencyContact emergencyContact = em.find(EmergencyContact.class, id);
            if (emergencyContact == null) {
                return;
            }
            emergencyContact.setName(name);
            emergencyContact.setTelephoneNumber(telephoneNumber);
            
            em.merge(emergencyContact);
        } catch(EJBException e) {
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
        } catch(EJBException e) {
            throw new EJBException(e.getMessage());
        }
    }

    public List<EmergencyContact> getAll() {
        try {
            List<EmergencyContact> emergencyContacts = (List<EmergencyContact>) em.createNamedQuery("getAllEmergencyContacts").getResultList();
            
            return emergencyContacts;  
        } catch(EJBException e) {
            throw new EJBException(e.getMessage());
        }
    }

}
