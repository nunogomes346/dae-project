package entities;

import entities.MaterialGroup.GROUP;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
    @NamedQuery(name = "getAllVideos",
    query = "SELECT v FROM Video v ORDER BY v.id")
})
public class Video extends Material implements Serializable {

    private String url;
    
    public Video(){
    }
    
    public Video(String url){
        super(GROUP.Video);
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    
}
