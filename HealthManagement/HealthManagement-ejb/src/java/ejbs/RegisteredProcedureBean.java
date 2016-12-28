package ejbs;

import dtos.PatientDTO;
import dtos.RegisteredProcedureDTO;
import entities.Caregiver;
import entities.Material;
import entities.Patient;
import entities.RegisteredProcedure;
import exceptions.CaregiverAssociatedException;
import exceptions.CaregiverDiassociatedException;
import exceptions.EntityDoesNotExistException;
import exceptions.MyConstraintViolationException;
import exceptions.Utils;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;

@Stateless
public class RegisteredProcedureBean {

    @PersistenceContext
    EntityManager em;

    //Material material, Patient patient, Caregiver caregiver, String date
    public void create(int materialID, int patientID, int caregiverID, String date)
            throws MyConstraintViolationException {
        try {

            RegisteredProcedure registeredProcedure
                    = new RegisteredProcedure(
                            this.findMaterial(materialID),
                            this.findPatient(patientID),
                            this.findCaregiver(caregiverID),
                            date
                    );

            em.persist(registeredProcedure);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void remove(int id) throws EntityDoesNotExistException {
        try {
            RegisteredProcedure registeredProcedure = em.find(RegisteredProcedure.class, id);

            if (registeredProcedure == null) {
                throw new EntityDoesNotExistException("There is no registeredProcedure with that id.");
            }

            em.remove(registeredProcedure);
        } catch (EntityDoesNotExistException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public List<RegisteredProcedureDTO> getAll() {
        try {
            List<RegisteredProcedure> registeredProcedures = (List<RegisteredProcedure>) em.createNamedQuery("getAllRegisteredProcedures").getResultList();

            return registeredProceduresToDTOs(registeredProcedures);
        } catch (EJBException e) {
            throw new EJBException(e.getMessage());
        }
    }

    public RegisteredProcedureDTO getRegisteredProcedure(int id) {
        try {
            RegisteredProcedure registeredProcedure = em.find(RegisteredProcedure.class, id);

            return registeredProcedureToDTO(registeredProcedure);
        } catch (EJBException e) {
            throw new EJBException(e.getMessage());
        }
    }

    //Build DTOs
    RegisteredProcedureDTO registeredProcedureToDTO(RegisteredProcedure registeredProcedure) {
        return new RegisteredProcedureDTO(
                registeredProcedure.getMaterial(),
                registeredProcedure.getPatient(),
                registeredProcedure.getCaregiver(),
                registeredProcedure.getDate()
        );
    }

    List<RegisteredProcedureDTO> registeredProceduresToDTOs(List<RegisteredProcedure> registeredProcedures) {
        List<RegisteredProcedureDTO> dtos = new ArrayList<>();
        for (RegisteredProcedure r : registeredProcedures) {
            dtos.add(registeredProcedureToDTO(r));
        }
        return dtos;
    }
    
    // helpers
    private Material findMaterial(int id) throws EntityDoesNotExistException {
        try {
            Material material = em.find(Material.class, id);

            if (material == null) {
                throw new EntityDoesNotExistException("There is no material with that id.");
            }

           return material;
        } catch (EntityDoesNotExistException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    private Patient findPatient(int id) throws EntityDoesNotExistException {
        try {
            Patient patient = em.find(Patient.class, id);

            if (patient == null) {
                throw new EntityDoesNotExistException("There is no patient with that id.");
            }

           return patient;
        } catch (EntityDoesNotExistException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    private Caregiver findCaregiver(int id) throws EntityDoesNotExistException {
        try {
            Caregiver caregiver = em.find(Caregiver.class, id);

            if (caregiver == null) {
                throw new EntityDoesNotExistException("There is no caregiver with that id.");
            }

           return caregiver;
        } catch (EntityDoesNotExistException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

}
