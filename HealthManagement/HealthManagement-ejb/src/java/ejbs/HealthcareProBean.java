
package ejbs;

import dtos.HealthcareProDTO;
import entities.HealthcarePro;
import exceptions.EntityAlreadyExistsException;
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
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Stateless
@Path("/healthcarepros")
public class HealthcareProBean {

    @PersistenceContext
    EntityManager em;
    
    public void create(String username, String password, String name, String mail) 
            throws EntityAlreadyExistsException, MyConstraintViolationException {
        try {
            if (em.find(HealthcarePro.class, username) != null) {
                throw new EntityAlreadyExistsException("A healthcarePro with that username already exists.");
            }
            HealthcarePro healthcarepro = new HealthcarePro(username, password, name, mail);

            em.persist(healthcarepro);
        } catch (EntityAlreadyExistsException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public void remove(String username) throws EntityDoesNotExistException {
        try {
            HealthcarePro healthcarePro = em.find(HealthcarePro.class, username);
            
            if (healthcarePro == null) {
                throw new EntityDoesNotExistException("There is no healthcarePro with that username.");
            }
            
            em.remove(healthcarePro);
        } catch (EntityDoesNotExistException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public void update(String username, String password, String name, String mail) 
            throws EntityDoesNotExistException, MyConstraintViolationException {
        try {
            HealthcarePro healthcarePro = em.find(HealthcarePro.class, username);
            if (healthcarePro == null) {
                throw new EntityDoesNotExistException("There is no healthcarePro with that username.");
            }
            
            healthcarePro.setPassword(password);
            healthcarePro.setName(name);
            healthcarePro.setMail(mail);
            
            em.merge(healthcarePro);
        } catch (EntityDoesNotExistException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));            
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public List<HealthcareProDTO> getAll() {
        try {
            List<HealthcarePro> healthcarePros = (List<HealthcarePro>) em.createNamedQuery("getAllHealthcarePros").getResultList();
            
            return HealthcareprosToDTOs(healthcarePros);  
        } catch(EJBException e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public HealthcareProDTO getHealthcarePro(String username) {
        try {
            HealthcarePro healthcarePro = em.find(HealthcarePro.class, username);
            
            return healthcareproToDTO(healthcarePro);  
        } catch(EJBException e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    //Service
    @POST
    @RolesAllowed({"Administrator"})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("create")
    public void createREST(HealthcareProDTO h) {
        try {
            create(h.getUsername(), h.getPassword(), h.getName(), h.getMail());
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @PUT
    @RolesAllowed({"Administrator"})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("update")
    public void updateREST(HealthcareProDTO h) {
        try {
            update(h.getUsername(), h.getPassword(), h.getName(), h.getMail()); 
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @GET
    @RolesAllowed({"Administrator"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("all")
    public List<HealthcareProDTO> getAllREST() {
        try {
            List<HealthcarePro> healthcarePros = (List<HealthcarePro>) em.createNamedQuery("getAllHealthcarePros").getResultList();
            
            return HealthcareprosToDTOs(healthcarePros);   
        } catch(EJBException e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @DELETE
    @RolesAllowed({"Administrator"})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("delete/{username}")
    public void removeREST(@PathParam("username") String username) {
        try {
            remove(username);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    //Build DTOs
    HealthcareProDTO healthcareproToDTO(HealthcarePro healthcarePro) {
        return new HealthcareProDTO(
                healthcarePro.getUsername(),
                null,
                healthcarePro.getName(),
                healthcarePro.getMail());
    }
    
    
    List<HealthcareProDTO> HealthcareprosToDTOs(List<HealthcarePro> healthcarePros) {
        List<HealthcareProDTO> dtos = new ArrayList<>();
        for (HealthcarePro h : healthcarePros) {
            dtos.add(healthcareproToDTO(h));
        }
        return dtos;
    }
}
