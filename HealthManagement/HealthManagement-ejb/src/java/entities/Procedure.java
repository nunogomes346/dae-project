package entities;

import entities.MaterialGroup.GROUP;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
import static javax.persistence.FetchType.EAGER;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

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
