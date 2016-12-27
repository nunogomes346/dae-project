package dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "EmergencyContact")
@XmlAccessorType(XmlAccessType.FIELD)
public class EmergencyContactDTO extends MaterialDTO {
    
    private String name;
    private String telephoneNumber;
    
    public EmergencyContactDTO(){ }
    
    public EmergencyContactDTO(int id, String name, String telephoneNumber) {
        super(id);
        this.name = name;
        this.telephoneNumber = telephoneNumber;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }
    
    public void reset() {
        super.reset();
        setName(null);
        setTelephoneNumber(null);
    }
}
