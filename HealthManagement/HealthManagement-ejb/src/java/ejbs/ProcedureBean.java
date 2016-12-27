package ejbs;

import dtos.ProcedureDTO;
import dtos.VideoDTO;
import entities.Procedure;
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
public class ProcedureBean {

    @PersistenceContext
    EntityManager em;

    /* Mesmo parametros */
    public void create(String text) {
        try {
            Procedure procedure = new Procedure(text);

            em.persist(procedure);
        } catch (EJBException e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void update(int id, String text) {
        try {
            Procedure procedure = em.find(Procedure.class, id);
            if (procedure == null) {
                return;
            }

            procedure.setText(text);

            em.merge(procedure);
        } catch (EJBException e) {
            throw new EJBException(e.getMessage());
        }
    }

    public Procedure getProcedure(int id) {
        try {
            Procedure procedure = em.find(Procedure.class, id);

            return procedure;
        } catch (EJBException e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void remove(int id) {
        try {
            Procedure procedure = em.find(Procedure.class, id);

            em.remove(procedure);
        } catch (EJBException e) {
            throw new EJBException(e.getMessage());
        }
    }

    /* Criar um rest */
    public List<ProcedureDTO> getAll() {
        try {
            List<Procedure> procedures = (List<Procedure>) em.createNamedQuery("getAllProcedures").getResultList();

            return proceduresToDTOs(procedures);
        } catch (EJBException e) {
            throw new EJBException(e.getMessage());
        }
    }

    @GET
    @RolesAllowed({"Caregiver"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("all")
    public List<ProcedureDTO> getAllREST() {
        try {
            List<Procedure> procedures = (List<Procedure>) em.createNamedQuery("getAllProcedures").getResultList();

            return proceduresToDTOs(procedures);
        } catch (EJBException e) {
            throw new EJBException(e.getMessage());
        }
    }

    //Build DTOs
    ProcedureDTO procedureToDTO(Procedure procedure) {
        return new ProcedureDTO(
                procedure.getId(),
                procedure.getText()
        );
    }

    List<ProcedureDTO> proceduresToDTOs(List<Procedure> procedures) {
        List<ProcedureDTO> dtos = new ArrayList<>();
        for (Procedure p : procedures) {
            dtos.add(procedureToDTO(p));
        }
        return dtos;
    }

}
