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
 * @author joaoc
 */
@XmlRootElement(name = "Patient")
@XmlAccessorType(XmlAccessType.FIELD)
public class PatientDTO extends UserDTO {

    public PatientDTO(String username, String password, String name, String mail) {
        super(username, password, name, mail);
    }
    
    @Override
    public void reset() {
        super.reset();
    }
    
}
