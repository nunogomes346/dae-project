package ejbs;

import dtos.TutorialDTO;
import entities.Tutorial;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class TutorialBean {

    @PersistenceContext
    EntityManager em;

    /* Mesmo parametros */
    public void create(String description, String text) {
        try {
            Tutorial tutorial = new Tutorial(description, text);

            em.persist(tutorial);
        } catch (EJBException e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void update(int id, String description, String text) {
        try {
            Tutorial tutorial = em.find(Tutorial.class, id);
            if (tutorial == null) {
                return;
            }

            tutorial.setDescription(description);
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
    
    //Build DTOs
    TutorialDTO tutorialToDTO(Tutorial tutorial) {
        return new TutorialDTO(
                tutorial.getId(),
                tutorial.getDescription(),
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
