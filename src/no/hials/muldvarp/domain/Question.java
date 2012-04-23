/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.domain;

import java.util.List;

/**
 *
 * @author kristoffer
 */
public class Question {
    long id;
    String name;
    List<Alternative> alternatives;
    Alternative answer;

    public Question(String name, List<Alternative> alternatives, Alternative answer) {
        this.name = name;
        this.alternatives = alternatives;
        this.answer = answer;
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

    public void setAnswer(Alternative alt) {
        this.answer = alt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public static class Alternative {
        int id;
        String name;
        
        public Alternative(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
