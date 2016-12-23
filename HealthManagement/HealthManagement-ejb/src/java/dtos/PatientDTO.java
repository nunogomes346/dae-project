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
public class PatientDTO {
    
    protected String name;
    protected String mail;
    protected String caregiverUsername;
    
    public PatientDTO() {
    }

    public PatientDTO(String name, String mail, String caregiverUsername) {
        this.name = name;
        this.mail = mail;
        this.caregiverUsername = caregiverUsername;
    }
    
    public void reset() {
        setName(null);
        setMail(null);
    }

    public String getCaregiverUsername() {
        return caregiverUsername;
    }

    public void setCaregiverUsername(String caregiverUsername) {
        this.caregiverUsername = caregiverUsername;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
    
    
}
