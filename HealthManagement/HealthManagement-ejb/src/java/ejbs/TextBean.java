package ejbs;

import dtos.TextDTO;
import dtos.VideoDTO;
import entities.Text;
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
public class TextBean {

    @PersistenceContext
    EntityManager em;

    /* Mesmo parametros */
    public void create(String textContent) {
        try {
            Text text = new Text(textContent);

            em.persist(text);
        } catch (EJBException e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void update(int id, String textContent) {
        try {
            Text text = em.find(Text.class, id);
            if (text == null) {
                return;
            }

            text.setText(textContent);

            em.merge(text);
        } catch (EJBException e) {
            throw new EJBException(e.getMessage());
        }
    }

    public Text getText(int id) {
        try {
            Text text = em.find(Text.class, id);

            return text;
        } catch (EJBException e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void remove(int id) {
        try {
            Text text = em.find(Text.class, id);

            em.remove(text);
        } catch (EJBException e) {
            throw new EJBException(e.getMessage());
        }
    }

    public List<TextDTO> getAll() {
        try {
            List<Text> texts = (List<Text>) em.createNamedQuery("getAllTexts").getResultList();

            return textsToDTOs(texts);
        } catch (EJBException e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @GET
    @RolesAllowed({"Caregiver"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("all")
    public List<TextDTO> getAllREST() {
        try {
            List<Text> texts = (List<Text>) em.createNamedQuery("getAllTexts").getResultList();
            
            return textsToDTOs(texts);
        } catch(EJBException e) {
            throw new EJBException(e.getMessage());
        }
    }

    //Build DTOs
    TextDTO textToDTO(Text text) {
        return new TextDTO(
                text.getId(),
                text.getText()
        );
    }

    List<TextDTO> textsToDTOs(List<Text> texts) {
        List<TextDTO> dtos = new ArrayList<>();
        for (Text t : texts) {
            dtos.add(textToDTO(t));
        }
        return dtos;
    }

}
