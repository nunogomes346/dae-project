package ejbs;

import dtos.TutorialDTO;
import entities.Tutorial;
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
public class TutorialBean {

    @PersistenceContext
    EntityManager em;

    /* Mesmo parametros */
    public void create(String text) {
        try {
            Tutorial tutorial = new Tutorial(text);

            em.persist(tutorial);
        } catch (EJBException e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void update(int id, String text) {
        try {
            Tutorial tutorial = em.find(Tutorial.class, id);
            if (tutorial == null) {
                return;
            }

            tutorial.setText(text);

            em.merge(tutorial);
        } catch (EJBException e) {
            throw new EJBException(e.getMessage());
        }
    }

    public Tutorial getProcedure(int id) {
        try {
            Tutorial tutorial = em.find(Tutorial.class, id);

            return tutorial;
        } catch (EJBException e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void remove(int id) {
        try {
            Tutorial tutorial = em.find(Tutorial.class, id);

            em.remove(tutorial);
        } catch (EJBException e) {
            throw new EJBException(e.getMessage());
        }
    }

    /* Criar um rest */
    public List<TutorialDTO> getAll() {
        try {
            List<Tutorial> tutorials = (List<Tutorial>) em.createNamedQuery("getAllTutorials").getResultList();

            return tutorialsToDTOs(tutorials);
        } catch (EJBException e) {
            throw new EJBException(e.getMessage());
        }
    }

    @GET
    @RolesAllowed({"Caregiver"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("all")
    public List<TutorialDTO> getAllREST() {
        try {
            List<Tutorial> tutorials = (List<Tutorial>) em.createNamedQuery("getAllTutorials").getResultList();

            return tutorialsToDTOs(tutorials);
        } catch (EJBException e) {
            throw new EJBException(e.getMessage());
        }
    }

    //Build DTOs
    TutorialDTO tutorialToDTO(Tutorial tutorial) {
        return new TutorialDTO(
                tutorial.getId(),
                tutorial.getText()
        );
    }

    List<TutorialDTO> tutorialsToDTOs(List<Tutorial> tutorials) {
        List<TutorialDTO> dtos = new ArrayList<>();
        for (Tutorial t : tutorials) {
            dtos.add(tutorialToDTO(t));
        }
        return dtos;
    }

}
