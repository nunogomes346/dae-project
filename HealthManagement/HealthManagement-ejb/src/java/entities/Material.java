package entities;

import entities.MaterialGroup.GROUP;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "MATERIALS")
public class Material implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id; // it was long before being int
    
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "material")
    protected MaterialGroup group;
    
    @ManyToMany(mappedBy = "materials")
    private List<Need> needs;
    
    public Material(){
        this.needs = new LinkedList<Need>();
    }
    
    public Material(GROUP group){
        this.group = new MaterialGroup(group, this);
        this.needs = new LinkedList<Need>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public List<Need> getNeeds() {
        return needs;
    }

    public void setNeeds(List<Need> needs) {
        this.needs = needs;
    }
    
    public void addNeed(Need need) {
        this.needs.add(need);
    }
    
    public void removeNeed(Need need) {
        this.needs.remove(need);
    }
}
