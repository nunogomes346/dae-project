/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import entities.EmergencyContact;
import entities.FAQ;
import entities.Video;
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
public class VideoBean {

    @PersistenceContext
    EntityManager em;

    /* Mesmo parametros */
    public void create(String url) {
        try {
            Video video = new Video(url);

            em.persist(video);
        } catch (EJBException e) {
            throw new EJBException(e.getMessage());
        }

    }

    public void update(int id, String url) {
        try {
            Video video = em.find(Video.class, id);
            if (video == null) {
                return;
            }

            video.setUrl(url);

            em.merge(video);
        } catch (EJBException e) {
            throw new EJBException(e.getMessage());
        }
    }

    public Video getVideo(int id) {
        try {
            Video video = em.find(Video.class, id);

            return video;
        } catch (EJBException e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void remove(int id) {
        try {
            Video video = em.find(Video.class, id);

            em.remove(video);
        } catch (EJBException e) {
            throw new EJBException(e.getMessage());
        }
    }

    public List<Video> getAll() {
        try {
            List<Video> videos = (List<Video>) em.createNamedQuery("getAllVideos").getResultList();

            return videos;
        } catch (EJBException e) {
            throw new EJBException(e.getMessage());
        }
    }
}