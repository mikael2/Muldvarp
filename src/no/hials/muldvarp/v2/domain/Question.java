/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.domain;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author kristoffer
 */
public class Question implements Serializable{
    long id;
    String name;
    
    List<Alternative> alternatives;
    Alternative answer;

    public Question(String name, List<Alternative> alternatives, Alternative answer) {
        this.name = name;
        this.alternatives = alternatives;
        this.answer = answer;
    }

    public long getId() {
        return id;
    }

    public List<Alternative> getAlternatives() {
        return alternatives;
    }

    public void setAlternatives(List<Alternative> alternatives) {
        this.alternatives = alternatives;
    }

    public Alternative getAnswer() {
        return answer;
    }

    public void setAnswer(Alternative answer) {
        this.answer = answer;
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
}
