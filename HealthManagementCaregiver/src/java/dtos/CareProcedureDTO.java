package dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "CareProcedure")
@XmlAccessorType(XmlAccessType.FIELD)
public class CareProcedureDTO {
    
    /*
    ID DA NECESSIDADE
    +
    INFORMACAO DA NECESSIDADE
    */
    private Long careProcedureId;
    
    private int materialID;
    private String materialDescription;
    
    private Long patientID;
    private String patientName;

    private String caregiverUsername;
    private String caregiverName;
    
    private String date;

    public CareProcedureDTO() { }

    public CareProcedureDTO(Long careProcedureId, int materialID, String materialDescription, Long patientID, 
            String patientName, String caregiverUsername, String caregiverName, String date) {
        this.careProcedureId = careProcedureId;
        this.materialID = materialID;
        this.materialDescription = materialDescription;
        this.patientID = patientID;
        this.patientName = patientName;
        this.caregiverUsername = caregiverUsername;
        this.caregiverName = caregiverName;
        this.date = date;
    }

    public Long getCareProcedureId() {
        return careProcedureId;
    }

    public void setCareProcedureId(Long careProcedureId) {
        this.careProcedureId = careProcedureId;
    }
    
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void reset() {
        setDate(null);
    }

    public int getMaterialID() {
       return materialID;
    }

    public Long getPatientID() {
        return patientID;
    }

    public String getCaregiverUsername() {
        return caregiverUsername;
    }

    public void setMaterialID(int materialID) {
        this.materialID = materialID;
    }

    public void setPatientID(Long patientID) {
        this.patientID = patientID;
    }

    public void setCaregiverUsername(String caregiverUsername) {
        this.caregiverUsername = caregiverUsername;
    }

    public String getMaterialDescription() {
        return materialDescription;
    }

    public void setMaterialDescription(String materialDescription) {
        this.materialDescription = materialDescription;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getCaregiverName() {
        return caregiverName;
    }

    public void setCaregiverName(String caregiverName) {
        this.caregiverName = caregiverName;
    }
    
}
