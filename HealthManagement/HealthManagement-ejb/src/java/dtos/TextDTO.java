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
@XmlRootElement(name = "Text")
@XmlAccessorType(XmlAccessType.FIELD)
public class TextDTO extends MaterialDTO{
        private String text;
        
        public TextDTO (int id,String text){
            super(id);
            this.text = text;
        }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void reset() {
        setText(null);
    }
        
}
