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
    @NamedQuery(name = "getAllTutorials",
    query = "SELECT t FROM Tutorial t ORDER BY t.id")
})
public class Tutorial extends Material implements Serializable {
    
    @Lob @Basic(fetch=EAGER)
    private String text;

    public Tutorial(){
    }
    
    public Tutorial(String text) {
        super(GROUP.Tutorial);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
  
}