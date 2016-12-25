/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.TextDTO;
import entities.Text;
import java.util.ArrayList;
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
