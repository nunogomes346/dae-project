/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import entities.MaterialGroup.GROUP;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
import static javax.persistence.FetchType.EAGER;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author joaoc
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "getAllTexts",
    query = "SELECT t FROM Text t ORDER BY t.id")
})
public class Text extends Material implements Serializable {
    
    @Lob @Basic(fetch=EAGER)
    private String text;
    
    public Text(){
    }
    
    public Text(String text){
        super(GROUP.Text);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
    
}
