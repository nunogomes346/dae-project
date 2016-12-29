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
    
    @EJB
    NeedBean needBean;
    @EJB
    ProceedingsBean procedureBean;

    @EJB
    EmergencyContactBean emergencyBean;
    @EJB
    FaqBean faqBean;
    @EJB
    TutorialBean tutorialBean;
    @EJB
    TextBean textBean;
    @EJB
    VideoBean videoBean;

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
            
            // IDs -> 1 - 6
            patientBean.create("João Sousa", "sousa@gmail.com");
            patientBean.create("João Sousa1", "sousa@gmail.com");
            patientBean.create("João Sousa2", "sousa@gmail.com");
            patientBean.create("João Sousa3", "sousa@gmail.com");
            patientBean.create("João Sousa4", "sousa@gmail.com");
            patientBean.create("João Sousa5", "sousa@gmail.com");
            
            // NEEDS: IDs -> 7 - 9
            needBean.create("Mudar sonda gastrica");
            needBean.create("Dar comprimidos po colesterol");
            needBean.create("Levar o paciente ao wc");
            
            needBean.associateNeedToPatient(Long.parseLong("7"), Long.parseLong("5"));
            needBean.associateNeedToPatient(Long.parseLong("8"), Long.parseLong("5"));
            needBean.associateNeedToPatient(Long.parseLong("9"), Long.parseLong("4"));
            needBean.associateNeedToPatient(Long.parseLong("7"), Long.parseLong("3"));
            needBean.associateNeedToPatient(Long.parseLong("8"), Long.parseLong("2"));
            needBean.associateNeedToPatient(Long.parseLong("7"), Long.parseLong("2"));
            needBean.associateNeedToPatient(Long.parseLong("9"), Long.parseLong("1"));
            
            patientBean.associatePatientToCaregiver(Long.parseLong("1"), "piteu");
            patientBean.associatePatientToCaregiver(Long.parseLong("2"), "piteu2");
            
            // MATERIALS: IDs -> 12 - 24
            emergencyBean.create("112", "Numero de Emergencia Nacional", "112");
            emergencyBean.create("urgencias de leiria", "Urgência Geral da Unidade Hospitalar de Leiria", "244 817 078");
            emergencyBean.create("saude 24", "Saude 24", "808 242 424");
            emergencyBean.create("cruz vermelha", "Cruz Vermelha", "219 421 111");
            emergencyBean.create("comandos", "Comandos", "213 924 700");

            faqBean.create("FAQ sobre o que sao ulceras", "O que sao as ulceras de pressao",
                    "A úlcera de pressão pode ser definida como uma lesão de pele"
                    + " causada pela interrupção sangüínea em uma determinada área,"
                    + " que se desenvolve devido a uma pressão aumentada por um "
                    + "período prolongado. Também é conhecida como úlcera de decúbito,"
                    + "escara ou escara de decúbito. O termo escara deve ser utilizado "
                    + "quando se tem uma parte necrótica ou crosta preta na lesão.");

            faqBean.create("FAQ sobre como se desenvolvem ulceras", "Como se desenvolvem as ulceras de pressao?",
                    "A úlcera de pressão se desenvolve quando se tem uma compressão "
                    + " tecido mole entre uma proeminência óssea e uma superfície"
                    + " dura por um período prolongado. O local mais freqüente "
                    + "para o seu desenvolvimento é na região sacra, calcâneo, "
                    + "nádegas, trocânteres, cotovelos e tronco.");

            faqBean.create("FAQ sobre as causas de desidratação", "Quais as principais causas de desidratação?",
                    "Desidratamos quando o balanço hídrico é negativo, isto é, quando as perdas de água não são\n"
                    + "repostas. Esta situação é mais frequente quando as necessidades hídricas estão aumentadas\n"
                    + "(calor, exercício físico, vómitos, diarreia, …), devendo-se ter especial cuidado com crianças e\n"
                    + "idosos, uma vez que a sua capacidade de detectar o estado de desidratação e/ou responder\n"
                    + "aos seus sinais pode estar diminuída. ");

            /*
            Procedimentos: 
                Limpeza da Ferida
                http://www2.eerp.usp.br/site/grupos/feridascronicas/index.php?option=com_content&view=article&id=10&Itemid=18
            */
            //procedureBean.create("Limpeza da Ferida","Limpeza: limpe as feridas inicialmente e a cada troca de curativo.");
            tutorialBean.create("tutorial de limpeza de feridas", "Limpeza: limpe as feridas inicialmente e a cada troca de curativo.");
            tutorialBean.create("tutorial sobre ulceras", "Utilize uma técnica não traumática usando uma força mecânica mínima quando estiver limpando a úlcera com gaze, compressas ou esponjas.");
            tutorialBean.create("tutorial de cultura de swab", "Não faça culturas de swab para diagnosticar a infecção da ferida, pois todas as UPP são colonizadas. Culturas de swab detectam somente a colonização da superfície e podem não refletir verdadeiramente o microrganismo que está causando a infecção do tecido.");
            
            textBean.create("texto sobre obstruçoes", "Por vezes, uma obstrução resolve-se por si própria sem outro tratamento, sobretudo se for devida à presença de aderências. Para tratar de algumas perturbações como a torção dum segmento da parte baixa do cólon, pode introduzir-se um endoscópio através do ânus ou um clister com papa de bário, o que faz com que a referida porção do intestino se insufle e a obstrução seja resolvida pela pressão exercida. No entanto, o mais habitual é fazer uma intervenção cirúrgica quanto antes. Durante a mesma, o segmento bloqueado do intestino pode ser extirpado e as duas extremidades livres podem ser unidas de novo.");
            textBean.create("texto sobre zonas dolorosas", "O médico examina o abdómen em busca de zonas dolorosas e deformações da parede ou massas. Os sons abdominais normais (ruídos intestinais), que se ouvem através dum fonendoscópio, podem estar aumentados e ser muito agudos, ou então não se ouvirem. Se a perfuração tiver provocado peritonite, o paciente sentirá dor com a pressão do abdómen, que aumenta quando o médico afasta subitamente a mão (sinal de descompressão positivo).");
            
            videoBean.create("video 1", "https://www.youtube.com/watch?v=-vSXINtEPpE");
            videoBean.create("video 2", "https://www.youtube.com/watch?v=NzV6h9K8ApQ");
            videoBean.create("video 3", "https://www.youtube.com/watch?v=W3EHXiuktgM");
            
            needBean.associateMaterialToNeed(12, Long.parseLong("7"));
            needBean.associateMaterialToNeed(14, Long.parseLong("8"));
            needBean.associateMaterialToNeed(17, Long.parseLong("7"));
            needBean.associateMaterialToNeed(22, Long.parseLong("9"));
            needBean.associateMaterialToNeed(23, Long.parseLong("9"));
            needBean.associateMaterialToNeed(15, Long.parseLong("9"));
            needBean.associateMaterialToNeed(19, Long.parseLong("8"));
            needBean.associateMaterialToNeed(20, Long.parseLong("7"));
            needBean.associateMaterialToNeed(24, Long.parseLong("7"));
            
            procedureBean.create(20, Long.parseLong("1"), "piteu", "12-10-2016");
            procedureBean.create(22, Long.parseLong("1"), "piteu", "23-10-2016");
            procedureBean.create(22, Long.parseLong("2"), "piteu2", "23-10-2016");
            procedureBean.create(23, Long.parseLong("1"), "piteu", "15-11-2016");
            procedureBean.create(20, Long.parseLong("1"), "piteu", "17-11-2016");
            procedureBean.create(20, Long.parseLong("2"), "piteu2", "17-10-2016");
            procedureBean.create(22, Long.parseLong("1"), "piteu", "08-12-2016");
            procedureBean.create(23, Long.parseLong("2"), "piteu2", "08-11-2016");
            
        } catch (EntityAlreadyExistsException | MyConstraintViolationException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

}
