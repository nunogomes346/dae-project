/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import entities.Procedure;
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

    public List<Procedure> getAll() {
        try {
            List<Procedure> procedures = (List<Procedure>) em.createNamedQuery("getAllProcedures").getResultList();

            return procedures;
        } catch (EJBException e) {
            throw new EJBException(e.getMessage());
        }
    }
}
