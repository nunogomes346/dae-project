package ejbs;

import dtos.AdministratorDTO;
import entities.Administrator;
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
@Path("/administrators")
public class AdministratorBean {

    @PersistenceContext
    EntityManager em;
    
    public void create(String username, String password, String name, String mail) 
            throws EntityAlreadyExistsException, MyConstraintViolationException {
        try {
            if (em.find(Administrator.class, username) != null) {
                throw new EntityAlreadyExistsException("A administrator with that username already exists.");
            }
            
            Administrator administrator = new Administrator(username, password, name, mail);

            em.persist(administrator);
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
            Administrator administrator = em.find(Administrator.class, username);
            
            if (administrator == null) {
                throw new EntityDoesNotExistException("There is no administrator with that username.");
            }
            
            em.remove(administrator);
        } catch (EntityDoesNotExistException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public void update(String username, String password, String name, String mail) 
            throws EntityDoesNotExistException, MyConstraintViolationException {
        try {
            Administrator administrator = em.find(Administrator.class, username);
            if (administrator == null) {
                throw new EntityDoesNotExistException("There is no administrator with that username.");
            }
            
            administrator.setPassword(password);
            administrator.setName(name);
            administrator.setMail(mail);
            
            em.merge(administrator);
        } catch (EntityDoesNotExistException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));            
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public List<AdministratorDTO> getAll() {
        try {
            List<Administrator> administrators = (List<Administrator>) em.createNamedQuery("getAllAdministrators").getResultList();
            
            return administratorsToDTOs(administrators); 
        } catch(EJBException e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public AdministratorDTO getAdministrator(String username) {
        try {
            Administrator administrator = em.find(Administrator.class, username);
            
            return administratorToDTO(administrator);  
        } catch(EJBException e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    //Service
    @POST
    @RolesAllowed({"Administrator"})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("create")
    public void createREST(AdministratorDTO a) {
        try {
            create(a.getUsername(), a.getPassword(), a.getName(), a.getMail());
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @PUT
    @RolesAllowed({"Administrator"})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("update")
    public void updateREST(AdministratorDTO a) {
        try {
            update(a.getUsername(), a.getPassword(), a.getName(), a.getMail()); 
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @GET
    @RolesAllowed({"Administrator"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("all")
    public List<AdministratorDTO> getAllREST() {
        try {
            List<Administrator> administrators = (List<Administrator>) em.createNamedQuery("getAllAdministrators").getResultList();
            
            return administratorsToDTOs(administrators); 
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
    AdministratorDTO administratorToDTO(Administrator administrator) {
        return new AdministratorDTO(
                administrator.getUsername(),
                null,
                administrator.getName(),
                administrator.getMail());
    }
    
    List<AdministratorDTO> administratorsToDTOs(List<Administrator> administrators) {
        List<AdministratorDTO> dtos = new ArrayList<>();
        for (Administrator a : administrators) {
            dtos.add(administratorToDTO(a));
        }
        return dtos;
    }
}
