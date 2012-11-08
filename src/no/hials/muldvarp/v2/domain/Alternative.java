/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.domain;

import java.io.Serializable;

/**
 * This class represents an Alternative in the Quiz-context of the Muldvarp application domain.
 * 
 * @author johan
 */
public class Alternative extends Domain implements Serializable {
    boolean shuffleAlternatives;
    boolean isCorrect;
    boolean isChoosen;
    String answerText; //This text is the text that can be set by a user

    public Alternative(String name) {
        super(name);
        isChoosen = false;
    }
    
    public Alternative(String name, boolean isCorrect) {
        super(name);
        this.isCorrect = isCorrect;
        isChoosen = false;
    }
    
    public Alternative(String name, boolean isCorrect, boolean shuffleAlternatives) {
        super(name);
        this.isCorrect = isCorrect;
        isChoosen = false;
        
    }

    public boolean isIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public boolean isIsChoosen() {
        return isChoosen;
    }

    public void setIsChoosen(boolean isChoosen) {
        this.isChoosen = isChoosen;
    }
    
    public void toggleChosen(){
        isChoosen = !isChoosen;
    }
    
    
    
    
}
