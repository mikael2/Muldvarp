/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.domain;

import java.util.HashMap;

/**
 *
 * @author terje
 */
public class QuizAnswers extends Domain {
    private HashMap<Question, String> result;

    public QuizAnswers(String name) {
        super(name);                                                            //The "name" variable in this case is the name of the user.
        result = new HashMap();
    }
    
    public void addQuestion(Question question, String answer){
        result.put(question, answer);
    }
    
    public HashMap<Question, String> getResult(){
        return result;
    }
}
