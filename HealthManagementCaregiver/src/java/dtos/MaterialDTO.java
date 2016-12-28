package dtos;
import java.io.Serializable;

public abstract class MaterialDTO implements Serializable{
    
    private int id;
    
    public MaterialDTO() { }
    
    public MaterialDTO(int id) { 
        this.id = id;
    }
    
    public void reset(){
        this.setId(0);
    };
    
    public int getId(){
        return this.id;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
}
