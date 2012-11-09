/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * This class represents a Quiz in the Muldvarp Domain.
 * 
 * @author johan
 */
public class Quiz extends Domain {
    public enum QuizType {
        FEEDBACK,
        REMOTE,
        REMOTEFEEDBACK,
        GUIDE
    }
    List<Question> questions;
    boolean shuffleQuestions;
    
    public Quiz(String name){
        super(name);
        questions = new ArrayList<Question>();
    }
    
    public Quiz(String name, ArrayList<Question> questions){
        super(name);
        this.questions = questions;
    }
    
    public Quiz(String name, boolean shuffleQuestions){
        super(name);
        questions = new ArrayList<Question>();
        this.shuffleQuestions = shuffleQuestions;
        if (shuffleQuestions) {
            Collections.shuffle(questions);
        }
    }
    
    public Quiz(String name, ArrayList<Question> questions, boolean shuffleQuestions){
        super(name);
        this.questions = questions;
        this.shuffleQuestions = shuffleQuestions;
        if (shuffleQuestions) {
            Collections.shuffle(questions);
        }
    }


    public List<Question> getQuestions() {        
        if (questions == null) {
            questions = new ArrayList<Question>();
        }        
        return questions;
    }

    public void setQuestions(List<Question> questions) {        
        this.questions = questions;
        if (shuffleQuestions) {
            Collections.shuffle(this.questions);
        }
    }
}
