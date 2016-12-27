/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.AdministratorDTO;
import dtos.VideoDTO;
import entities.Administrator;
import entities.Video;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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

    public List<VideoDTO> getAll() {
        try {
            List<Video> videos = (List<Video>) em.createNamedQuery("getAllVideos").getResultList();

            return videosToDTOs(videos);
        } catch (EJBException e) {
            throw new EJBException(e.getMessage());
        }
    }

    @GET
    @RolesAllowed({"Caregiver"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("all")
    public List<VideoDTO> getAllREST() {
        try {
            List<Video> videos = (List<Video>) em.createNamedQuery("getAllVideos").getResultList();

            return videosToDTOs(videos);
        } catch (EJBException e) {
            throw new EJBException(e.getMessage());
        }
    }

    VideoDTO videoToDTO(Video video) {
        return new VideoDTO(
                video.getId(),
                video.getUrl()
        );
    }

    List<VideoDTO> videosToDTOs(List<Video> videos) {
        List<VideoDTO> dtos = new ArrayList<>();
        for (Video v : videos) {
            dtos.add(videoToDTO(v));
        }
        return dtos;
    }

}
