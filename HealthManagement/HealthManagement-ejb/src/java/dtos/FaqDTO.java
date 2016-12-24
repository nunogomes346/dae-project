/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author sphinx
 */
@XmlRootElement(name = "FAQ")
@XmlAccessorType(XmlAccessType.FIELD)
public class FaqDTO extends MaterialDTO {
    
    private String question;
    private String answer;
    
    public FaqDTO(String question, String answer){
        super();
        this.question = question;
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    @Override
    public void reset() {
        setAnswer(null);
        setQuestion(null);
    }
    
    
    
}
