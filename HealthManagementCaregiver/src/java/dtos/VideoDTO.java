package dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Video")
@XmlAccessorType(XmlAccessType.FIELD)
public class VideoDTO extends MaterialDTO {
    
    private String url;

    public VideoDTO() { }
    
    public VideoDTO(int id, String url){
        super(id);
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void reset() {
        super.reset();
        setUrl(null);
    }
    
    
}
