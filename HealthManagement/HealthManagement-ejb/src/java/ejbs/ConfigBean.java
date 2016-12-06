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
            
            patientBean.create("sousa", "patientpw", "João Sousa", "sousa@gmail.com");
            
        } catch (EntityAlreadyExistsException | MyConstraintViolationException e) {
            System.err.println("Error: " + e.getMessage());
        } catch(Exception e){
            System.err.println(e.getMessage());
        }
    }
    
}
