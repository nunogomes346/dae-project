package dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Procedure")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProcedureDTO extends MaterialDTO{
    
    private String text;

    public ProcedureDTO() { }
    
    public ProcedureDTO(int id, String text) {
        super(id);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void reset() {
        super.reset();
        setText(null);
    }
}
