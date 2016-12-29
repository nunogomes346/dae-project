package ejbs;

import dtos.CareProcedureDTO;
import entities.Caregiver;
import entities.Material;
import entities.Patient;
import entities.CareProcedure;
import exceptions.EntityDoesNotExistException;
import exceptions.MyConstraintViolationException;
import exceptions.Utils;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

@Stateless
@Path("/care_procedures")
public class CareProcedureBean {

    @PersistenceContext
    EntityManager em;

    //Material material, Patient patient, Caregiver caregiver, String date
    public void create(int materialID, Long patientID, String caregiverUsernameID, String date)
            throws MyConstraintViolationException {
        try {

            CareProcedure careProcedure
                    = new CareProcedure(
                            this.findMaterial(materialID),
                            this.findPatient(patientID),
                            this.findCaregiver(caregiverUsernameID),
                            date
                    );

            em.persist(careProcedure);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void remove(long id) throws EntityDoesNotExistException {
        try {
            CareProcedure careProcedure = em.find(CareProcedure.class, id);

            if (careProcedure == null) {
                throw new EntityDoesNotExistException("There is no careProcedure with that id.");
            }

            em.remove(careProcedure);
        } catch (EntityDoesNotExistException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    @POST
    @RolesAllowed({"Caregiver"})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("create")
    public void createREST(CareProcedureDTO c) {
        try {
            create(this.findMaterial(c.getMaterialID()).getId(),
                    this.findPatient(c.getPatientID()).getId(),
                    this.findCaregiver(c.getCaregiverUsername()).getUsername(),
                    c.getDate()
            );
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    @DELETE
    @RolesAllowed({"Caregiver"})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("delete/{id}")
    public void removeREST(@PathParam("id") long id) {
        try {
            remove(id);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public CareProcedureDTO getCareProcedure(int id) {
        try {
            CareProcedure careProcedure = em.find(CareProcedure.class, id);

            return careProcedureToDTO(careProcedure);
        } catch (EJBException e) {
            throw new EJBException(e.getMessage());
        }
    }

    //Build DTOs
    public CareProcedureDTO careProcedureToDTO(CareProcedure careProcedure) {
        return new CareProcedureDTO(
                careProcedure.getId(),
                careProcedure.getMaterial().getId(),
                careProcedure.getMaterial().getDescription(),
                careProcedure.getPatient().getId(),
                careProcedure.getPatient().getName(),
                careProcedure.getCaregiver().getUsername(),
                careProcedure.getCaregiver().getName(),
                careProcedure.getDateProcedure()
        );
    }

    public List<CareProcedureDTO> careProceduresToDTOs(List<CareProcedure> careProcedures) {
        List<CareProcedureDTO> dtos = new ArrayList<>();
        for (CareProcedure c : careProcedures) {
            dtos.add(careProcedureToDTO(c));
        }
        return dtos;
    }

    // Helpers
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

    public Patient findPatient(Long id) throws EntityDoesNotExistException {
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

    private Caregiver findCaregiver(String caregiverUsernameID) throws EntityDoesNotExistException {
        try {
            Caregiver caregiver = em.find(Caregiver.class, caregiverUsernameID);

            if (caregiver == null) {
                throw new EntityDoesNotExistException("There is no caregiver with that username.");
            }

            return caregiver;
        } catch (EntityDoesNotExistException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

}
