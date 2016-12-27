package ejbs;

import dtos.FaqDTO;
import dtos.VideoDTO;
import entities.FAQ;
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
public class FaqBean {

    @PersistenceContext
    EntityManager em;

    /* Mesmo parametros */
    public void create(String question, String answer) {
        try {
            FAQ faq = new FAQ(question, answer);

            em.persist(faq);
        } catch (EJBException e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void update(int id, String question, String answer) {
        try {
            FAQ faq = em.find(FAQ.class, id);
            if (faq == null) {
                return;
            }

            faq.setQuestion(question);
            faq.setAnswer(answer);

            em.merge(faq);
        } catch (EJBException e) {
            throw new EJBException(e.getMessage());
        }
    }

    public FAQ getFaq(int id) {
        try {
            FAQ faq = em.find(FAQ.class, id);

            return faq;
        } catch (EJBException e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void remove(int id) {
        try {
            FAQ faq = em.find(FAQ.class, id);

            em.remove(faq);
        } catch (EJBException e) {
            throw new EJBException(e.getMessage());
        }
    }

    public List<FaqDTO> getAll() {
        try {
            List<FAQ> faqs = (List<FAQ>) em.createNamedQuery("getAllFAQs").getResultList();

            return faqsToDTOs(faqs);
        } catch (EJBException e) {
            throw new EJBException(e.getMessage());
        }
    }

    @GET
    @RolesAllowed({"Caregiver"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("all")
    public List<FaqDTO> getAllREST() {
        try {
            List<FAQ> faqs = (List<FAQ>) em.createNamedQuery("getAllFaqs").getResultList();

            return faqsToDTOs(faqs);
        } catch (EJBException e) {
            throw new EJBException(e.getMessage());
        }
    }

    //Build DTOs
    FaqDTO faqToDTO(FAQ faq) {
        return new FaqDTO(
                faq.getId(),
                faq.getQuestion(),
                faq.getAnswer()
        );
    }

    List<FaqDTO> faqsToDTOs(List<FAQ> faqs) {
        List<FaqDTO> dtos = new ArrayList<>();
        for (FAQ f : faqs) {
            dtos.add(faqToDTO(f));
        }
        return dtos;
    }

}
