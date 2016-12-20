package dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "HealthcarePro")
@XmlAccessorType(XmlAccessType.FIELD)
public class HealthcareProDTO extends UserDTO {
    
    public HealthcareProDTO() { }

    public HealthcareProDTO(String username, String password, String name, String mail) {
        super(username, password, name, mail);
    }
    
    @Override
    public void reset() {
        super.reset();
    }
}
