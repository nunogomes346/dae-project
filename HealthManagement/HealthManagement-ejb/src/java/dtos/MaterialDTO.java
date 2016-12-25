/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;
import java.io.Serializable;

/**
 *
 * @author sphinx
 */
public abstract class MaterialDTO implements Serializable{
    
    private int id;
    
    public MaterialDTO() { }
    
    public MaterialDTO(int id) { 
        this.id = id;
    }
    
    public abstract void reset();
    
    public int getId(){
        return this.id;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
}
