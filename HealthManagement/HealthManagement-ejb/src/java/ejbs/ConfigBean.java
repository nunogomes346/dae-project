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
    EmergencyContactBean emergencyBean;
    @EJB
    FaqBean faqBean;
    @EJB
    ProcedureBean procedureBean;
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

            patientBean.create("sousa", "patientpw", "João Sousa", "sousa@gmail.com");

            /*
                Materials 
            */
            emergencyBean.create("Numero de Emergencia Nacional", "112");
            emergencyBean.create("Urgência Geral da Unidade Hospitalar de Leiria", "244 817 078");
            emergencyBean.create("Saude 24", "808 242 424");
            emergencyBean.create("Cruz Vermelha", "219 421 111");
            emergencyBean.create("Comandos", "213 924 700");

            faqBean.create("O que sao as ulceras de pressao",
                    "A úlcera de pressão pode ser definida como uma lesão de pele"
                    + " causada pela interrupção sangüínea em uma determinada área,"
                    + " que se desenvolve devido a uma pressão aumentada por um "
                    + "período prolongado. Também é conhecida como úlcera de decúbito,"
                    + "escara ou escara de decúbito. O termo escara deve ser utilizado "
                    + "quando se tem uma parte necrótica ou crosta preta na lesão.");

            faqBean.create("Como se desenvolvem as ulceras de pressao?",
                    "A úlcera de pressão se desenvolve quando se tem uma compressão "
                    + " tecido mole entre uma proeminência óssea e uma superfície"
                    + " dura por um período prolongado. O local mais freqüente "
                    + "para o seu desenvolvimento é na região sacra, calcâneo, "
                    + "nádegas, trocânteres, cotovelos e tronco.");

            faqBean.create("Quais as principais causas de desidratação?",
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
            procedureBean.create("Limpeza: limpe as feridas inicialmente e a cada troca de curativo.");
            procedureBean.create("Utilize uma técnica não traumática usando uma força mecânica mínima quando estiver limpando a úlcera com gaze, compressas ou esponjas.");
            procedureBean.create("Não faça culturas de swab para diagnosticar a infecção da ferida, pois todas as UPP são colonizadas. Culturas de swab detectam somente a colonização da superfície e podem não refletir verdadeiramente o microrganismo que está causando a infecção do tecido.");
            
            textBean.create("Por vezes, uma obstrução resolve-se por si própria sem outro tratamento, sobretudo se for devida à presença de aderências. Para tratar de algumas perturbações como a torção dum segmento da parte baixa do cólon, pode introduzir-se um endoscópio através do ânus ou um clister com papa de bário, o que faz com que a referida porção do intestino se insufle e a obstrução seja resolvida pela pressão exercida. No entanto, o mais habitual é fazer uma intervenção cirúrgica quanto antes. Durante a mesma, o segmento bloqueado do intestino pode ser extirpado e as duas extremidades livres podem ser unidas de novo.");
            textBean.create("O médico examina o abdómen em busca de zonas dolorosas e deformações da parede ou massas. Os sons abdominais normais (ruídos intestinais), que se ouvem através dum fonendoscópio, podem estar aumentados e ser muito agudos, ou então não se ouvirem. Se a perfuração tiver provocado peritonite, o paciente sentirá dor com a pressão do abdómen, que aumenta quando o médico afasta subitamente a mão (sinal de descompressão positivo).");
            
            videoBean.create("https://www.youtube.com/watch?v=-vSXINtEPpE");
            videoBean.create("https://www.youtube.com/watch?v=NzV6h9K8ApQ");
            videoBean.create("https://www.youtube.com/watch?v=W3EHXiuktgM");
            
        } catch (EntityAlreadyExistsException | MyConstraintViolationException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

}
