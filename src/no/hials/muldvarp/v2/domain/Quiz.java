/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class represents a Quiz in the Muldvarp Domain.
 * 
 * @author johan
 */
public class Quiz extends Domain {

    public enum QuizType {
        FEEDBACK("Feedback"),
        REMOTE("Remote"),
        REMOTEFEEDBACK("Remote med feedback"),
        GUIDE("Guide");        
        private String quizType;        
        private QuizType(String quizType){        
            this.quizType = quizType;
        }
        public String getName() {            
            return quizType;
        }
    }
    QuizType quizType;
    List<Question> questions;
    boolean shuffleQuestions;
    
    public Quiz(){
        
    }
    
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
    
    public Quiz(String name, QuizType quizType){
        super(name);
        questions = new ArrayList<Question>();
        this.quizType = quizType;
    }
    
    public Quiz(String name, ArrayList<Question> questions, QuizType quizType){
        super(name);
        this.questions = questions;
        this.quizType = quizType;
    }
    
    public Quiz(String name, boolean shuffleQuestions, QuizType quizType){
        super(name);
        questions = new ArrayList<Question>();
        this.shuffleQuestions = shuffleQuestions;
        this.quizType = quizType;
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

    public boolean isShuffleQuestions() {
        return shuffleQuestions;
    }

    public void setShuffleQuestions(boolean shuffleQuestions) {
        this.shuffleQuestions = shuffleQuestions;
    }

    public QuizType getQuizType() {
        return quizType;
    }

    public void setQuizType(QuizType quizType) {
        this.quizType = quizType;
    }
}
