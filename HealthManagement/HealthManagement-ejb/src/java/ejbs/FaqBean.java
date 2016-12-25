/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.FaqDTO;
import entities.FAQ;
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
public class FaqBean {

    @PersistenceContext
    EntityManager em;

    /* Mesmo parametros */
    public void create(String question, String answer) {
        try {
            FAQ faq = new FAQ(question,answer);

            em.persist(faq);
        } catch(EJBException e) {
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

    
    FaqDTO faqToDTO(FAQ faq) {        
        return new FaqDTO(
                faq.getId(),
                faq.getAnswer(),
                faq.getQuestion()
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
