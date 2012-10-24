/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a Quiz in the Muldvarp Domain.
 * 
 * @author johan
 */
public class Quiz extends Domain {
    List<Question> questions;
    
    public Quiz(String name){
        super(name);
        questions = new ArrayList<Question>();
    }
    
    public Quiz(String name, ArrayList<Question> questions){
        super(name);
        this.questions = questions;
    }

    public List<Question> getQuestions() {
        
        if (questions == null) {
            questions = new ArrayList<Question>();
        }        
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
