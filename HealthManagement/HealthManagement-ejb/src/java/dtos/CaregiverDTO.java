package dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Caregiver")
@XmlAccessorType(XmlAccessType.FIELD)
public class CaregiverDTO extends UserDTO {

    
    public CaregiverDTO() {
    }

    public CaregiverDTO(String username, String password, String name, String mail) {
        super(username, password, name, mail);
    }
    
    @Override
    public void reset() {
        super.reset();
    }

  
    
    
}
