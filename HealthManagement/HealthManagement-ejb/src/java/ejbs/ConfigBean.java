package ejbs;

import exceptions.EntityAlreadyExistsException;
import exceptions.MyConstraintViolationException;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Startup
public class ConfigBean {
    
    @EJB
    AdministratorBean administratorBean;
    @EJB
    HealthcareProBean healthcareProBean;
    @EJB
    CaregiverBean caregiverBean;
    @EJB
    PatientBean patientBean;
    
    
    @PostConstruct
    public void populateDB() {
        try {          
            administratorBean.create("nunogomes", "adminpw", "Nuno Gomes", "nunogomes@gmail.com");
            administratorBean.create("joaocaroco", "adminpw", "João Caroço", "joaoc2800@gmail.com");
            administratorBean.create("pedrodomingues", "adminpw", "Pedro Domingues", "pedrolcd@outlook.com");
            
            healthcareProBean.create("teddy", "propw", "Bruno Fonseca", "teddy@gmail.com");
            
            caregiverBean.create("piteu", "carepw", "Bruno Anastácio", "piteu@gmail.com");
            caregiverBean.create("piteu2", "carepw", "Bruno Anastácio2", "piteu@gmail.com");
            caregiverBean.create("piteu3", "carepw", "Bruno Anastácio3", "piteu@gmail.com");
            
            patientBean.create("João Sousa", "sousa@gmail.com");
            patientBean.create("João Sousa1", "sousa@gmail.com");
            patientBean.create("João Sousa2", "sousa@gmail.com");
            patientBean.create("João Sousa3", "sousa@gmail.com");
            patientBean.create("João Sousa4", "sousa@gmail.com");
            patientBean.create("João Sousa5", "sousa@gmail.com");
            
        } catch (EntityAlreadyExistsException | MyConstraintViolationException e) {
            System.err.println("Error: " + e.getMessage());
        } catch(Exception e){
            System.err.println(e.getMessage());
        }
    }
    
}
