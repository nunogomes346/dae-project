/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author sphinx
 */
@XmlRootElement(name = "Video")
@XmlAccessorType(XmlAccessType.FIELD)
public class VideoDTO extends MaterialDTO {
    
    private String url;
    
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

    @Override
    public void reset() {
        setUrl(null);
    }
    
    
}
