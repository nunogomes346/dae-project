package ejbs;

import dtos.TextDTO;
import entities.Text;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class TextBean {

    @PersistenceContext
    EntityManager em;

    /* Mesmo parametros */
    public void create(String description, String textContent) {
        try {
            Text text = new Text(description, textContent);

            em.persist(text);
        } catch (EJBException e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void update(int id, String description, String textContent) {
        try {
            Text text = em.find(Text.class, id);
            if (text == null) {
                return;
            }

            text.setDescription(description);
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

    //Build DTOs
    public TextDTO textToDTO(Text text) {
        return new TextDTO(
                text.getId(),
                text.getDescription(),
                text.getText()
        );
    }

    public List<TextDTO> textsToDTOs(List<Text> texts) {
        List<TextDTO> dtos = new ArrayList<>();
        for (Text t : texts) {
            dtos.add(textToDTO(t));
        }
        return dtos;
    }

}
