/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.domain;

import java.io.Serializable;
import java.util.List;

/**
 * This class represents a Question in the Muldvarp application domain.
 * 
 * @author johan
 */
public class Question implements Serializable{
    public enum QuestionType {
        SINGLE,
        MULTIPLE
    }
    
    long id;
    String name;
    QuestionType questionType;
    
    List<Alternative> alternatives;
    List<Alternative> answers;
    
    public Question(String name, List<Alternative> alternatives, Question.QuestionType quType){
        this.name = name;
        this.alternatives = alternatives;
        this.questionType = quType;
    }

    public Question(String name, List<Alternative> alternatives, List<Alternative> answer) {
        this.name = name;
        this.alternatives = alternatives;
        this.answers = answer;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }
    

    public List<Alternative> getAlternatives() {
        return alternatives;
    }

    public void setAlternatives(List<Alternative> alternatives) {
        this.alternatives = alternatives;
    }

    public List<Alternative> getAnswer() {
        return answers;
    }

    public void setAnswers(List<Alternative> answer) {
        this.answers = answer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addAlternative(Alternative newAlternative) {
        alternatives.add(newAlternative);
    }

    public void removeAlternative(Alternative a) {
        alternatives.remove(a);
    }
    
    public String getAlternative(int id){
        for(Alternative alternative : alternatives){
            if(alternative.getId()==id){
                return alternative.getName();
            }
        }
        return null;
    }
}
