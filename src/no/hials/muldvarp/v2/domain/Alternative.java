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
public class Alternative implements Serializable {
    int id;
    String name;
    boolean isCorrect;
    boolean isChoosen;
    String answerText; //This text is the text that can be set by a user

    public Alternative(String name) {
        this.name = name;
    }
    
    public Alternative(String name, boolean isCorrect) {
        this.name = name;
        this.isCorrect = isCorrect;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    
    
    
    
}
