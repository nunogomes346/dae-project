package dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Proceeding")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProceedingDTO {

    private Long proceedingId;
    private int materialID;
    private String materialDescription;
    private Long patientID;
    private String patientName;
    private String caregiverUsername;
    private String proceedingDate;

    public ProceedingDTO() { }
    
    public ProceedingDTO(Long proceedingId, int materialID, String materialDescription, Long patientID, 
            String patientName, String caregiverUsername, String proceedingDate) {
        this.proceedingId = proceedingId;
        this.materialID = materialID;
        this.materialDescription = materialDescription;
        this.patientID = patientID;
        this.patientName = patientName;
        this.caregiverUsername = caregiverUsername;
        this.proceedingDate = proceedingDate;
    }

    public Long getProceedingId() {
        return proceedingId;
    }

    public void setProceedingId(Long proceedingId) {
        this.proceedingId = proceedingId;
    }

    public int getMaterialID() {
        return materialID;
    }

    public void setMaterialID(int materialID) {
        this.materialID = materialID;
    }

    public String getMaterialDescription() {
        return materialDescription;
    }

    public void setMaterialDescription(String materialDescription) {
        this.materialDescription = materialDescription;
    }

    public Long getPatientID() {
        return patientID;
    }

    public void setPatientID(Long patientID) {
        this.patientID = patientID;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getCaregiverUsername() {
        return caregiverUsername;
    }

    public void setCaregiverUsername(String caregiverUsername) {
        this.caregiverUsername = caregiverUsername;
    }

    public String getProceedingDate() {
        return proceedingDate;
    }

    public void setProceedingDate(String proceedingDate) {
        this.proceedingDate = proceedingDate;
    }

    public void reset() {
        setProceedingId(null);
        setMaterialID(0);
        setMaterialDescription(null);
        setPatientID(null);
        setPatientName(null);
        setCaregiverUsername(null);
        setProceedingDate(null);
    }
}
