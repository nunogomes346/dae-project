package dtos;

import entities.Caregiver;
import entities.Material;
import entities.Patient;

public class RegisteredProcedureDTO {

    private Material material;
    private Patient patient;
    private Caregiver caregiver;
    private String date;

    public RegisteredProcedureDTO() { }
    
    public RegisteredProcedureDTO(Material material, Patient patient, Caregiver caregiver, String date) {
        super();
        this.material = material;
        this.patient = patient;
        this.caregiver = caregiver;
        this.date = date;
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

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Caregiver getCaregiver() {
        return caregiver;
    }

    public void setCaregiver(Caregiver caregiver) {
        this.caregiver = caregiver;
    }
}
