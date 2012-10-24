/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.domain;

import java.util.List;

/**
 *
 * @author kristoffer
 */
public class Quiz extends Domain {
    List<Question> questions;
    
    public Quiz(String name){
        super(name);
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
