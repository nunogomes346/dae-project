/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import entities.MaterialGroup.GROUP;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
import static javax.persistence.FetchType.EAGER;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author joaoc
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "getAllFAQs",
    query = "SELECT f FROM FAQ f ORDER BY f.id")
})
public class FAQ extends Material implements Serializable {
    
    @Lob @Basic(fetch=EAGER)
    private String question;
    
    @Lob @Basic(fetch=EAGER)
    private String answer;
    
    public FAQ(){
    }
    
    public FAQ(String question, String answer){
        super(GROUP.Faq);
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
    
    
    
}
