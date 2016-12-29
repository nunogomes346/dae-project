package ejbs;

import dtos.VideoDTO;
import entities.Video;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class VideoBean {

    @PersistenceContext
    EntityManager em;

    /* Mesmo parametros */
    public void create(String description, String url) {
        try {
            Video video = new Video(description, url);

            em.persist(video);
        } catch (EJBException e) {
            throw new EJBException(e.getMessage());
        }

    }

    public void update(int id, String description, String url) {
        try {
            Video video = em.find(Video.class, id);
            if (video == null) {
                return;
            }

            video.setDescription(description);
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

    public VideoDTO videoToDTO(Video video) {
        return new VideoDTO(
                video.getId(),
                video.getDescription(),
                video.getUrl()
        );
    }

    public List<VideoDTO> videosToDTOs(List<Video> videos) {
        List<VideoDTO> dtos = new ArrayList<>();
        for (Video v : videos) {
            dtos.add(videoToDTO(v));
        }
        return dtos;
    }

}
