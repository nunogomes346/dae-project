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
    @NamedQuery(name = "getAllProcedures",
    query = "SELECT p FROM Procedure p ORDER BY p.id")
})
public class Procedure extends Material implements Serializable {
    
    @Lob @Basic(fetch=EAGER)
    private String text;

    public Procedure(){
    }
    
    public Procedure(String text) {
        super(GROUP.Procedure);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
  
}
